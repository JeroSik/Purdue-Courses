const char * usage =
"                                                               \n"
"IRCServer:                                                   \n"
"                                                               \n"
"Simple server program used to communicate multiple users       \n"
"                                                               \n"
"To use it in one window type:                                  \n"
"                                                               \n"
"   IRCServer <port>                                          \n"
"                                                               \n"
"Where 1024 < port < 65536.                                     \n"
"                                                               \n"
"In another window type:                                        \n"
"                                                               \n"
"   telnet <host> <port>                                        \n"
"                                                               \n"
"where <host> is the name of the machine where talk-server      \n"
"is running. <port> is the port number you used when you run    \n"
"daytime-server.                                                \n"
"                                                               \n";

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <time.h>

#include <vector>
#include <string>
#include <map>

#include <iterator>
#include <algorithm>

#include <iostream>
#include <fstream>

#include "IRCServer.h"

using namespace std;

int QueueLength = 5;

// Array for usernames and rooms
vector <string> usernames;
vector <string> rooms;
// Container to store usernames and passwords
map <string, string> userData;
// Container for room users and messages
map <string, vector <string> > roomData;
map <string, vector <string> > roomMessages;

//test
int IRCServer::open_server_socket(int port) {
	// Set the IP address and port for this server
	struct sockaddr_in serverIPAddress; 
	memset( &serverIPAddress, 0, sizeof(serverIPAddress) );
	serverIPAddress.sin_family = AF_INET;
	serverIPAddress.sin_addr.s_addr = INADDR_ANY;
	serverIPAddress.sin_port = htons((u_short) port);
  
	// Allocate a socket
	int masterSocket =  socket(PF_INET, SOCK_STREAM, 0);
	if ( masterSocket < 0) {
		perror("socket");
		exit( -1 );
	}

	// Set socket options to reuse port. Otherwise we will
	// have to wait about 2 minutes before reusing the sae port number
	int optval = 1; 
	int err = setsockopt(masterSocket, SOL_SOCKET, SO_REUSEADDR, 
			     (char *) &optval, sizeof( int ) );
	
	// Bind the socket to the IP address and port
	int error = bind( masterSocket,
			  (struct sockaddr *)&serverIPAddress,
			  sizeof(serverIPAddress) );
	if ( error ) {
		perror("bind");
		exit( -1 );
	}
	
	// Put socket in listening mode and set the 
	// size of the queue of unprocessed connections
	error = listen( masterSocket, QueueLength);
	if ( error ) {
		perror("listen");
		exit( -1 );
	}

	return masterSocket;
}

void IRCServer::runServer(int port) {
	int masterSocket = open_server_socket(port);

	initialize();
	
	while ( 1 ) {		
		// Accept incoming connections
		struct sockaddr_in clientIPAddress;
		int alen = sizeof( clientIPAddress );
		int slaveSocket = accept( masterSocket,
					  (struct sockaddr *)&clientIPAddress,
					  (socklen_t*)&alen);
		
		if ( slaveSocket < 0 ) {
			perror( "accept" );
			exit( -1 );
		}
		
		// Process request.
		processRequest( slaveSocket );		
	}
}

int main( int argc, char ** argv ) {
	// Print usage if not enough arguments
	if ( argc < 2 ) {
		fprintf( stderr, "%s", usage );
		exit( -1 );
	}
	
	// Get the port from the arguments
	int port = atoi( argv[1] );

	IRCServer ircServer;

	// It will never return
	ircServer.runServer(port);
	
}

