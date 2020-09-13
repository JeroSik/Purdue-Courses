# CS 536 - Fall 2020
# Youngsik Yoon (yoon161@purdue.edu)
# Lab 1
# Description: A basic echo server
import socket
import sys
import thread

def client_thread(conn, address):
    """
    Handles incoming messages from the client through a thread.
    """
    # Continue listening to new messages
    while True:
        # Receive incoming data stream
        msg = conn.recv(1024)
        # Check if incoming message is a string
        if not msg:
            break
        
        print("Client: " + str(msg))

        # Check for special messages, otherwise echo message to client
        if str(msg).lower().strip() == 'hello':
            conn.send("Welcome!")
        elif str(msg).lower().strip() == 'quit':
            conn.send("Goodbye! Connection with server will close.")
            break
        else:
            conn.send(msg)

    # Close client connection
    conn.close()


def server_program(port):
    """
    Creates a server socket with the given port argument.
        - Server will send recieved message back to client.
        - If the message from the client is ...
            - "HELLO", then server will send "Welcome!".
            - "QUIT", then server stops connection with the client.
    """
    # Get host name of current machine
    host = socket.gethostname()

    # Create socket object and bind to address
    server_socket = socket.socket()
    server_socket.bind((host, port))

    # Listen to connections made to socket
    server_socket.listen(5)
    print("Listening on", (host, port))

    # Continue accepting new connections
    while True:
        # Accept new client connection
        conn, address = server_socket.accept()
        print("Connection from: " + str(address))

        # Create new thread with client connection
        thread.start_new_thread(client_thread, (conn, address))
    
    # Close server socket
    server_socket.close()


if __name__ == '__main__':
    # Check if valid arguments
    if len(sys.argv) != 2:
        sys.exit("ArgumentError: Wrong number of arguments (given " + str(len(sys.argv)) + ", expected 2)\n" +
                 "    python server.py <port>\n" +
                 "    <port>: Port number 1024~65535")
    if int(sys.argv[1]) < 1024 or int(sys.argv[1]) > 65535:
        sys.exit("ArgumentError: Port out of range\n" +
                 "    python server.py <port>\n" +
                 "    <port>: Port number 1024~65535")

    # Get port number from user input
    port = int(sys.argv[1])

    # Start echo server with given port number
    server_program(port)