import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Utkarsh on 8/30/18.
 */
/**
 * Program Name
 *
 * brief description of the program
 *
 * @author your name, email
 * @version date of completition
 *
 */

public class InFixEvaluator
{
    public double evaluator(String str) throws Exception
    {
        //Write your code here
        // The input comes as a string 
        // The final output should be returned as a double.
        // The precision does not matter, as the answers are rounded to the fourth decimal value.  


        return 0.0;                 //remove this line and return the appropriate answer
    }

    public static void main(String[] args)throws IOException
    {
        // The buffered reader has been provided.
        // The examples can be found in input.txt file, provided in the src folder.
        // Feel free to add your own examples.
        // Make sure the tests work before submitting your final code.

        InFixEvaluator i = new InFixEvaluator();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("src/input.txt"));
            String line;
            while ((line = br.readLine()) != null)
            {
                System.out.println(i.evaluator(line));
        }
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
