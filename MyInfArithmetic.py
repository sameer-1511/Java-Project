import subprocess
import sys

def main():
    if len(sys.argv) != 5:
        print("Usage: python run_myinfarith.py <int/float> <add/sub/mul/div> <number1> <number2>")
        return

    arg1, arg2, arg3, arg4 = sys.argv[1:]


    #subprocess.run(["ant"], shell=True, check=True)

    run = ["ant", "run", "-Darg1=" + arg1, "-Darg2="+arg2, "-Darg3="+arg3, "-Darg4="+arg4 ]
    subprocess.run(run, shell=True, check=True)

if __name__ == "__main__":
    main()
