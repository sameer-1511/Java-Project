# MyInfArith

**MyInfArith** is a Java project that implements arbitrary precision arithmetic for integers and floating-point numbers using custom data types.

## Features

- Supports large number operations beyond Java's built-in limits.
- Handles both integer and floating-point arithmetic.
- Operations: `add`, `sub`, `mult`, `div`

## Structure

The project includes the following files:

- `MyInfArith.java` — Main class to parse input and invoke operations.
- `arbitraryarithmetic/AInteger.java` — Implements arbitrary precision integer arithmetic.
- `arbitraryarithmetic/AFloat.java` — Implements arbitrary precision floating-point arithmetic.
- `MyIntArithmetic.py` - Python script that automates compilation and execution of the Java code using Apache Ant.
- `build.xml` - The build system contains targets to Compilr, Clean, Run, Package compiled classes.

## How to Run

Compile and run using Java:

**Run these commands in the terminal from the parent folder:**

```bash
javac MyInfArith.java
java MyInfArith <type> <operation> <number1> <number2>
```

Another Method for compile and run is by using python script.
To compile and run:
```bash
python MyInfArithmetic <type> <operation> <number1> <number2>
```
`ant` compiles and saves the class files in a different directory.
`ant jar` packages the compiled classes.

