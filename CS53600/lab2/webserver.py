# CS 536 - Fall 2020
# Youngsik Yoon (yoon161@purdue.edu)
# Lab 2
# Description: A Web server
import socket
import sys
import thread

def createServer(port):
    serversocket = socket(AF_INET, SOCK_STREAM)
    serversocket.bind(('localhost',port))
    serversocket.listen(5)
    while(1):
        (clientsocket, address) = serversocket.accept()
        clientsocket.send("HTTP/1.1 200 OK\n"
         +"Content-Type: text/html\n"
         +"\n" # Important!
         +"<html><body>Hello World</body></html>\n")
        clientsocket.shutdown(SHUT_WR)
        clientsocket.close()

    serversocket.close()

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

    createServer(port)

    # Start echo server with given port number
    # server_program(port)
