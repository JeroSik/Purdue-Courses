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

def get_stor(configurationFile):
    stor = None
    with open(configurationFile, 'r') as config:
        for line in config:
            line = line.strip()
            if line.startswith("ALLOWCOMMAND_STOR="):
                stor = line[19:-1]
                break

    return True if stor == 'yes' else False

def checkPWD(response, curr_dir, verbose):
    expected_1 = "257 "
    expected_1 += "\"" + os.path.abspath('./testing_environment/' + curr_dir) + "/\" "
    expected_1 += "is the current working directory."

    expected_2 = "257 "
    expected_2 += "\"" + os.path.abspath('./testing_environment/' + curr_dir) + "\" "
    expected_2 += "is the current working directory."

    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected_1))
    
    return True if (response == expected_1 or response == expected_2) else False

def computeMKD(command):
    dir_name = command.split(" ")[1]
    expected = ""

    if os.path.exists("./testing_environment/" + dir_name):
        expected += "451 Error: File exists."
    else:
        expected += "257 "
        expected += "\"" + dir_name + "\" "
        expected += "has been created."
    
    return expected

def computeRMD(command):
    dir_name = command.split(" ")[1]
    expected = ""

    if os.path.exists("./testing_environment/" + dir_name):
        if len(os.listdir("./testing_environment/" + dir_name)) != 0:
            expected += "550 "
            expected += dir_name
            expected += ": Directory not empty"
        else:
            expected += "250 OK"
    else:
        expected += "451 Error: No such file or directory."
    
    return expected

def checkSTAT(command, response, verbose):
    dir_name = command.split(" ")[1]
    expected = []

    expected.append("213-Status of " + dir_name + ":")
    if os.path.exists("./testing_environment/" + dir_name):
        expected.append(response[1])
        expected.append("213 End of Status.")
    else:
        expected.append("213-Error: No such file or directory.\n213 End of Status.")
        expected.append("")

    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected))
    
    return True if response == expected else False

def checkSIZE(command, response, verbose):
    dir_name = command.split(" ")[1]
    expected = ""

    if os.path.exists("./testing_environment/" + dir_name):
        expected = "213 " + str(os.path.getsize("./testing_environment/" + dir_name))
    else:
        expected = "550 Error: No such file or directory."

    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected))
    
    return True if response == expected else False

def checkPASV(response):
    expected = ['227', 'Entering', 'Passive', 'Mode']
    for i in range(4):
        if expected[i] != response[i]:
            return False
    
    return True

def checkNLST(response, output, pasv_mode, verbose):
    expected = []

    if pasv_mode:
        expected.append("150 BINARY data connection established.")
        expected.append("226 Directory list has been submitted.")

        dir_list = os.listdir("./testing_environment")
        dir_list.append("")
        if dir_list != output:
            return False
    else:
        expected.append("425-Unable to establish data connection.")
        if response[1][0:3] != '425':
            return False
        expected.append(response[1])
    
    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected))

    return True if response == expected else False

def checkLIST(command, response, output, pasv_mode, verbose):
    dir_name = command.split(" ")[1] if len(command.split(" ")) != 1 else "./"
    expected = []

    if pasv_mode:
        expected.append("150 BINARY data connection established.")
        expected.append("226 Directory list has been submitted.")

        cmd = ['ls', '-l', './testing_environment/' + dir_name]
        ls_proc = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        o, e = ls_proc.communicate()
        dir_list = o.split('\n')
        dir_list.remove(dir_list[0])

        exp_set = set()
        for item in dir_list:
            exp_set.add(item.split(" ")[-1])

        for item in output:
            if item.split(" ")[-1] in exp_set:
                exp_set.remove(item.split(" ")[-1])
            else:
                return False
        
        if len(exp_set) != 0:
            return False
    else:
        expected.append("425-Unable to establish data connection.")
        if response[1][0:3] != '425':
            return False
        expected.append(response[1])

    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected))

    return True if response == expected else False

