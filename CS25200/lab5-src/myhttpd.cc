const char * usage =
"                                                                                           \n"
"myhttpd:                                                                                   \n"
"                                                                                           \n"
"An HTTP server that allows an HTTP client (a web browser like FireFox or Internet Explorer)\n"
"to connect to it and download files.                                                       \n"
"                                                                                           \n"
"To use it in one window type:                                                              \n"
"                                                                                           \n"
"   myhttpd [-f|-t|-p] [<port>]                                                             \n"
"                                                                                           \n"
"Where 1024 < port < 65536 and default port is 52499.                                       \n"
"                                                                                           \n"
"In an HTTP client type:                                                                    \n"
"                                                                                           \n"
"   data.cs.purdue.edu:<port>                                                               \n"
"                                                                                           \n";

#include <algorithm>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <dirent.h>
#include <dlfcn.h>
#include <fcntl.h>
#include <fstream>
#include <iostream>
#include <limits.h>
#include <math.h>
#include <netdb.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <string.h>
#include <sys/resource.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include <unistd.h>
#include <unordered_set>
#include <vector>

using namespace std;

int QueueLength = 5; // Maximum number of threads at once
int totalRequests = 0; // Count total number of requests sent
time_t init_time; // Initialize time of start of program
time_t curr_time; // Initialize the current time
double minTime = 1000000000000000000; // Keep track of maximum service time
double maxTime = -1; // Keep track of minimum service time
string minURL = ""; // Keep track of maximum URL request
string maxURL = ""; // Keep track of minimum URL request
bool nAscending = false; // Keep track of sort of name
bool sAscending = false; // Keep track of sort of size

// Initialize global variable for modules
unordered_set<string> moduleSet = {};

// Initialize mutex
pthread_mutex_t mutex;

// Password for user authentication
const char * password = "Y3MyNTI6cGFzc3dvcmQ=";

// Helper function
string getContentType(string filePath);
void processCGIBIN(int socket, string filePath);
void loadModule(int socket, string filePath);
void processStats(int socket, string filePath);
void updateServiceTime(time_t start, string docPath);
void processLogs(int socket, string filePath);
void insertLog(string filePath);
void browseDirectories(int socket, DIR * dir, string docPath, string filePath, string sortLabel);
string fixTime(time_t m_time);
string fixSize(double m_size);
bool sortBySize(const tuple<string, string, string>& a, const tuple<string, string, string>& b);

// Process request
void processRequest(int socket);
void processRequestThread(int socket);
void poolSlave(int socket);

// Handle Zombie
extern "C" void sigChildHandler(int sig) {
  while(waitpid(-1, NULL, WNOHANG) > 0);
}

