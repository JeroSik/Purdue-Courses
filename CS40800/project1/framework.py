import argparse
import subprocess
import os
# These are some imports that I found useful when implementing this project, but you can use
# other options if you prefer
import telnetlib
import time
import sys

# TODO: handle no -f flag
# TODO: handle -v flag

def compare_outputs(response, expected):
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

    # Get configuration file
    configurationFile = "testing_environment/configurations/" + input_lines[0]

    # Runs the BFTPD server as a subprocess. Allows program to connect to server using telnetlib framwork.
    executeLine = './' +  binary_exe + ' -c ' +  configurationFile + " -D"
    proc = subprocess.Popen(executeLine, shell=True)
    time.sleep(2)

    # Check if PORT is already taken
    if proc.poll() != None:
        print("BFTPD failed to start, try changing your port")
        return 2

    # Create mapping of client number to telnet connection
    conn_dict = dict()

    # Iterate through lines of input (skip config file)
    iter_input_lines = iter(input_lines)
    next(iter_input_lines)
    res_list = []
    for lines in iter_input_lines:
        # Parse client number and request message
        client_num, req_msg = lines.split(':')
        
        # If existing connection, get client telnet. Otherwise, create new telnet connection.
        client_tn = None
        if client_num in conn_dict:
            client_tn = conn_dict.get(client_num)
        else:
            client_tn = telnetlib.Telnet('localhost', 6061)
            conn_dict[client_num] = client_tn
            res_list.append(client_tn.read_until('\r\n').strip())
        
        # Send request message and recieve response
        client_tn.write(req_msg + '\r\n')
        res_list.append(client_tn.read_until('\r\n').strip())
    
    # Close files
    input_file.close()
    output_file.close()

    # This kills the bftpd process, and all subprocesses that it created. You must run this before you start another server on the same port
    os.system("ps -u $USER | grep bftpd | grep -v grep | awk '{print $1}' | xargs -r kill -9")

    # Return comparison between response and expected output
    return compare_outputs(res_list, output_lines)

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