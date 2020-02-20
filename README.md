# Finite State Machine
Functional'ish style finite state machine (FSM) pattern (akka inspired).

I have had the opportunity to use the finite state machine pattern implemented in
the akka library https://doc.akka.io/docs/akka/current/fsm.html many times. I liked the solution proposed 
by its authors (especially DSL) so much that I decided to transfer it from the AKKA actors' model.
This library is just a subset of functionalities implemented in pure java so it can be used in any java application.

**IMPORTANT**: this implementation is based on akka classic actor model FSM. 

# Usage
Sample usage can be found here: [SimpleFsmTest.java](/src/test/java/org/dsinczak/fp/fsm/SimpleFsmTest.java).