int main(int argc, char** argv) {
  signal(SIGPIPE, SIG_IGN);
  //struct rlimit mem_limit = { .rlim_cur = 40960000, .rlim_max = 91280000 };
  //struct rlimit mem_limit = { .rlim_cur = 91280000, .rlim_max = 912800000 };
  struct rlimit mem_limit = { .rlim_cur = 912800000, .rlim_max = 9128000000 };
  struct rlimit cpu_limit = { .rlim_cur = 300, .rlim_max = 600 };
  struct rlimit nproc_limit = { .rlim_cur = 50, .rlim_max = 100 };
  if (setrlimit(RLIMIT_AS, &mem_limit)) {
    perror("Couldn't set memory limit\n");
  }
  if (setrlimit(RLIMIT_CPU, &cpu_limit)) {
    perror("Couldn't set CPU limit\n");
  }
  if (setrlimit(RLIMIT_NPROC, &nproc_limit)) {
    perror("Couldn't set NPROC limit\n");
  }

  // Zombie Elimination
  struct sigaction saZ;
  saZ.sa_handler = sigChildHandler;
  sigemptyset(&saZ.sa_mask);
  saZ.sa_flags = SA_RESTART;
  int errorZ = sigaction(SIGCHLD, &saZ, NULL);
  if (errorZ) {
    perror("sigaction");
    exit(-1);
  }

  // Parse arguments and display usage message if error
  int port;
  int flag = 0;

  // If only 1 argument, use default port number
  if (argc == 1) {
    port = 52499;
  // If 2 arguments, set port number
  } else if (argc == 2) {
    port = atoi(argv[1]);
  // If 3 arguments, set port number and flag
  } else if (argc == 3) {
    if (!strcmp(argv[1], "-f")) {
      flag = 1;
    } else if (!strcmp(argv[1], "-t")) {
      flag = 2;
    } else if (!strcmp(argv[1], "-p")) {
      flag = 3;
    } else {
      fprintf(stderr, "%s", usage);
      exit(-1);
    }

    port = atoi(argv[2]);
  // Otherwise, display usage message
  } else {
    fprintf(stderr, "%s", usage);
    exit(-1);
  }
  // Check for valid port
  if (!(1024 < port && port < 65536)) {
    fprintf(stderr, "%s", usage);
    exit(-1);
  }

  // Set the IP address and port for this server
  struct sockaddr_in serverIPAddress;
  memset(&serverIPAddress, 0, sizeof(serverIPAddress));
  serverIPAddress.sin_family = AF_INET;
  serverIPAddress.sin_addr.s_addr = INADDR_ANY;
  serverIPAddress.sin_port = htons((u_short) port);

  // Allocate a socket
  int masterSocket = socket(PF_INET, SOCK_STREAM, 0);
  if (masterSocket < 0) {
    perror("socket");
    exit(-1);
  }

  fstream fs;
  fs.open("http-root-dir/htdocs/logs", fstream::out | fstream::trunc);
  fs.close();
  
  // Start timer
  time(&init_time);

  // Set socket options to reuse port
  // Otherwise, wait about 2 minutes before reusing the same port number
  int optval = 1;
  int err = setsockopt(masterSocket, SOL_SOCKET, SO_REUSEADDR,
                      (char *) &optval, sizeof(int));

  // Bind the socket to the IP address and port
  int error = bind(masterSocket,
                  (struct sockaddr *) &serverIPAddress,
                  sizeof(serverIPAddress));
  if (error) {
    perror("bind");
    exit(-1);
  }

  // Put socket in listening mode and set the queue size of unproccessed connections  
  error = listen(masterSocket, QueueLength);
  if (error) {
    perror("listen");
    exit(-1);
  }

  // If '-f' flag, create a new process for each request
  if (flag == 1) {
    while (1) {
      // Accept incoming connections
      struct sockaddr_in clientIPAddress;
      int alen = sizeof(clientIPAddress);
      int slaveSocket = accept(masterSocket, (struct sockaddr*) &clientIPAddress,
                            (socklen_t*) &alen);
      if (slaveSocket < 0) {
        perror("accept");
        exit(-1);
      }

      // Process request
      int slave = fork();
      if (slave == 0) { 
        processRequest(slaveSocket);
        close(slaveSocket);
        exit(1);
      }

      // Close socket
      close(slaveSocket);
    }
  // If '-t' flag, create a new thread for each request
  } else if (flag == 2) {
    while (1) {
      // Accept incoming connections
      struct sockaddr_in clientIPAddress;
      int alen = sizeof(clientIPAddress);
      int slaveSocket = accept(masterSocket, (struct sockaddr*) &clientIPAddress,
                            (socklen_t*) &alen);
      if (slaveSocket < 0) {
        perror("accept");
        exit(-1);
      }

      cout << "REQUEST ACCEPTED\n";

      // Initialize pthread attributes
      pthread_t tid;
      pthread_attr_t attr;
      
      pthread_attr_init(&attr);
      pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
      pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);
      
      cout << "after pthread\n";

      // Process request
      pthread_create(&tid, &attr, (void *(*)(void *)) processRequestThread,
                    (void *) slaveSocket);
    }
  // If '-p' flag, pool of threads
  } else if (flag == 3) {
    // Initialize pthread attributes
    pthread_t tid[5];
    pthread_attr_t attr;
    
    pthread_attr_init(&attr);
    pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);
    
    pthread_mutex_init(&mutex, NULL);

    for(int i = 0; i< 5; i++) {
      pthread_create(&tid[i], &attr, (void *(*)(void *)) poolSlave, (void *) masterSocket);
    }
    
    pthread_join(tid[0], NULL);
  // Basic server
  } else {
    while (1) {
      // Accept incoming connections
      struct sockaddr_in clientIPAddress;
      int alen = sizeof(clientIPAddress);
      int slaveSocket = accept(masterSocket, (struct sockaddr*) &clientIPAddress,
                            (socklen_t*) &alen);
      if (slaveSocket < 0) {
        perror("accept");
        exit(-1);
      }

      // Process request
      processRequest(slaveSocket);

      // Close socket
      close(slaveSocket);
    }
  }
}

