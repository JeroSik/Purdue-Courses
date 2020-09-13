# CS 536 - Fall 2020
# Youngsik Yoon (yoon161@purdue.edu)
# Lab 1
# Description: A basic echo client
import socket
import sys

def client_program(host, port):
    """
    Creates a client connection to the server socket with the given host and port argument.
    Receives incoming echo from server.
    """
    client_socket = socket.socket()
    client_socket.connect((host, port))
    
    # Continue messaging server socket
    while True:
        # Get input from user
        msg = raw_input(" -> ")

        # Send message to server and receive response
        client_socket.send(msg)
        res = client_socket.recv(1024)

        print('Server: ' + res)

        if str(msg).lower().strip() == 'quit':
            break

    # Close client socket connection
    client_socket.close()


if __name__ == '__main__':
    # Check if valid arguments
    if len(sys.argv) != 3:
        sys.exit("ArgumentError: Wrong number of arguments (given " + str(len(sys.argv)) + ", expected 3)\n" +
                 "    python client.py <host> <port>\n" +
                 "    host  : Host name of server machine\n"
                 "    <port>: Port number 1024~65535")
    if int(sys.argv[2]) < 1024 or int(sys.argv[2]) > 65535:
        sys.exit("ArgumentError: Port out of range\n" +
                 "    python client.py <host> <port>\n" +
                 "    host  : Host name of server machine\n"
                 "    <port>: Port number 1024~65535")

    # Get host and port number from user input
    host = sys.argv[1]
    port = int(sys.argv[2])

    # Start echo client with given host and port number
    client_program(host, port)