def checkSTOR(command, response, pasv_mode, verbose):
    dir_name = command.split(" ")[1]
    expected = []

    if pasv_mode:
        if os.access('./testing_environment/' + dir_name, os.W_OK):
            expected.append("150 BINARY data connection established.")
            expected.append("226 File transmission successful.")
        else:
            expected.append("553 Error: Permission denied.")
            expected.append("")
    else:
        expected.append("425-Unable to establish data connection.")
        if response[1][0:3] != '425':
            return False
        expected.append(response[1])
        os.system('rm ./testing_environment/' + dir_name)

    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected))

    return True if response == expected else False

def checkRETR(command, response, output, pasv_mode, verbose):
    dir_name = command.split(" ")[1]
    expected = []

    if pasv_mode:
        if os.path.exists("./testing_environment/" + dir_name):
            expected.append("150 BINARY data connection established.")
            expected.append("226 File transmission successful.")
        else:
            expected.append("553 Error: No such file or directory.")
            expected.append("")

    else:
        expected.append("425-Unable to establish data connection.")
        if response[1][0:3] != '425':
            return False
        expected.append(response[1])

    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected))

    return True if response == expected else False

def checkREST(command, response, verbose):
    offset = command.split(" ")[1]
    expected = "350 Restarting at offset " + offset + "."

    if verbose:
        print('Response: {input}'.format(input=response))
        print('Expected: {input}'.format(input=expected))

    return True if response == expected else False