void processRequestThread(int socket) {
  cout << "in request thread\n";
  processRequest(socket);
  close(socket);
}

void poolSlave(int socket) {
  while (1) {
    // Accept incoming connections
    struct sockaddr_in clientIPAddress;
    int alen = sizeof(clientIPAddress);

    pthread_mutex_lock(&mutex);
    int slaveSocket = accept(socket, (struct sockaddr*) &clientIPAddress,
                           (socklen_t*) &alen);
    if (slaveSocket < 0) {
      perror("accept");
      exit(-1);
    }
    pthread_mutex_unlock(&mutex);
    
    // Process request
    processRequest(slaveSocket);

    // Close socket
    close(slaveSocket);
  }
}

string getContentType(string filePath) {
  string contentType = "";
  if ((filePath.compare(filePath.length() - 5, 5, ".html") == 0) || 
      (filePath.compare(filePath.length() - 6, 6, ".html/") == 0)) {
    contentType = "text/html";
  } else if ((filePath.compare(filePath.length() - 4, 4, ".png") == 0) || 
             (filePath.compare(filePath.length() - 5, 5, ".png/") == 0)) {
    contentType = "image/png";
  } else if ((filePath.compare(filePath.length() - 4, 4, ".gif") == 0) || 
             (filePath.compare(filePath.length() - 5, 5, ".gif/") == 0)) {
    contentType = "image/gif";
  } else if ((filePath.compare(filePath.length() - 4, 4, ".svg") == 0) || 
             (filePath.compare(filePath.length() - 5, 5, ".svg/") == 0)) {
    contentType = "image/svg+xml";
  } else {
    contentType = "text/plain";
  }

  return contentType;
}

void send404Error(int socket, string contentType) {
  string headerStr = "HTTP/1.0 404 File Not Found\r\n";
  string serverStr = "Server: CS 252 lab5\r\n";
  string contentStr = "Content-type: " + contentType + "\r\n\r\n";
  string notFound = "File not Found";
  write(socket, headerStr.c_str(), headerStr.length());
  write(socket, serverStr.c_str(), serverStr.length());
  write(socket, contentStr.c_str(), contentStr.length());
  write(socket, notFound.c_str(), notFound.length());
}

void processCGIBIN(int socket, string filePath) {
  // Seperate environment stuff
  int posQuestion = filePath.find('?');
  string varValuePair = "";
  if (posQuestion != string::npos) {
    varValuePair = filePath.substr(posQuestion + 1, filePath.length());
    filePath = filePath.substr(0, posQuestion);
  }

  // Fork child process
  int ret = fork();
  if (ret == 0) {
    // Set the environment variable
    setenv("REQUEST_METHOD", "GET", 1);
    if (posQuestion != string::npos) {
      setenv("QUERY_STRING", varValuePair.c_str(), 1);
    }

    // Redirect output of child process to slave socket
    dup2(socket, 1);

    // Print the header
    string headerStr = "HTTP/1.1 200 Document follows\r\n";
    string serverStr = "Server: CS 252 lab5\r\n";
    write(socket, headerStr.c_str(), headerStr.length());
    write(socket, serverStr.c_str(), serverStr.length());

    // Execute script
    vector<char *> temp;
    temp.push_back(const_cast<char *>(filePath.c_str()));
    temp.push_back(NULL);
    execvp(temp[0], temp.data());
  } else if (ret < 0) {
    perror("fork");
  }

  // Wait for last process
  waitpid(ret, NULL, 0);
  
  // Close socket
  close(socket);
}

