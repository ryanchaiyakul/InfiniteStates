# InFiniteStates

![Java CI with Gradle](https://github.com/ryanchaiyakul/InFiniteStates/workflows/Java%20CI%20with%20Gradle/badge.svg)

## Software Architecture

### Finite State Machines

[article](https://medium.com/@mlbors/what-is-a-finite-state-machine-6d8dec727e2c)

### Subsystems

Each subsystem is an individual FSM that takes inputs from the output registers (They are the output of the teleop/processor FSMs). Every cycle, they read the output registers and set their motors and solenoids.

### Registers

A register class modifies the way a variable is set and accessed. This is used mainly for shared variables between different notifiers (threads) to prevent data race conditions. Currently, all registers are declared in the class Registers as final global constants.

### Update Register

An update register will not change the value until the update function is called. As the FSMs are output latched, update will be called by the looper which sets the register.

### Resetable Register

A resetable register will have its value set to its reset value (passed in the constructor) when requested. This is used to reset the registers when starting or resetting.

### Stoppable Register

A stoppable register will have its value set to its stop value (passed in the constructor) when requested. This is used to set the register to a stop value before executing a final duty cycle.

### Set Once Register

Self explanatory. Only allows the value to be set once.

## Naming Convention

### Constants

```java
final static double kConstant = 3.0;
```

Constants will be camel cased and will start with a "k".

### Private Variables

```java
private double mPrivate = 2.0;
```

Private variables will be camel cased and will start with a "m".
