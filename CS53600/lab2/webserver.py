# CS 536 - Fall 2020
# Youngsik Yoon (yoon161@purdue.edu)
# Lab 2
# Description: A Web server
# from socket import *
import errno
import os
import socket
import signal
import sys
import thread
import time

def get_response_message_header(status_code, keep_connected=False):
    curr_time = time.strftime("%a, %d %b %Y %H:%M:%S", time.localtime())

    res_msg = 'HTTP/1.1 {status}\r\n'.format(status=status_code)
    res_msg += 'Date: {time}\r\n'.format(time=curr_time)
    res_msg += 'Server: Python Web Server\r\n'
    if keep_connected:
        res_msg += 'Connection: keep-alive\r\n'
    else:
        res_msg += 'Connection: close\r\n'

    if status_code == '400 Bad Request':
        res_msg += '\r\n'
        res_msg += ('<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">\r\n' + 
                    '<html>\r\n' +
                    '<head>\r\n' +
                        '<title>400 Bad Request</title>\r\n' +
                    '</head>\r\n' +
                    '<body>\r\n' +
                        '<h1>Bad Request</h1>\r\n' +
                        '<p>Your browser sent a request that this server could not understand.</p>\r\n' +
                        '<p>The request line contained invalid characters following the protocol string.</p>\r\n' +
                    '</body>\r\n' +
                    '</html>')
    elif status_code == '403 Forbidden':
        res_msg += '\r\n'
        res_msg += ('<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">\r\n' + 
                    '<html>\r\n' +
                    '<head>\r\n' +
                        '<title>403 Forbidden</title>\r\n' +
                    '</head>\r\n' +
                    '<body>\r\n' +
                        '<h1>Forbidden</h1>\r\n' +
                        '<p>You don\'t have permission to access the file on this server.</p>\r\n' +
                    '</body>\r\n' +
                    '</html>')
    elif status_code == '404 Not Found':
        res_msg += '\r\n'
        res_msg += ('<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">\r\n' + 
                    '<html>\r\n' +
                    '<head>\r\n' +
                        '<title>404 Not Found</title>\r\n' +
                    '</head>\r\n' +
                    '<body>\r\n' +
                        '<h1>Not Found</h1>\r\n' +
                        '<p>The requested URL was not found on this server.</p>\r\n' +
                    '</body>\r\n' +
                    '</html>')
    elif status_code == '505 HTTP Version Not Supported':
        res_msg += '\r\n'
        res_msg += ('<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">\r\n' + 
                    '<html>\r\n' +
                    '<head>\r\n' +
                        '<title>505 HTTP Version Not Supported</title>\r\n' +
                    '</head>\r\n' +
                    '<body>\r\n' +
                        '<h1>HTTP Version Not Supported</h1>\r\n' +
                        '<p>The HTTP protocol you rare asking for is unsupported.</p>\r\n' +
                    '</body>\r\n' +
                    '</html>')

    # Return response message
    return res_msg

def parse_request_line(req_line):
    """
    Parses through request line and ensure values are valid
        - Returns error if format is incorrect
    """
    # Check if there are 3 parts to header
    if len(req_line) != 3:
        print('Request line does not have three parts: {msg}'.format(msg=req_line))
        return '', '', '400 Bad Request'
    
    # Check if request method is GET
    req_method = req_line[0]
    if req_method != 'GET':
        print('HTTP Request not recognized: {msg}'.format(msg=req_method))
        return '', '', '400 Bad Request'
    
    # Get filename
    filename = req_line[1]

    # Check if HTTP protocol and valid version number
    version = req_line[2]
    version_split = version.split('/')
    if version_split[0] != 'HTTP':
        print('HTTP protocol not found: {msg}'.format(msg=version))
        return '', '', '400 Bad Request'
    if version_split[1] != '1.1' and version_split[1] != '1.0':
        print('HTTP Version not supported: {msg}'.format(msg=version))
        return '', '', '505  HTTP  Version  Not  Supported'

    # Return filename and version with no error
    return filename, version, ''

def parse_headers(req_msg, version):
    """
    Parses through request message line by line and ensure values are valid
        - Returns 'Connection' value
    """
    keep_connected = False

    # Iterate through each line in request message
    for line in req_msg:
        split = line.split(':')

        # Check connection header
        if split[0] == 'Connection':
            if split[1] == ' keep-alive':
                keep_connected = True
            break

    return keep_connected, ''