//
// Commands:
//   Commands are started y the client.
//
//   Request: ADD-USER <USER> <PASSWD>\r\n
//   Answer: OK\r\n or DENIED\r\n
//
//   REQUEST: GET-ALL-USERS <USER> <PASSWD>\r\n
//   Answer: USER1\r\n
//            USER2\r\n
//            ...
//            \r\n
//
//   REQUEST: CREATE-ROOM <USER> <PASSWD> <ROOM>\r\n
//   Answer: OK\n or DENIED\r\n
//
//   Request: LIST-ROOMS <USER> <PASSWD>\r\n
//   Answer: room1\r\n
//           room2\r\n
//           ...
//           \r\n
//
//   Request: ENTER-ROOM <USER> <PASSWD> <ROOM>\r\n
//   Answer: OK\n or DENIED\r\n
//
//   Request: LEAVE-ROOM <USER> <PASSWD>\r\n
//   Answer: OK\n or DENIED\r\n
//
//   Request: SEND-MESSAGE <USER> <PASSWD> <MESSAGE> <ROOM>\n
//   Answer: OK\n or DENIED\n
//
//   Request: GET-MESSAGES <USER> <PASSWD> <LAST-MESSAGE-NUM> <ROOM>\r\n
//   Answer: MSGNUM1 USER1 MESSAGE1\r\n
//           MSGNUM2 USER2 MESSAGE2\r\n
//           MSGNUM3 USER2 MESSAGE2\r\n
//           ...\r\n
//           \r\n
//
//    REQUEST: GET-USERS-IN-ROOM <USER> <PASSWD> <ROOM>\r\n
//    Answer: USER1\r\n
//            USER2\r\n
//            ...
//            \r\n
//

void IRCServer::processRequest( int fd ) {
	// Buffer used to store the comand received from the client
	const int MaxCommandLine = 1024;
	char commandLine[ MaxCommandLine + 1 ];
	int commandLineLength = 0;
	int n;
	
	// Currently character read
	unsigned char prevChar = 0;
	unsigned char newChar = 0;
	
	//
	// The client should send COMMAND-LINE\n
	// Read the name of the client character by character until a
	// \n is found.
	//

	// Read character by character until a \n is found or the command string is full.
	while ( commandLineLength < MaxCommandLine &&
		read( fd, &newChar, 1) > 0 ) {

		if (newChar == '\n' && prevChar == '\r') {
			break;
		}
		
		commandLine[ commandLineLength ] = newChar;
		commandLineLength++;

		prevChar = newChar;
	}
	
	// Add null character at the end of the string
	// Eliminate last \r
	commandLineLength--;
        commandLine[ commandLineLength ] = 0;

	printf("RECEIVED: %s\n", commandLine);

	//printf("The commandLine has the following format:\n");
	//printf("COMMAND <user> <password> <arguments>. See below.\n");
	//printf("You need to separate the commandLine into those components\n");
	//printf("For now, command, user, and password are hardwired.\n");

	//const char * command = "ADD-USER";
	//const char * user = "peter";
	//const char * password = "spider";
	//const char * args = "";

	// Split commandLine into four parts
	char * command = strtok(commandLine, " ");
	char * user = strtok(NULL, " ");
	char * password = strtok(NULL, " ");
	char * args = strtok(NULL, "");

	printf("command=%s\n", command);
	printf("user=%s\n", user);
	printf( "password=%s\n", password );
	printf("args=%s\n", args);

	if (!strcmp(command, "LOG-IN")) {
		logIn(fd, user, password, args);
	} else if (!strcmp(command, "ADD-USER")) {
		addUser(fd, user, password, args);
	} else if (!strcmp(command, "CREATE-ROOM")) {
		createRoom(fd, user, password, args);
	} else if (!strcmp(command, "LIST-ROOMS")) {
		listRooms(fd, user, password, args);
	} else if (!strcmp(command, "ENTER-ROOM")) {
		enterRoom(fd, user, password, args);
	} else if (!strcmp(command, "LEAVE-ROOM")) {
		leaveRoom(fd, user, password, args);
	} else if (!strcmp(command, "SEND-MESSAGE")) {
		sendMessage(fd, user, password, args);
	} else if (!strcmp(command, "GET-MESSAGES")) {
		getMessages(fd, user, password, args);
	} else if (!strcmp(command, "GET-USERS-IN-ROOM")) {
		getUsersInRoom(fd, user, password, args);
	} else if (!strcmp(command, "GET-ALL-USERS")) {
		getAllUsers(fd, user, password, args);
	} else {
		const char * msg =  "UNKNOWN COMMAND\r\n";
		write(fd, msg, strlen(msg));
	}

	// Send OK answer
	//const char * msg =  "OK\n";
	//write(fd, msg, strlen(msg));

	close(fd);	
}