def run_testcase(binary_exe, input_filename, output_filename, verbose):
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
    isSTORDisabled = get_stor(configurationFile)

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
    out_index = -1
    rest_val = 0
    pasv_mode = False
    pasv_port = None
    curr_dir = "./"

    # Set up iteration and skip config file
    iter_input_lines = iter(input_lines)
    next(iter_input_lines)
    exit_status = 0

    # Iterate through input line-by-line
    for lines in iter_input_lines:
        # Parse client number and request message
        client_num, req_command = lines.split(':')
        
        # If existing connection, get client telnet. Otherwise, create new telnet connection.
        client_tn = None
        if client_num in conn_dict:
            client_tn = conn_dict.get(client_num)
        else:
            if not pasv_mode:
                client_tn = telnetlib.Telnet('localhost', port)
                conn_dict[client_num] = client_tn
                res_msg = client_tn.read_until('\r\n').strip()
                out_index += 1
                if res_msg != output_lines[out_index]:
                    if verbose:
                        print('Response: {input}'.format(input=res_msg))
                        print('Expected: {input}'.format(input=output_lines[out_index]))
                    exit_status = 1
                    break
            else:
                client_tn = telnetlib.Telnet('localhost', pasv_port)
                conn_dict[pasv_port] = client_tn
        
        # Send request message and recieve response
        if req_command != '':
            # Precompute expected message if necessary
            expected = ""
            if req_command.split(" ")[0] == 'mkd':
                expected = computeMKD(req_command)
            elif req_command.split(" ")[0] == 'rmd':
                expected = computeRMD(req_command)
            
            # Write to telnet and get response
            client_tn.write(req_command + '\r\n')
            res_msg = client_tn.read_until('\r\n').strip()

            # Handle different commands
            if req_command == 'pwd':
                if not checkPWD(res_msg, curr_dir, verbose):
                    exit_status = 1
                    break
            elif req_command.split(" ")[0] == 'mkd':
                if res_msg != expected:
                    if verbose:
                        print('Response: {input}'.format(input=res_msg))
                        print('Expected: {input}'.format(input=expected))
                    exit_status = 1
                    break
            elif req_command.split(" ")[0] == 'rmd':
                if res_msg != expected:
                    if verbose:
                        print('Response: {input}'.format(input=res_msg))
                        print('Expected: {input}'.format(input=expected))
                    exit_status = 1
                    break
            elif req_command.split(" ")[0] == 'stat':
                res_list = [res_msg]
                res_list.append(client_tn.read_until('\r\n').strip())
                res_list.append(client_tn.read_until('\r\n', 2).strip())
                if not checkSTAT(req_command, res_list, verbose):
                    exit_status = 1
                    break
            elif req_command.split(" ")[0] == 'size':
                if not checkSIZE(req_command, res_msg, verbose):
                    exit_status = 1
                    break
            elif req_command == 'pasv':
                pasv_mode = True
                split_msg = res_msg.split(" ")
                if not checkPASV(split_msg):
                    exit_status = 1
                    break
                A_bytes = res_msg.split(",")
                a1 = int(A_bytes[4])
                a2 = int(A_bytes[5][:-1])
                pasv_port = a1 * 256 + a2
            elif req_command == 'nlst':
                res_list = [res_msg]
                res_list.append(client_tn.read_until('\r\n').strip())
                pasv_list = []
                if pasv_mode:
                    pasv_tn = conn_dict.get(pasv_port)
                    pasv_list += pasv_tn.read_all().split('\r\n')
                if not checkNLST(res_list, pasv_list, pasv_mode, verbose):
                    exit_status = 1
                    break
                pasv_mode = False
            elif req_command.split(" ")[0] == 'list':
                res_list = [res_msg]
                res_list.append(client_tn.read_until('\r\n').strip())
                pasv_list = []
                if pasv_mode:
                    pasv_tn = conn_dict.get(pasv_port)
                    pasv_list += pasv_tn.read_all().split('\r\n')
                if not checkLIST(req_command, res_list, pasv_list, pasv_mode, verbose):
                    exit_status = 1
                    break
                pasv_mode = False
            elif isSTORDisabled and req_command.split(" ")[0] == 'stor':
                if pasv_mode:
                    pasv_tn = conn_dict.get(pasv_port)
                    pasv_tn.write("0123456789\r\n")
                    pasv_tn.close()
                res_list = [res_msg]
                res_list.append(client_tn.read_until('\r\n', 2).strip())
                if not checkSTOR(req_command, res_list, pasv_mode, verbose):
                    exit_status = 1
                    break
                pasv_mode = False
            elif req_command.split(" ")[0] == 'retr':
                res_list = [res_msg]
                res_list.append(client_tn.read_until('\r\n', 2).strip())
                pasv_list = []
                if pasv_mode and res_msg[0:3] != '553':
                    pasv_tn = conn_dict.get(pasv_port)
                    pasv_list += pasv_tn.read_all().split('\r\n')
                if not checkRETR(req_command, res_list, pasv_list, pasv_mode, verbose):
                    exit_status = 1
                    os.system('rm ./testing_environment/' + req_command.split(" ")[1])
                    break
                if os.path.exists("./testing_environment/" + req_command.split(" ")[1]) and req_command.split(" ")[1] != 'password_list':
                    os.system('rm ./testing_environment/' + req_command.split(" ")[1])
                pasv_mode = False
            elif req_command.split(" ")[0] == 'rest':
                rest_val = int(req_command.split(" ")[1])
                if not checkREST(req_command, res_msg, verbose):
                    exit_status = 1
                    break
            else:
                out_index += 1
                if res_msg != output_lines[out_index]:
                    if verbose:
                        print('Command: {input}'.format(input=lines))
                        print('Response: {input}'.format(input=res_msg))
                        print('Expected: {input}'.format(input=output_lines[out_index]))
                    exit_status = 1
                    break

                if req_command.split(" ")[0] == 'cwd':
                    curr_dir += req_command.split(" ")[1]
                elif req_command.split(" ")[0] == 'cdup':
                    curr_dir += '../'
        
    
    # Close files
    input_file.close()
    output_file.close()

    # This kills the bftpd process, and all subprocesses that it created. You must run this before you start another server on the same port
    os.system("ps -u $USER | grep bftpd | grep -v grep | awk '{print $1}' | xargs -r kill -9")

    # Return comparison between response and expected output
    return exit_status

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

    isVerbose = False
    if args.verbose != None:
        isVerbose = True

    # Run testcases on either given file or all files
    exit_status = -1
    if args.file == None:
        # Iterate through all test inputs
        for file_num in range(1,35):
            print('Running test on test_input_{num}'.format(num=file_num))
            filename = './testing_environment/testcases/test_input_' + str(file_num) + '.txt'
            exit_status = run_testcase(args.binary, filename, filename.replace('input', 'output'), isVerbose)
            if exit_status == 1:
                break
    else:
        print('Running test on {input}'.format(input=args.file))
        exit_status = run_testcase(args.binary, args.file, args.file.replace('input', 'output'), isVerbose)
    
    # if exit_status == 0:
    #     print('\033[92m' + 'PASS' + '\033[0m')
    # elif exit_status == 1:
    #     print('\033[91m' + 'FAIL' + '\033[0m')
    sys.exit(exit_status)