def parse_file(filepath):
    """
    Parse through the file given and return error if exists
        - Return file data
        - Return content type
        - Return content length
    """
    # Change default '/' to 'index.html'
    if filepath[0] != '.':
        filepath = '.' + filepath
    if filepath == './':
        filepath = './index.html'
    
    # Check if file exists
    if os.path.exists(filepath):
        try:
            # Get size of file
            content_length = os.path.getsize(filepath)
            
            # Get extension of filename
            tmp, file_extension = os.path.splitext(filepath)
            if file_extension == '.html':
                content_type = 'text/html'
            elif file_extension == '.jpg' or file_extension == '.jpeg':
                content_type = 'image/jpg'
            elif file_extension == '.gif':
                content_type = 'image/gif'
            else:
                content_type = ''
            
            # Read file data
            f = open(filepath, 'rb')
            file_data = f.read()
            f.close()

            # Return respective data with no error
            return file_data, content_length, content_type, ''
        except (OSError, IOError) as e:
            print('File error: {msg}'.format(msg=filepath))
            return '','','','403 Forbidden'                   
    else:
        print('File not found: {msg}'.format(msg=filepath))
        return '','','','404 Not Found'

def handle_client_thread(client_socket, address):
    """
    Handles incoming messages from the client through a thread.
        - Returns response message back to client
    """
    res_msg = ''
    keep_connected = False
    while True:
        # Recieve data packet from client and decode
        data = client_socket.recv(8192).decode()
        print("-------------------- REQUEST MESSAGE --------------------")
        print(data)
        print("-------------------- REPONSE DATA --------------------")
        if not data:
            res_msg = get_response_message_header('400 Bad Request')
            break

        # Split request message by lines
        req_msg = data.splitlines()

        # Parse request line and get filename and version
        filename, version, error = parse_request_line(req_msg[0].split(' '))
        if error != '':
            res_msg = get_response_message_header(error)
            break
        print('Filename: {msg}'.format(msg=filename))
        print('Version: {msg}'.format(msg=version))

        # Get connection information from messages
        keep_connected, error = parse_headers(req_msg, version)
        if error != '':
            res_msg = get_response_message_header(error)
            break
        print('Keep-connected: {msg}'.format(msg=keep_connected))

        # Parse filename and get file data, content length, and content type
        file_data, content_length, content_type, error = parse_file('.' + filename)
        if error != '':
            res_msg = get_response_message_header(error)
            break
        print('Content type: {msg}'.format(msg=content_type))
        print('Content length: {msg}'.format(msg=content_length))

        # Create response message with OK header
        res_msg = get_response_message_header('200 OK', keep_connected)
        res_msg += 'Content-Type: {msg}\r\n'.format(msg=content_type)
        res_msg += 'Content-Length: {msg}\r\n'.format(msg=content_length)
        res_msg += '\r\n'
        res_msg += file_data
        break

    # Send response to client
    client_socket.send(res_msg)

    # Close client socket connection
    if not keep_connected:
        client_socket.close()

def server_program(port):
    """
    Creates a server socket with the given port argument.
        - Server will connect up to 5 clients.
        - Server will receive HTTP request from client.
        - Server will send HTTP response back to client.
    """
    # Get host name of current machine
    host = socket.gethostname()

    # Create socket object and bind to address
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((host, port))

    # Listen to connections made to socket
    server_socket.listen(5)
    print('Listening on {h}: {p}'.format(h=host,p=port))

    # Continue accepting new connections
    while True:
        # Accept new client connection
        conn, address = server_socket.accept()
        print("-------------------- New Connection --------------------")
        print('Connection from: {msg}'.format(msg=address))

        # Create new thread with client connection
        thread.start_new_thread(handle_client_thread, (conn, address))
    
    # Close server socket
    server_socket.close()

if __name__ == '__main__':
    # Check if valid arguments
    if len(sys.argv) != 2:
        sys.exit('ArgumentError: Wrong number of arguments (given ' + str(len(sys.argv)) + ', expected 2)\n' +
                 '    python server.py <port>\n' +
                 '    <port>: Port number 1024~65535')
    if int(sys.argv[1]) < 1024 or int(sys.argv[1]) > 65535:
        sys.exit('ArgumentError: Port out of range\n' +
                 '    python server.py <port>\n' +
                 '    <port>: Port number 1024~65535')

    # Get port number from user input
    port = int(sys.argv[1])

    # Start HTTP server with given port number
    server_program(port)