void IRCServer::initialize() {
	string input;
	string username;
	string password;
	int count = 0;
	
	// Open password file
	ifstream myfile("password.txt");
	if (myfile.is_open()) {
		// Initalize message list
		while (getline(myfile, input)) {
			count++;
			if ((count % 3) == 0) {
				// Initialize users in room
				userData[username] = password;
				usernames.push_back(username);
			} else if ((count % 3) == 1) {
				// Read username
				username = input;
			} else {
				// Read password
				password = input;
			}
		}
	}
}

// Check the password
bool IRCServer::checkPassword(int fd, const char * user, const char * password) {
	// Compares password given to user password
	if (userData[user].compare(password) != 0) {
		// Print error when wrong
		const char * msg =  "ERROR (Wrong password)\r\n";
		write(fd, msg, strlen(msg));
		return false;
	} else {
		return true;
	}
}

// Log In
void IRCServer::logIn(int fd, const char * user, const char * password, const char * args) {	
	// Make sure username exists
	if (find(usernames.begin(), usernames.end(), user) != usernames.end()) {
		// Check password
		if (checkPassword(fd, user, password)) {
			const char * msg =  "OK\r\n";
			write(fd, msg, strlen(msg));
			return;
		}	
	} else {	
		const char * msg =  "DENIED\r\n";
		write(fd, msg, strlen(msg));
		return;
	}
}

// Add a new user
void IRCServer::addUser(int fd, const char * user, const char * password, const char * args) {
	// Make sure username is unique
	if (find(usernames.begin(), usernames.end(), user) == usernames.end()) {
		// Add username and password to container
		userData[user] = password;
		
		// Add username to array
		usernames.push_back(user);

		const char * msg =  "OK\r\n";
		write(fd, msg, strlen(msg));
		return;	
	} else {	
		const char * msg =  "DENIED\r\n";
		write(fd, msg, strlen(msg));
		return;
	}
}

// List rooms
void IRCServer::listRooms(int fd, const char * user, const char * password, const char * args) {
	// Check password
	if (checkPassword(fd, user, password)) {
		string print = "";
		
		// Go through all rooms
		for (int i = 0; i < rooms.size(); i++) {
			print.append(rooms.at(i));
			print.append("\r\n");
		}
		print.append("\r\n");

		const char * msg = print.c_str();
		write(fd, msg, strlen(msg));	
	}
}

// Create a new room
void IRCServer::createRoom(int fd, const char * user, const char * password, const char * args) {
	// Check password
	if (checkPassword(fd, user, password)) {
		// Make sure room name is unique
		if (find(rooms.begin(), rooms.end(), args) == rooms.end()) {
			// Add room to array
			rooms.push_back(args);
		
			const char * msg = "OK\r\n";
			write(fd, msg, strlen(msg));
			return;
		} else {
			const char * msg = "ERROR (Room already exists)\r\n";
			write(fd, msg, strlen(msg));
			return;
		}
	}
}

// Enter a room
void IRCServer::enterRoom(int fd, const char * user, const char * password, const char * args) {
	// Check password
	if (checkPassword(fd, user, password)) {
		// Make sure room name exists
		if (find(rooms.begin(), rooms.end(), args) == rooms.end()) {
			const char * msg = "ERROR (No room)\r\n";
			write(fd, msg, strlen(msg));
			return;
		} else {
			// Go through the users in the room 
			for (int i = 0; i < roomData[args].size(); i++) {
				// Check to see if user is already in room
				if (roomData[args][i].compare(user) == 0) {
					const char * msg = "OK\r\n";
					write(fd, msg, strlen(msg));
					return;
				}
			}
			
			// Add user to room
			roomData[args].push_back(user);

			const char * msg = "OK\r\n";
			write(fd, msg, strlen(msg));
		}
	}
}

