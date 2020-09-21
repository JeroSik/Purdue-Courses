# CS 536 - Fall 2020
# Youngsik Yoon (yoon161@purdue.edu)
# Lab 2
# Description: A Web server
# from socket import *
import socket
import sys
import thread

def handle_client_thread(client_socket, address):
    while True:
        # Recieve data packet from client and decode
        data = client_socket.recv(1024).decode()
        if not data:
            break

        request_method = data.split(' ')[0]
        print("Method: {m}".format(m=request_method))
        print("Request Body: {b}".format(b=data))
        break

def server_program(port):
    # Create socket object and bind to address
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('localhost', port))

    # Listen to connections made to socket
    server_socket.listen(5)
    print("Listening on", ('localhost', port))

    # Continue accepting new connections
    while True:
        # Accept new client connection
        client_socket, address = server_socket.accept()
        print("Connection from: " + str(address))

        # Create new thread with client connection
        # thread.start_new_thread(client_thread, (conn, address))

        #NOTE: 1
    
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

    # Start HTTP server with given port number
    server_program(port)



###############################################
#                     NOTES
###############################################
''' 1 '''
# clientsocket.send("HTTP/1.1 200 OK\n"
#          +"Content-Type: text/html\n"
#          +"\n" # Important!
#          +"<html><body>Hello World</body></html>\n")
#         clientsocket.shutdown(SHUT_WR)
#         clientsocket.close()