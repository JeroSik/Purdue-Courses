import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

final class ChatClient {
  private ObjectInputStream sInput;
  private ObjectOutputStream sOutput;
  private Socket socket;

  private final String server;
  private final String username;
  private final int port;

  private ChatClient(String server, int port, String username) {
    this.server = server;
    this.port = port;
    this.username = username;
  }

  /*
   * This starts the Chat Client
   */
  private boolean start() {
    // Create a socket
    try {
      socket = new Socket(server, port);
    } catch (IOException e) {
      System.out.println("Client unable to connect to server.");
    }

    // Create your input and output streams
    try {
      sInput = new ObjectInputStream(socket.getInputStream());
      sOutput = new ObjectOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      System.out.println("Client unable to establish i/o streams with the server.");
    }

    // This thread will listen from the server for incoming messages
    Runnable r = new ListenFromServer();
    Thread t = new Thread(r);
    t.start();

    // After starting, send the clients username to the server.
    try {
      sOutput.writeObject(username);
    } catch (IOException e) {
      System.out.println("Unable to send client username to the server.");
    }

    return true;
  }

  /**
   * Returns a string representation of the current time.
   * @return
   */
  public static String getCurrentTime() {
    return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
  }


  /*
   * This method is used to send a ChatMessage Objects to the server
   */
  private void sendMessage(ChatMessage msg) {
    try {
      sOutput.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /*
   * To start the Client use one of the following command
   * > java ChatClient
   * > java ChatClient username
   * > java ChatClient username portNumber
   * > java ChatClient username portNumber serverAddress
   *
   * If the portNumber is not specified 1500 should be used
   * If the serverAddress is not specified "localHost" should be used
   * If the username is not specified "Anonymous" should be used
   */
  public static void main(String[] args) throws IOException {
    // Get proper arguments and override defaults
    String userName = "";
    int portNumber = 1500;
    String serverAddress = "localhost";

    // Assigning the terminal arguments to their corresponding fields.
    try {
      userName = args[0];
      portNumber = Integer.parseInt(args[1]);
      serverAddress = args[2];
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid argument. Check port number and server address.");
    }

    catch (ArrayIndexOutOfBoundsException e) {
    }

    // Create your client and start it
    ChatClient client = new ChatClient(serverAddress, portNumber, userName);
    try {
      client.start();
    } catch (Exception e) {
      System.out.println("Server is not running!");
      return;
    }

    Scanner sc = new Scanner(System.in);
    // Loops until the client no longer wishes to participate in the chat.
    while (sc.hasNextLine()) {
      String msg = sc.nextLine();

      if(msg.equals("")) continue;

        // If the user chooses to log out.
      else if(msg.equalsIgnoreCase("/logout")) {
        client.sendMessage(new ChatMessage(1, client.username + " logged out."));
        client.sInput.close();
        client.sOutput.close();
        client.socket.close();
        System.exit(0);
      }

      else if(msg.equalsIgnoreCase("/list")) {
        client.sendMessage(new ChatMessage(2, client.username));
      }

      else if(msg.startsWith("/msg")) {
        String data = msg.substring(msg.indexOf(" ") + 1);
        String recipient = data.substring(0, data.indexOf(" "));
        if (!client.username.equals(recipient)) {
          String message = data.substring(data.indexOf(" ") + 1);
          client.sendMessage(new ChatMessage(3, client.username + ":" + message, recipient));
          System.out.printf("%s %s -> %s: %s\n", ChatClient.getCurrentTime(), client.username, recipient, message);
        } else {
          System.out.printf("User is not allowed to direct message themselves.\n");
        }
      }

      else {
        client.sendMessage(new ChatMessage(0, msg));
      }
    }
  }


  /*
   * This is a private class inside of the ChatClient
   * It will be responsible for listening for messages from the ChatServer.
   * ie: When other clients send messages, the server will relay it to the client.
   */
  private final class ListenFromServer implements Runnable {
    public void run() {
      try {
        while (true) {
          String msg = (String) sInput.readObject();
          System.out.println(msg);
        }
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("Server has closed the connection.");
        System.exit(-1);
      }
    }
  }
}