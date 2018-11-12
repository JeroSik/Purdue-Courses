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
 * @version Sep. 11, 2018
 *
 */

public class InFixEvaluator {
    // Evaluates a string of of operators and operands and evaluates the infix arithmetic
    public double evaluator(String str) throws Exception {
        // Catches errors if an empty stack is popped. If an empty stack is popped, the input is invalid.
        try {
            // Makes sure there are no empty parenthesis
            if (str.contains("( )") || str.contains("[ ]") || str.contains("{ }")) {
                throw new Exception("Invalid Expression");
            }
            // Parses the input string into an array
            String[] input = str.split("\\s+");
            // Makes sure the input starts with matching parenthesis
            if (!input[0].equals("(") && !input[0].equals("[") && !input[0].equals("{")) {
                throw new Exception("Invalid Expression");
            }
            // Makes sure the input ends with matching parenthesis
            if (!input[input.length - 1].equals(")") && !input[input.length - 1].equals("]") && !input[input.length - 1].equals("}")) {
                throw new Exception("Invalid Expression");
            }

            Stack<String> op = new Stack(input.length);      // Creates a stack for operands
            Stack<Double> nums = new Stack(input.length);    // Creates a stack for numbers
            String temp;                                     // Creates a temporary variable to hold pop
            double eval1, eval2;                             // Creates variables for numbers
            boolean existsOp = true;                         // Makes sure a parenthesis doesn't start without proper syntax

            // Starts the evaluator
            for (int i = 0; i < input.length; i++) {
                // Push left parenthesis
                if (input[i].equals("(") || input[i].equals("[") || input[i].equals("{")) {
                    if (existsOp) {
                        op.push(input[i]);
                    } else {
                        throw new Exception("Invalid Expression");
                    }
                // Push operators
                } else if (input[i].equals("+") || input[i].equals("-") || input[i].equals("*") || input[i].equals("/") || input[i].equals("^")) {
                    existsOp = true;
                    op.push(input[i]);
                } else if (input[i].equals("sin") || input[i].equals("cos") || input[i].equals("log")) {
                    op.push(input[i]);
                // Push numbers
                } else if (isNumeric(input[i])) {
                    nums.push(Double.parseDouble(input[i]));
                    existsOp = false;
                // Check right parenthesis
                } else if (input[i].equals(")") || input[i].equals("]") || input[i].equals("}")) {
                    existsOp = true;
                    temp = op.pop();
                    // Pops an operand and evaluates properly
                    if (temp.equals("+")) {
                        eval2 = nums.pop();
                        eval1 = nums.pop();
                        nums.push(eval1 + eval2);
                    } else if (temp.equals("-")) {
                        eval2 = nums.pop();
                        eval1 = nums.pop();
                        nums.push(eval1 - eval2);
                    } else if (temp.equals("*")) {
                        eval2 = nums.pop();
                        eval1 = nums.pop();
                        nums.push(eval1 * eval2);
                    } else if (temp.equals("/")) {
                        eval2 = nums.pop();
                        eval1 = nums.pop();
                        nums.push(eval1 / eval2);
                    } else if (temp.equals("^")) {
                        eval2 = nums.pop();
                        eval1 = nums.pop();
                        if (eval1 == 0 && eval2 == 0)
                            return Double.NaN;
                        nums.push(Math.pow(eval1, eval2));
                    } else if (temp.equals("sin")) {
                        nums.push(Math.sin(nums.pop()));
                    } else if (temp.equals("cos")) {
                        nums.push(Math.cos(nums.pop()));
                    } else if (temp.equals("log")) {
                        nums.push(Math.log(nums.pop()));
                    } else if (temp.equals("(")) {
                        if (!input[i].equals(")")) {
                            throw new Exception("Invalid Expression");
                        } else {
                            continue;
                        }
                    } else if (temp.equals("[")) {
                        if (!input[i].equals("]")) {
                            throw new Exception("Invalid Expression");
                        } else {
                            continue;
                        }
                    } else if (temp.equals("{")) {
                        if (!input[i].equals("}")) {
                            throw new Exception("Invalid Expression");
                        } else {
                            continue;
                        }
                    }

                    // Checks for matching parenthesis
                    temp = op.pop();
                    if (temp.equals("(")) {
                        if (!input[i].equals(")")) {
                            throw new Exception("Invalid Expression");
                        }
                    } else if (temp.equals("[")) {
                        if (!input[i].equals("]")) {
                            throw new Exception("Invalid Expression");
                        }
                    } else if (temp.equals("{")) {
                        if (!input[i].equals("}")) {
                            throw new Exception("Invalid Expression");
                        }
                    } else {
                        throw new Exception("Invalid Expression");
                    }
                    // Return error if none of the above
                } else {
                    throw new Exception("Invalid Expression");
                }
            }
            // Makes sure stack is empty at the end
            if (op.IsEmpty()) {
                return nums.pop();
            } else {
                throw new Exception("Invalid Expression");
            }
        } catch (Exception e) {
            throw new Exception("Invalid Expression");
        }
    }

    // Check if a string is a number
    public static boolean isNumeric(String str) {
        try {
            double test = Double.parseDouble(str);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args)throws IOException {
        InFixEvaluator i = new InFixEvaluator();
        try{
            System.out.println(i.evaluator(args[0]));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
