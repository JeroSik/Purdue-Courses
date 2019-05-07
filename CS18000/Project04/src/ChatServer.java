import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

final class ChatServer {
  private static int uniqueId = 0;
  private final List<ClientThread> clients = new ArrayList<>();
  private final int port;
  private String badWordsFileName;
  private ChatFilter filter;
  /**
   * Constucts a ChatServer object with the specified port number.
   * @param port
   */
  private ChatServer(int port) {
    this.port = port;
  }

  /**
   * Constructs a ChatServer object with the specified port number and the word filter list.
   * @param port
   * @param badWordsFileName
   */
  public ChatServer(int port, String badWordsFileName) {
    this.port = port;
    this.badWordsFileName = badWordsFileName;
    filter = new ChatFilter(badWordsFileName);
  }

  /**
   * Broadcasts the message to all clients in the chat.
   * @param message
   * @throws IOException
   */
  private void broadcast(String message) throws IOException {
    synchronized (new Object()) {
      for(ClientThread clientThread : clients) {
        clientThread.writeMessage(filter.filter(getCurrentTime() + " " + message));
      }
    }
  }

  /**
   * Sends the message to the specified username.
   * @param message
   * @param username
   */
  public void directMessage(String message, String username) {

    synchronized (new Object()) {
      for(ClientThread clientThread : clients) {
        if(clientThread != null && clientThread.username.equals(username)) {
          String[] data = message.split(":");
          clientThread.writeMessage(String.format("%s %s -> %s: %s", getCurrentTime(),
                  data[0],
                  username,
                  data[1]));
        }

      }
    }

  }

  /**
   * Removes the user with the specified id.
   * @param id
   */
  private void remove(int id) {
    for(int i = 0; i < clients.size(); i++) {
      if(clients.get(i).id == id)
        clients.remove(i);
    }
  }

  /**
   *
   * @return
   */
  public String getCurrentTime() {
    return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
  }

  /*
   * This is what starts the ChatServer.
   * Right now it just creates the socketServer and adds a new ClientThread to a list to be handled
   */
  private void start() {
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      System.out.printf("Banned words file: %s\n", badWordsFileName);
      System.out.println("Banned words: ");

      try {
        Scanner sc = new Scanner(new File(badWordsFileName));
        while (sc.hasNextLine()) {
          System.out.println(sc.nextLine());
        }
      } catch (IOException e) {
        System.out.println("Unable to read from the file specified.");
      }

      System.out.println();

      while (true) {
        System.out.printf("%s Server waiting for Clients on port %d.\n", getCurrentTime(), port);
        Socket socket = serverSocket.accept();
        Runnable r = new ClientThread(socket, uniqueId++);
        Thread t = new Thread(r);
        clients.add((ClientThread) r);
        t.start();
        System.out.printf("%s %s just connected\n", getCurrentTime(), ((ClientThread) r).username);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void close() {

  }

  /*
   *  > java ChatServer
   *  > java ChatServer portNumber
   *  If the port number is not specified 1500 is used
   */
  public static void main(String[] args) throws Exception {
    int portNumber = 1500;
    String fileList = "";

    try {
      portNumber = Integer.parseInt(args[0]);
      fileList = args[1];
      if(!fileList.endsWith(".txt")) {
        throw new Exception("Invalid file name.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid argument. Check port number");
    }
    catch (ArrayIndexOutOfBoundsException e) {
    }

    ChatServer server = new ChatServer(portNumber, fileList);
    server.start();
  }


  /*
   * This is a private class inside of the ChatServer
   * A new thread will be created to run this every time a new client connects.
   */
  private final class ClientThread implements Runnable {
    Socket socket;
    ObjectInputStream sInput;
    ObjectOutputStream sOutput;
    int id;
    String username;
    ChatMessage cm;

    private ClientThread(Socket socket, int id) {
      this.id = id;
      this.socket = socket;
      try {
        sOutput = new ObjectOutputStream(socket.getOutputStream());
        sInput = new ObjectInputStream(socket.getInputStream());
        username = (String) sInput.readObject();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    private boolean writeMessage(String message) {
      if(!socket.isConnected()) return false;

      try {
        sOutput.writeObject(message);
      } catch (IOException e) {
        System.out.println("Message not sent to client!");
      }

      return true;
    }

    /*
     * This is what the client thread actually runs.
     */
    @Override
    public void run() {
      // Read the username sent to you by client
      while (true) {
        try {

          cm = (ChatMessage) sInput.readObject();
          // When the client sends in a logout request.
          if(cm != null && cm.getType() == 1) {
            remove(this.id);
            System.out.printf("%s disconnected with a LOGOUT message.\n", username);
            break;
          }

          // When the client sends in a list request.
          else if(cm != null && cm.getType() == 2) {
            String out = "";
            for(int i = 0; i < clients.size(); i++) {
              if(!clients.get(i).username.equalsIgnoreCase(this.username))
                out += clients.get(i).username + ", ";
            }

            // Check for comma when there is only one other user.
            if(out.endsWith(", ")) {
              out = out.substring(0, out.length() - 2);
            }

            writeMessage(getCurrentTime() + " List of users connected right now: " + out);
          }

          // When the client sends in a direct message request.
          else if(cm != null && cm.getType() == 3) {
            String[] data = cm.getMessage().split(":");
            directMessage(cm.getMessage(), cm.getRecipient());
            //System.out.printf("%s %s -> %s: %s\n", getCurrentTime(), data[0], cm.getRecipient(), data[1]);
            System.out.println(filter.filter(getCurrentTime() + " " + data[0] + " -> " + cm.getRecipient() + ": " + data[1]));
          }

          // When the client sends in a normal message.
          else {
            try {
              broadcast(username + ": " + cm.getMessage());
              System.out.println(filter.filter(getCurrentTime() + " " + username + ": " + cm.getMessage()));
            } catch (IOException e) {
              break;
            }
          }
        } catch (IOException e) {
          remove(this.id);
          System.out.printf("%s disconnected with a LOGOUT message.\n", username);
          return;
        }
        catch (ClassNotFoundException e) {
          e.printStackTrace();
          break;
        }

      }
    }
  }
}