// Leave a room
void IRCServer::leaveRoom(int fd, const char * user, const char * password, const char * args) {
	// Check password
	if (checkPassword(fd, user, password)) {
		// Go through all users in room
		for (int i = 0; i < roomData[args].size(); i++) {
			// Check to see if user is in room
			if (roomData[args][i].compare(user) == 0) {
				// Remove user from the room
				roomData[args].erase(roomData[args].begin() + i);

				const char * msg = "OK\r\n";
				write(fd, msg, strlen(msg));
				return;
			}
		}
		
		// User is not in room
		const char * msg = "ERROR (No user in room)\r\n";
		write(fd, msg, strlen(msg));
	}
}

// Send a message in a room
void IRCServer::sendMessage(int fd, const char * user, const char * password, const char * args) {
	string print = "";	
	
	// Check password
	if (checkPassword(fd, user, password)) {
		// Convert const char * args into char *
		char * temp = strdup(args);
		char * username = strdup(user);
		
		// Split args into room and message
		char * room = strtok(temp, " ");
		char * note = strtok(NULL, "");

		// Go through all users in room
		for (int i = 0; i < roomData[room].size(); i++) {
			// Check if user exists	in room
			if (roomData[room][i].compare(user) == 0) {
				// Add message to room
				print.append(username);
				print.append(" ");
				print.append(note);
				roomMessages[room].push_back(print);
				
				const char * msg = "OK\r\n";
				write(fd, msg, strlen(msg));
				return;
			}		
		}

		const char * msg = "ERROR (user not in room)\r\n";
		write(fd, msg, strlen(msg));
		return;
	}
}

// Get the messages in a room
void IRCServer::getMessages(int fd, const char * user, const char * password, const char * args) {
	// Check password
	if (checkPassword(fd, user, password)) {
		const char * args1 = "-1";
		string message = "";

		// Convert const char * args into char *
		char * temp = strdup(args);
		char * username = strdup(user);
		
		// Split args into room and message
		char * index = strtok(temp, " ");
		char * room = strtok(NULL, " ");

		// Convert char * into int
		int number = atoi(index);

		// Go through all users in room
		for (int i = 0; i < roomData[room].size(); i++) {
			// Check if user exists	in room
			if (roomData[room][i].compare(user) == 0) {
				// Check if there are any messages in room
				if ((number + 1) > roomMessages[room].size()) {
					const char * old = "NO-NEW-MESSAGES\r\n";
					write(fd, old, strlen(old));
					return;
				}
				
				// Go through all room messages starting room line number
				for (int i = number + 1; i < roomMessages[room].size(); i++) {
					char buffer[30];
					sprintf(buffer, "%d", i);
					message.append(buffer);
					message.append(" ");
					message.append(roomMessages[room][i]);
					message.append("\r\n");
				}
				message.append("\r\n");
		
				// Convert string into const char *
				const char * msg = message.c_str();

				// Print all users
				write(fd, msg, strlen(msg));

				return;
			}		
		}

		const char * msg = "ERROR (User not in room)\r\n";
		write(fd, msg, strlen(msg));
	}
}

// Print all users in room
void IRCServer::getUsersInRoom(int fd, const char * user, const char * password, const char * args) {
	string print = "";

	// Check password
	if (checkPassword(fd, user, password)) {
		// Sort usernames in room alphabetically
		std::sort(roomData[args].begin(), roomData[args].end());

		// Go through all users in room
		for (int i = 0; i < roomData[args].size(); i++) {
			print.append(roomData[args][i]);
			print.append("\r\n");
		}
		print.append("\r\n");
		
		// Convert string into const char *
		const char * msg = print.c_str();
		
		// Print all users
		write(fd, msg, strlen(msg));
	}
}

// Print all users on server regardless of room
void IRCServer::getAllUsers(int fd, const char * user, const char * password,const  char * args) {
	string print = "";

	// Check password
	if (checkPassword(fd, user, password)) {
		// Sort usernames alphabetically
		std::sort(usernames.begin(), usernames.end());

		// Go through all users
		for (int i = 0; i < usernames.size(); i++) {
			print.append(usernames.at(i));
			print.append("\r\n");	
		}
		print.append("\r\n");
		
		// Convert string into const char *
		const char * msg = print.c_str();

		// Print all users
		write(fd, msg, strlen(msg));
	} 
}
