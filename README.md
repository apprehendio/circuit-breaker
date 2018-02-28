# Circuit Breaker Demo Project

This is a demo project to show the use of circuit breakers.  It demonstrates the
way circuitbreakers work manually with HystrixCommands, with an annotation,
and with the builtin features of the Netflix Feign Client.

## Requirements

You will need JDK 1.8 to compile and run this project

## Building the Project

In order to build the project run `./mk build`

## Running the project

Run `./mk bootRun` to run the project

## Demonstration

While running the project use the following commands to demonstrate
the circuit breaker functionality.

### API

Use the following API to demonstrate the use of the cicuit breaker

#### Unsafe call
`curl "localhost:8761/add?addend1=2&addend2=3"`
This adds the two numbers in SketchyService

#### Safe call
`curl "localhost:8761/safe-add?addend1=2&addend2=3"`
This calls add via an http call back to add with a
circuit breaker in effect based on the "safety-mode"

#### Toggle Failure
`curl -XPOST "localhost:8761/toggle-failure"`

#### Toggle Slowness
`curl -XPOST "localhost:8761/toggle-slowness"`

#### Set Safety Mode
`curl -XPOST "localhost:8761/set-safety-mode=[BUILTIN | COMMAND | ANNOTATION]"`
Sets the safety mode or the method by which the circuit breaker
is employed.**

### Demo Procedure

1. Call the unsafe add to watch it work
2. Call the safe add to watch it work
3. Call toggle-failure
4. Call the unsafe add to see it fail
5. Call the safe add to see it succeed with
the fallback watch the console and see the stacktrace
6. Call the safe add in a loop to trip the
circuit breaker.  Notice how the debug log in the
add method stops getting emited
7. Call toggle-failure
8. Call safe-add again.  Notice how the debug starts
to be emitted

** Kotlin is not currently compatible with AspectJ which causes
problems for the annotation.