void loadModule(int socket, string filePath) {
  // Seperate environment stuff
  int posQuestion = filePath.find('?');
  string varValuePair = "";
  if (posQuestion != string::npos) {
    varValuePair = filePath.substr(posQuestion + 1, filePath.length());
    filePath = filePath.substr(0, posQuestion);
  }

  // Check if module has been previously loaded
  unordered_set<string>::const_iterator got = moduleSet.find(filePath);
  if (got != moduleSet.end()) {
    return;
  }

  // Load module into memory
  moduleSet.insert(filePath);
  void *handle = dlopen(filePath.c_str(), RTLD_NOW);
  if (handle == NULL) {
    fprintf( stderr, "File not found:%s\n", filePath.c_str());
    perror( "dlopen");
  }

  void (* httprun)(int, char *);
  *(void **)(&httprun) = dlsym(handle, "httprun");

  // Write the response using httprun()
  string headerStr = "HTTP/1.1 200 Document follows\r\n";
  string serverStr = "Server: CS 252 lab5\r\n";
  write(socket, headerStr.c_str(), headerStr.length());
  write(socket, serverStr.c_str(), serverStr.length());
  httprun(socket, (char *) varValuePair.c_str());
}

void processStats(int socket, string filePath) {
  string headerStr = "HTTP/1.1 200 Document follows\r\n";
  string serverStr = "Server: CS 252 lab5\r\n";
  string contentStr = "Content: text/plain\r\n\r\n";
  write(socket, headerStr.c_str(), headerStr.length());
  write(socket, serverStr.c_str(), serverStr.length());
  write(socket, contentStr.c_str(), contentStr.length());

  time(&curr_time);
  double lifeTime = difftime(curr_time, init_time);

  string studentName = "Student Name: Youngsik Yoon\r\n";
  string serverTimeStr = "Server Lifetime: " + to_string(lifeTime) +"\r\n";
  string numRequestsStr = "Number of Requests: " + to_string(totalRequests) + "\r\n";
  string minTimeStr = "Minimum service Time: " + to_string(minTime) + "| URL: data.cs.purdue.edu:52499" + minURL.c_str() + "\r\n";
  string maxTimeStr = "Maximum service Time: " + to_string(maxTime) + "| URL: data.cs.purdue.edu:52499" + maxURL.c_str() + "\r\n";
  write(socket, studentName.c_str(), studentName.length());
  write(socket, serverTimeStr.c_str(), serverTimeStr.length());
  write(socket, numRequestsStr.c_str(), numRequestsStr.length());
  write(socket, minTimeStr.c_str(), minTimeStr.length());
  write(socket, maxTimeStr.c_str(), maxTimeStr.length());
}

void updateServiceTime(time_t start, string docPath) {
  time_t end;
  time(&end);
  double tmp = difftime(end, start);
  if (tmp > maxTime) {
    maxTime = tmp;
    maxURL = docPath;
  }
  if (tmp < minTime) {
    minTime = tmp;
    maxURL = docPath;
  }
}

void processLogs(int socket, string filePath) {
  string headerStr = "HTTP/1.1 200 Document follows\r\n";
  string serverStr = "Server: CS 252 lab5\r\n";
  string contentStr = "Content: text/plain\r\n\r\n";
  write(socket, headerStr.c_str(), headerStr.length());
  write(socket, serverStr.c_str(), serverStr.length());
  write(socket, contentStr.c_str(), contentStr.length());
  
  string logStr;
  ifstream logFile ("http-root-dir/htdocs/logs");
  while (getline(logFile, logStr)) {
    write(socket, logStr.c_str(), logStr.length());
  }
  logFile.close();
}

