
import sys
import subprocess

compileJava = subprocess.run(["javac","./arbitraryarithmetic/AFloat.java","./arbitraryarithmetic/AInteger.java","./MyInfArith.java"],text=True)

if compileJava.returncode==0 :
    print("Compiled Successfully")
else:
    print("Compilation failed")
    exit(1)


if len(sys.argv[1:])!=0:
    print("Running MyInfArith as arguments were provided")
    runjava = subprocess.run(["java","MyInfArith"]+sys.argv[1:],text=True)