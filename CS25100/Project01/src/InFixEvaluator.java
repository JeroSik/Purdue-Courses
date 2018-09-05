import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Utkarsh on 8/30/18.
 * Revised by Geoerge Adams on 09/02/18.
 */
/**
 * Project 01
 *
 * An infix arithmetic expression evaluator
 *
 * @author Youngsik Yoon, yoon161@purdue.edu
 * @version date of completition
 *
 */


public class InFixEvaluator {
    public double evaluator(String str) throws Exception {
        //Write your code here
        // The input comes as a string; the operands are of type double.
        // The final output should be returned as a double.

        String[] input = str.split("\\s+");
        Stack<String> op = new Stack(input.length);
        Stack<Double> nums = new Stack(input.length);
        double output = 0;
        double eval1,eval2;

        for (int i = 0; i < input.length; i++) {
            // Brackets
            if (input[i].equals("(") || input[i].equals("[") || input[i].equals("{")) {
                op.push(input[i]);
            } else if (input[i].equals("+") || input[i].equals("-") || input[i].equals("*") || input[i].equals("/") || input[i].equals("^")) {
                op.push(input[i]);
            } else if (input[i].equals("sin") || input[i].equals("cos") || input[i].equals("log")) {
                op.push(input[i]);
            } else if (isNumeric(input[i])) {
                nums.push(Double.parseDouble(input[i]));
            } else if (input[i].equals(")") || input[i].equals("]") || input[i].equals("}")) { // )
                if (op.pop().equals("+")) {
                    eval2 = nums.pop();
                    eval1 = nums.pop();
                    nums.push(eval1 + eval2);
                } else if (op.pop().equals("-")) {
                    eval2 = nums.pop();
                    eval1 = nums.pop();
                    nums.push(eval1 - eval2);
                } else if (op.pop().equals("*")) {
                    eval2 = nums.pop();
                    eval1 = nums.pop();
                    nums.push(eval1 * eval2);
                } else if (op.pop().equals("/")) {
                    eval2 = nums.pop();
                    eval1 = nums.pop();
                    nums.push(eval1 / eval2);
                } else if (op.pop().equals("^")) {
                    eval2 = nums.pop();
                    eval1 = nums.pop();
                    nums.push(Math.pow(eval1, eval2));
                } else if (op.pop().equals("sin")) {
                    nums.push(Math.sin(nums.pop()));
                } else if (op.pop().equals("cos")) {
                    nums.push(Math.cos(nums.pop()));
                } else if (op.pop().equals("log")) {
                    nums.push(Math.log(nums.pop()));
                }
                // FINISH CHECKING CLOSE AND OPEN BRACKETS
            } else if (input[i].equals("]")) { // ]
                if (op.pop().equals("[")) {
                } else {
                    throw new Exception("Invalid Input");
                }
            } else if (input[i].equals("}")) { // }
                if (op.pop().equals("{")) {
                } else {
                    throw new Exception("Invalid Input");
                }
            } else {
                throw new Exception("Invalid Input");
            }
        }
        return output;  //remove this line and return the appropriate answer
    }

    public static boolean isNumeric(String str) {
        try {
            double test = Double.parseDouble(str);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args)throws IOException {
        // The buffered reader has been provided.
        // Test examples can be found in input.txt file.
        // Make sure the tests work before submitting your final code.

        InFixEvaluator i = new InFixEvaluator();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/input.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(i.evaluator(line));
            }
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