void insertLog(string filePath) {
  fstream fs;
  fs.open("http-root-dir/htdocs/logs", fstream::in | fstream::out | fstream::app);
  fs << "data.cs.purdue.edu:52499/logs\r\n";
  fs << filePath + "\r\n";
  fs.close();
}

void browseDirectories(int socket, DIR * dir, string docPath, string filePath, string sortLabel) {
  // Get parent directory
  string parentPath = docPath;
  parentPath.pop_back();
  while (parentPath.back() != '/') {
    parentPath.pop_back();
  }

  // Create Header
  string headerStr = "HTTP/1.1 200 Document follows\r\n";
  string serverStr = "Server: CS 252 lab5\r\n";
  string contentStr = "Content-type: text/html\r\n\r\n";

  // Create vector for items
  vector<tuple<string, string, string>> tableList;

  // Go through each file in dir
  struct dirent * ent; 
  while ((ent = readdir(dir)) != NULL) {
    // Get file
    string f_name = ent->d_name;
    if (f_name.at(0) == '.') {
      continue;
    }

    // Get stat info
    struct stat buf;
    stat((filePath + f_name).c_str(), &buf);
    
    // Determine size and lm
    string time = fixTime(buf.st_mtime);
    string size = fixSize(static_cast<double>(buf.st_size));

    // Insert into vector
    tableList.push_back(tuple<string, string, string>(f_name, time, size));
  }

  // Sort directory
  if (!sortLabel.empty()) {
    char sortCategory = sortLabel.at(2);
    if (sortCategory == 'N') {
      if (nAscending) {
        sort(tableList.begin(), tableList.end());
        nAscending = false;
      } else {
        sort(tableList.rbegin(), tableList.rend());
        nAscending = true;
      }
    } else if (sortCategory == 'S') {
      if (sAscending) {
        sort(tableList.begin(), tableList.end(), sortBySize);
        sAscending = false;
      } else {
        sort(tableList.rbegin(), tableList.rend(), sortBySize);
        sAscending = true;
      }
    }
  }

  // Write to html
  string htmlStr = "<html>\n<head><title>Index of "+filePath+"</title></head>";
  htmlStr.append("<body><h1>Index of "+filePath+"</h1>");
  htmlStr.append("<table><tr><th valign=\"top\"><img src=\"/icons/blank.gif\" alt=\"[ICO]\"></th><th><a href=\"?C=N;O=D\">Name</a></th><th><a href=\"?C=M;O=A\">Last modified</a></th><th><a href=\"?C=S;O=A\">Size</a></th><th><a href=\"?C=D;O=A\">Description</a></th></tr>");
  htmlStr.append("<tr><th colspan=\"5\"><hr></th></tr>");
  htmlStr.append("<tr><td valign=\"top\"><img src=\"/icons/back.gif\"></td><td><a href=\""+parentPath+"\">Parent Directory</a></td><td>&nbsp;</td><td align=\"right\">  - </td><td>&nbsp;</td></tr>");

  for (int i = 0; i < tableList.size(); i++) {
    // Get variables 
    string iconType = "../icons/unknown.gif";
    string altType = "   ";
    if (get<0>(tableList[i]).find(".gif") != string::npos) {
      iconType = "../icons/image2.gif";
      altType = "IMG";
    } else if (opendir(get<0>(tableList[i]).c_str()) != NULL) {
      iconType = "../icons/folder.gif";
      altType = "DIR";
    }

    htmlStr.append("<tr><td valign=\"top\"><img src=\"" + iconType + "\" alt=\"[" + altType + "]\"></td>");
    htmlStr.append("<td><a href=\"" + get<0>(tableList[i]) + "\">" + get<0>(tableList[i]) + "</a></td>");
    htmlStr.append("<td align=\"right\">" + get<1>(tableList[i]) + "</td>");
    htmlStr.append("<td align=\"right\">" + get<2>(tableList[i]) + "</td><td>&nbsp;</td></tr>");
  }

  htmlStr.append("<tr><th colspan=\"5\"><hr></th></tr></table><address>Apache/2.4.18 (Ubuntu) Server at www.cs.purdue.edu Port 52499</address></body></html>");
    
  write(socket, headerStr.c_str(), headerStr.length());
  write(socket, serverStr.c_str(), serverStr.length());
  write(socket, contentStr.c_str(), contentStr.length());
  write(socket, htmlStr.c_str(), htmlStr.length());
}

