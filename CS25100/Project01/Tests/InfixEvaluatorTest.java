import org.junit.Test;

//import static jdk.nashorn.internal.objects.Global.Infinity;
import static org.junit.Assert.*;

/**
 * Created by utkyfolder on 8/29/18.
 */
public class InfixEvaluatorTest {

  @Test
  public void testOne() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(2.0, i.evaluator("( 2 )"), 0.0);
  }

  @Test
  public void testTwo() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(30.0, i.evaluator("( { 8 *  6 } - 18 )"), 0.0);
  }

  @Test
  public void testThree() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(7.0, i.evaluator("( { { [ { ( [ 5 * 5 ] ) } ] } - 18 } )"), 0.0);
  }

  @Test
  public void testFour() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(41.7410, i.evaluator("{ 42 + [ 87 / ( { 3 - 9 } * 56 ) ] }"), 0.009);
  }

  @Test
  public void testFive() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(-0.2574, i.evaluator("( [ 42 + 87 ] / ( 3 - { 9 * 56 } ) )"), 0.009);
  }

  @Test
  public void testSix() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(-0.1060, i.evaluator("( [ sin ( 45 + ( [ 30 - 15 ] * 3 ) ) ] + [ ( [ 18 - ( 2 * 9 ) ] ) - { cos { 133.07 - ( 7 * 19.01 ) } } ] )"), 0.009);
  }

/*  @Test
  public void testSeven() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(Infinity, i.evaluator("( { 2 + ( 2 + ( 4 - ( 1 + 4 ) ) ) } / [ ( 2 ^ [ ( 2 ^ 2 ) ] ) - ( 4 ^ { 4 - 2 } ) ] )"),0.0);
  }*/

  @Test
  public void testEight() throws Exception
  {
    InFixEvaluator i = new InFixEvaluator();
    assertEquals(14.1407, i.evaluator("[ ( { ( ) log ( 657 + [ 7 * 3 ] ) ( ) } + [ sin ( { 7 ^ 3 } + 10 ) ] ) { [ ] } / [ cos ( 9 * ( 24.01 - 19.01 ) ) ] ]"),0.009);
  }
}