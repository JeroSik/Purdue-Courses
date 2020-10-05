import argparse
import os
import subprocess
import sys
import telnetlib
import time

# TODO: handle no -f flag
# TODO: handle -v flag
# Specify which line that broke i.e. expected vs response

def get_port(configurationFile):
    port = None
    with open(configurationFile, 'r') as config:
        for line in config:
            line = line.strip()
            if line.startswith("PORT="):
                port = line[6:-1]
                break

    return int(port)

def compare_outputs(response, passive, expected):
    # print('----- RESPONSE -----')
    # print(response)
    # print('----- PASSIVE -----')
    # print(passive)
    # print('----- EXPECTED -----')
    # print(expected)

    response.append("==================================================")
    for line in passive:
        if line != '':
            response.append(line)

    print('----- RESPONSE -----')
    print(response)
    print('----- EXPECTED -----')
    print(expected)
    
    return 0 if response == expected else 1

def run_testcase(binary_exe, input_filename, output_filename):
    """
    Runs a testcase given the input testcase on the FTP server
        - Returns 0 if response is expected
        - Returns 1 if response is unexpected
        - Returns 2 if program could not run correctly
    """
    # Initialize file pointers and get list of lines
    input_file = open(input_filename, 'r')
    input_lines = [line.strip() for line in input_file.readlines()]
    output_file = open(output_filename, 'r')
    output_lines = [line.strip() for line in output_file.readlines()]

    # Get configuration file and port
    configurationFile = "testing_environment/configurations/" + input_lines[0]
    port = get_port(configurationFile)

    # Runs the BFTPD server as a subprocess. Allows program to connect to server using telnetlib framwork.
    executeLine = './' +  binary_exe + ' -c ' +  configurationFile + " -D"
    proc = subprocess.Popen(executeLine, shell=True)
    time.sleep(2)

    # Check if PORT is already taken
    if proc.poll() != None:
        print("BFTPD failed to start, try changing your port: {msg}".format(msg=port))
        return 2

    # Initialize variables needed to test input
    conn_dict = dict()
    res_list = []
    pasv_list = []
    pasv_mode = False

    # Iterate through lines of input (skip config file)
    iter_input_lines = iter(input_lines)
    next(iter_input_lines)
    for lines in iter_input_lines:
        # Parse client number and request message
        client_num, req_msg = lines.split(':')
        
        # If existing connection, get client telnet. Otherwise, create new telnet connection.
        client_tn = None
        if client_num in conn_dict:
            client_tn = conn_dict.get(client_num)
        else:
            client_tn = telnetlib.Telnet('localhost', port)
            conn_dict[client_num] = client_tn
            if not pasv_mode:
                res_list.append(client_tn.read_until('\r\n').strip())
        
        print(conn_dict)
        # Send request message and recieve response
        if req_msg != '':
            client_tn.write(req_msg + '\r\n')
            res_msg = client_tn.read_until('\r\n').strip()
            res_list.append(res_msg)
            if req_msg == "QUIT":
                res_msg = client_tn.read_until('\r\n').strip()
                res_list.append(res_msg)

            code, msg = res_msg.split(" ", 1)
            if code == "227":                   # PASV
                pasv_mode = True
                A_bytes = msg.split(",")
                a1 = int(A_bytes[4])
                a2 = int(A_bytes[5][:-1])
                port = a1 * 256 + a2
                continue

            if pasv_mode:
                pasv_tn = conn_dict.get('2')
                pasv_list += pasv_tn.read_all().split('\r\n')
    
    # Close files
    input_file.close()
    output_file.close()

    # This kills the bftpd process, and all subprocesses that it created. You must run this before you start another server on the same port
    os.system("ps -u $USER | grep bftpd | grep -v grep | awk '{print $1}' | xargs -r kill -9")

    # Return comparison between response and expected output
    return compare_outputs(res_list, pasv_list, output_lines)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="test cases for bftpd server")
    parser.add_argument('-p', action="store", dest="binary")
    parser.add_argument('-f', action="store", dest="file")
    parser.add_argument('-v', action="store", dest="verbose")
    args = parser.parse_args()

    # Returns exit code 2, if it can't run the program with the given arguments
    if args.binary == None:
        print("Usage: python framework.py -p binary [-c conf] [-f file] [-v verbose]")
        sys.exit(2)

    # Run testcases on either given file or all files
    exit_status = -1
    if args.file == None:
        # Iterate through all test inputs
        for filename in os.listdir('./testing_environment/testcases'):
            if filename.startswith('test_input'):
                print(filename)
    else:
        print('Running test on {input}'.format(input=args.file))
        exit_status = run_testcase(args.binary, args.file, args.file.replace('input', 'output'))
    
    if exit_status == 0:
        print('\033[92m' + 'PASS' + '\033[0m')
    elif exit_status == 1:
        print('\033[91m' + 'FAIL' + '\033[0m')
    sys.exit(exit_status)