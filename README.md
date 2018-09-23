# InFix-Evaluator
Evaluates Infix expressions from the command line, example: "( 3 + ( 5 * log [ 7 ] ) )" 
All characters must be separated by white space, equation must be fully parenthesized.

Basic Functionality is achieved by using two stacks: one for the operators and one for the operands.
Input is read left to right, error checked, and pushed onto the correct stack until a closing parenthesis: ),},]
is reached. The appropriate number of operands is then popped and the operation is computed and pushed. 
