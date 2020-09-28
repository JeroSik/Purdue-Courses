import argparse
import subprocess
import os


# These are some imports that I found useful when implementing this project, but you can use
# other options if you prefer
import telnetlib
import time
import sys

parser = argparse.ArgumentParser(description="test cases for bftpd server")
parser.add_argument('-p', action="store", dest="binary")
parser.add_argument('-f', action="store", dest="file")
parser.add_argument('-v', action="store", dest="verbose")

args = parser.parse_args()

if args.binary == None:
    print("Usage: python framework.py -p binary [-c conf] [-f file] [-v verbose]")
    sys.exit(2)#returns exit code 2, if it can't run the program with the given arguments

configurationFile = "testing_environment/configurations/bftpd.conf" #This should be parsed from the test case file

executeLine = './' +  args.binary + ' -c ' +  configurationFile + " -D"

proc = subprocess.Popen(executeLine, shell=True) #Runs the BFTPD server as a subprocess. Your program can now connect to it. The python telnetlib framework would be useful for this. 
time.sleep(2)
if proc.poll() != None:
    print("BFTPD failed to start, try changing your port")
    exit(2)

#run your tests here
os.system("ps -u $USER | grep bftpd | grep -v grep | awk '{print $1}' | xargs -r kill -9") #This kills the bftpd process, and all subprocesses that it created. You must run this before you start another server on the same port

            

    



