import subprocess
import sys

def main():
    if len(sys.argv) < 5:
        print("Usage: python run_java.py <type> <operation> <num1> <num2>")
        return

    # Build command: java MyInfArith float add 1.2 3.4
    command = ["java", "MyInfArith"] + sys.argv[1:]

    try:
        result = subprocess.run(
            command,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            check=True
        )
        print(result.stdout.strip())
    except subprocess.CalledProcessError as e:
        print("Java program failed:")
        print(e.stderr.strip())

if __name__ == "__main__":
    main()