string fixTime(time_t m_time) {
  return ctime(&m_time);
}

string fixSize(double m_size) {
  if (m_size == 0) {
    return "0B";
  } else if (m_size < 1024) {
    return to_string(m_size) + "B";
  }

  int exp = (int) (log(m_size) / log(1024));
  string type = "KMGTPE";
  string pre(1, type.at(exp - 1));
  char f_size[100];
  sprintf(f_size, "%.1f%sB", m_size / pow(1024, exp), pre.c_str());
  return f_size;
}

bool sortBySize(const tuple<string, string, string>& a, const tuple<string, string, string>& b) {
  return (get<2>(a) < get<2>(b));
}

void processRequest(int socket) {
  // Stat page stuff
  totalRequests += 1;
  time_t start_t;
  time(&start_t);

  // Initialize all flags
  bool fileNotFound = false;

  // Buffer used to store the name received from the client
  const int MaxName = 1024;
  char name[ MaxName + 1 ];
  int nameLength = 0;
  int n;

  // Currently character read
  unsigned char newChar;

  // Last character read
  unsigned char lastChar = 0;

  //
  // The client should send <name><cr><lf>
  // Read the name of the client character by character until a
  // <CR><LF> is found.
  //
  int isNewline = 0;
  while (( n = read( socket, &newChar, sizeof(newChar) ) ) > 0 ) {
    if ( lastChar == '\015' && newChar == '\012' && isNewline == 1) {
      // Discard previous <CR> from name
      nameLength--;
      break;
    } else if (lastChar == '\015' && newChar == '\012') {
      isNewline = 1;
    } else if(lastChar == '\012' && newChar == '\015') {
      isNewline = 1;
    } else {
      isNewline = 0;
    }

    name[ nameLength ] = newChar;
    nameLength++;

    lastChar = newChar;
  }
  string tmpname(name);

  // HTTP Authentication
  int posPassword = tmpname.find("eW9vbjE2MQo=");
  if (strstr(name, password) == 0) {
    string unauth = "HTTP/1.1 401 Unauthorized\r\n";
    string wwwauth = "WWW-Authenticate: Basic realm=\"myhttpd-cs252\"\r\n";
    write(socket, unauth.c_str(), unauth.length());
    write(socket, wwwauth.c_str(), wwwauth.length());
  }

  cout << "------------------------------------------------------------\n";
  cout << "READ FROM SOCKET\n";
  cout << tmpname << "\n";

  // Get the document path
  string docPath = "";

  int posSpace = tmpname.find(' ') + 1;
  while (tmpname.at(posSpace) != ' ') {
    docPath += tmpname.at(posSpace++);
  }

  cout << "DOCPATH: " << docPath << "\n";

  // Map the docpath to the real file
  char tmpcwd[256];
  getcwd(tmpcwd, sizeof(tmpcwd));
  string cwd(tmpcwd);
  
  cout << "CWD: " + cwd << "\n";
  
  // Get file path
  string filePath = "";
  if ((docPath.find("/icons") != string::npos) ||
      (docPath.find("/htdocs") != string::npos)) {
    if (docPath.at(0) == '/') {
      filePath = cwd + "/http-root-dir" + docPath;
    } else {
      filePath = cwd + "/http-root-dir/" + docPath;
    }
  } else if (docPath.find("/cgi-bin") != string::npos) {
    filePath = cwd + "/http-root-dir" + docPath;
    if (docPath.find(".so") != string::npos) {
      loadModule(socket, filePath);
    } else {
      processCGIBIN(socket, filePath);
    }
    close(socket);
    updateServiceTime(start_t, docPath);
    insertLog(filePath);
    return;
  } else if (docPath.find("/stats") != string::npos) {
    filePath = cwd + "/http-root-dir" + docPath;
    processStats(socket, filePath);
    close(socket);
    updateServiceTime(start_t, docPath);
    insertLog(filePath);
    return;
  } else if (docPath.find("/logs") != string::npos) {
    filePath = cwd + "/http-root-dir" + docPath;
    insertLog(filePath);
    processLogs(socket, filePath);
    updateServiceTime(start_t, docPath);
    return;
  } else if (docPath == "/") {
    filePath = cwd + "/http-root-dir/htdocs/index.html";
  } else {
    filePath = cwd + "/http-root-dir/htdocs" + docPath;
  }
  insertLog(filePath);
  
  cout << "FILEPATH: " << filePath << "\n";
  
  // Expand file path
  string expandedFilePath = filePath;
  int posExpand = expandedFilePath.find("..");
  while (posExpand != string::npos) {
    expandedFilePath.erase(posExpand - 1, 3);

    posExpand = posExpand - 2;
    while (posExpand != -1 && expandedFilePath.at(posExpand) != '/') {
      expandedFilePath.erase(posExpand, 1);
      posExpand--;
    }

    posExpand = expandedFilePath.find("..");
  }

  // Check if valid filepath
  if (expandedFilePath.length() < string(cwd + "/http-root-dir").length()) {
    fileNotFound = true;
  } else {
    filePath = expandedFilePath;
  }
  cout << "EXPANED FILEPATH: " << filePath << "\n";
  
  // Determine content type
  string contentType = getContentType(filePath);
  cout << "CONTENT TYPE: " << contentType << "\n";

  // Find sorting instruction
  int posQuestion = filePath.find('?');
  string sortLabel = "";
  if (posQuestion != string::npos) {
    sortLabel = filePath.substr(posQuestion + 1);
    filePath = filePath.substr(0, posQuestion);
    cout << "NEW FILEPATH: " + filePath + "\n";
    cout << "SORT LABEL: " + sortLabel + "\n";
  }

  // Check if filePath is a directory
  bool isDir = true;
  DIR * dir = opendir(filePath.c_str());
  if (dir == NULL) {
    isDir = false;
  } else {
  // Flag for backslash
    if (filePath.back() != '/' && filePath.find("sub") == string::npos) {
      cout << "STATUS 302\n";
      string status302 = "HTTP/1.1 302 Found\r\nLocation: /dir1/\r\n";
      write(socket, status302.c_str(), status302.length());
      return;
    }
  }

  // Open the file
  FILE * file;
  file = fopen(filePath.c_str(), "rb");
  if (file == NULL) {
    perror("fopen");
    fileNotFound = true;
  }

  // Send HTTP Reply Header
  if (fileNotFound) {
    send404Error(socket, contentType);
    updateServiceTime(start_t, docPath);
  } else if (isDir) {
    browseDirectories(socket, dir, docPath, filePath, sortLabel);
    updateServiceTime(start_t, docPath);
    fclose(file);
    docPath.clear();
    filePath.clear();
  } else {
    string headerStr = "HTTP/1.1 200 Document follows\r\n";
    string serverStr = "Server: CS 252 lab5\r\n";
    string contentStr = "Content-type: " + contentType + "\r\n\r\n";
    write(socket, headerStr.c_str(), headerStr.length());
    write(socket, serverStr.c_str(), serverStr.length());
    write(socket, contentStr.c_str(), contentStr.length());
    char c;
    int count = 0;
    while (count = read(fileno(file), &c, 1)) {
      if (write(socket, &c, 1) != count) {
        perror("write");
        break;
      }
    }
    
    updateServiceTime(start_t, docPath);
    closedir(dir);
    fclose(file);
    docPath.clear();
    filePath.clear();
  }
}
