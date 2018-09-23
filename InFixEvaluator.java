import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Utkarsh on 8/30/18.
 * Revised by Geoerge Adams on 09/02/18.
 */

public class InFixEvaluator
{

    public String[] availableOperators = {"+", "-", "*", "/", "^", "cos", "sin", "log", "(", ")", "{", "}", "[", "]"};

    public Double evaluator(String str) throws Exception
    {

        Stack<Double> operands = new Stack<>(20);
        Stack<String> operators = new Stack<>(20);

       int i = 0;
       String word = "";
       String input[] = new String[(str.length() / 2) + 1];
       int index = 0;


        //Check if str contains any sets of empty parenthesis
       if(str.contains("( )") || str.contains("[ ]") || str.contains("{ }")) throw new Exception("Invalid expression");

        //Reads str and puts each valid input into input[], throws error if incorrect input
       while (i < str.length()) {
           if(str.charAt(i) != ' '){
               word += str.charAt(i);

           } else {
               if(checkOperator(word) == false && checkNumber(word) == false){
                   throw new Exception("Invalid expression");
               } else {
                   input[index] = word;
                   index++;
                   word = "";
               }

           }

           i++;
       } //end While Loop

        input[index] = word;

        //The following loop performs calculations, the input has been checked for valid inputs but not for valid parenthesis usage

        double a;
        double b;
        String op; //operator
        String op2; //operator 2
        char par = 0; //parenthesis : (, {, [
        String temp; //holds object in input[] being evaluated

        for(int k = 0; k <= index; k++){

            temp = input[k];

            //Catches errors in parenthesis: ex: ((3+)3), (3(+)3), (3(+3))
            if(k > 0) {
                if(errorCheck(temp, input[k - 1]) == -1) throw new Exception("Invalid expression");
            }

            //if temp is an operator
            if(checkOperator(temp)){

                //if closing paren we must preform operation and check for sufficient + correct opening paren
                if(temp.charAt(0) == ')' || temp.charAt(0) == ']'|| temp.charAt(0) == '}'){

                    if(temp.charAt(0) == ')') par = 40;
                    else if(temp.charAt(0) == ']') par = 91;
                    else if(temp.charAt(0) == '}') par = 123;
                    op = operators.pop();

                    int numOps;

                    if((op.charAt(0) == 40) || (op.charAt(0) == 91) || (op.charAt(0) == 123)){
                        numOps = parseOperator(par, op);
                    } else {
                        op2 = operators.pop();
                        numOps = parseOperator(par, op, op2);
                    }

                    if(numOps == -1) throw new Exception("Invalid expression");

                    if(numOps == 1){
                        a = operands.pop();
                        operands.push(doOp(op, a));
                    } else if (numOps == 2){
                        b = operands.pop();
                        a = operands.pop();
                        operands.push(doOp(op, a, b));
                    } //numOps is 3 if here, do nothing


                } else {

                    operators.push(temp);
                }
            } else {
                operands.push(Double.parseDouble(temp));
            }
        }


        if(operands.size() != 1 || operators.size() != 0){
            throw new Exception("Invalid expression");
        }

        return operands.pop();
    }


    public boolean checkOperator(String word){              // checks if Operator is in arr available Operators
        for(int j = 0; j < availableOperators.length; j++){
            if(word.equals(availableOperators[j])) return true;
        }

        return false;
    }

    public boolean checkNumber(String word){                // checks if Number is valid, ex: 56b3 returns false
        for(int j = 0; j < word.length(); j++){
            if( word.charAt(j) != 46 && (word.charAt(j) < 48 || word.charAt(j) > 57) ) return false;
        }

        return true;
    }

    //  parseOperator: an overloaded method. If the first op popped is a paren, then it is passed to the first parseOp, where it is checked for matching paren
    public int parseOperator(char par, String op){
        int a = op.charAt(0);

        if(a == 40 && par != 40) return -1;
        if(a == 91 && par != 91) return -1;
        if(a == 123 && par != 123) return -1;

        return 3;
    }

    //If first op popped is not paren, then another is popped into op2, both are passed. op is a operator and op2 must be a paren, op2 is checked for matching paren and op is processed to return number of operands
    public int parseOperator(char par, String op, String op2){
        int a = op2.charAt(0);

        if(a == 40 && par != 40) return -1;
        if(a == 91 && par != 91) return -1;
        if(a == 123 && par != 123) return -1;

        int opIndex = 0;

        for(int i = 0; i < availableOperators.length; i++){
            if(op.equals(availableOperators[i])){
                opIndex = i;
                break;
            }
        }

        if(opIndex <= 4) return 2;

        return 1;

    }

    //doOp preforms the operation, overloaded based on number of operands
    public double doOp(String op, double a){
        char m = op.charAt(0);

        if(m == 'c') return Math.cos(a);
        if(m == 's') return Math.sin(a);

        return Math.log(a);
    }

    public double doOp(String op, double a, double b){
        char m = op.charAt(0);

        if(m == '+') return a + b;
        if(m == '-') return a - b;
        if(m == '*') return a * b;
        if(m == '/') return a / b;
        if(m == '^') return Math.pow(a, b);

        return -1;
    }

    public int errorCheck(String current, String last){

        int cur = 0; //cur and las will stay at 0 if they are numbers
        int las = 0;

        for(int j = 0; j < availableOperators.length; j++){

            if(current.equals(availableOperators[j])){
                if( j < 5) cur = 3;
                if( ( j >= 5 ) && ( j < 8 )) cur = 4;
                if( j == 8 || j == 10 || j == 12) cur = 1;
                if( j == 9 || j == 11 || j == 13) cur = 2;
            }

            if(last.equals(availableOperators[j])){
                if( j < 5) las = 3;
                if( ( j >= 5 ) && ( j < 8 )) las = 4;
                if( j == 8 || j == 10 || j == 12) las = 1;
                if( j == 9 || j == 11 || j == 13) las = 2;
            }
        } // for loop assigns values to various categories of operators

        //use assigned cur and las value to check for incompatible orders
        if(cur == 1 && las == 0) return -1; // opening paren preceded directly by a number
        if(cur == 3 && las == 1) return -1; // =*/-^ preceded directly by an opening paren
        if(cur == 2 && las == 3) return -1; // closing paren preceded directly by =*/-^

        return 0;
    }

    public static void main(String[] args)throws IOException
    {
        InFixEvaluator i = new InFixEvaluator();
        try{
            System.out.println(i.evaluator(args[0]));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
