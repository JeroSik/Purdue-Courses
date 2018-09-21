import org.junit.*;
import static org.junit.Assert.*;

public class InFixEvaluatorTest {

  @Test
  public void testVoc1(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( 2 + 3 )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,5, 0.001);
  }

  @Test
  public void testVoc2(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( ( ) 2 + 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc3(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( ( 2 ) + 3 ) ");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,5, 0.001);
  }

  @Test
  public void testVoc4(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( ( 2 + ) 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc5(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( ( 2 + 3 ) )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,5, 0.001);
  }

  @Test
  public void testVoc6(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 ( ) + 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc7(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 ( + ) 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc8(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 (  + 3 ) )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc9(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 + ( ) 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc10(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( 2 + ( 3 ) )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,5, 0.001);
  }


  @Test
  public void testVoc11(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 + 3 ( ) )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc12(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc13(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 + 3 ]");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc14(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("[ 2 + 3 }");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void testVoc15(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( 2 + [ 3 + 5 ] )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,10.0, 0.001);
  }

  @Test
  public void testVoc16(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("{ 2 / ( 3 - 3 ) }");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer, Double.POSITIVE_INFINITY, 0.001);
  }

  @Test
  public void testVoc17(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("{ 42 + [ 87 / ( { 3 - 9 } * 56 ) ] }");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,41.74107142857143, 0.001);
  }

  @Test
  public void testVoc18(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( { sin [ 90 ] } )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,0.8939966636005579, 0.001);
  }

  @Test
  public void testVoc19(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( sin ( log ( 3.1 ^ 3 ) ) )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,-0.24993553919734932, 0.001);
  }

  @Test
  public void testVoc20(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 3 + 5.8k7o )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }


  @Test
  public void test1(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("7.8k7o");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test2(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("3 + 5");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test3(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( { 3 } + 5 )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,8, 0.001);
  }

  @Test
  public void test4(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( { 3 } + 5 }");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test5(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( log 3 )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,1.0986122886681098, 0.001);
  }

  @Test
  public void test6(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( log ( 3 ) ) ");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,1.0986122886681098, 0.001);
  }

  @Test
  public void test7(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("{ ( cos ( ( 90 ) + [ [ [ 5 ] ] ] ) ) } ");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,0.7301735609948197, 0.001);
  }

  @Test
  public void test8(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("{}");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }


  @Test
  public void test9(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("{ }");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test10(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("{ + }");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test11(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 ( 1 + 3 ) )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test12(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( 2 + 1 + 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test13(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( - 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test14(){
    InFixEvaluator i = new InFixEvaluator();
    try {
      i.evaluator("( + 3 )");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Invalid Expression");
      return;
    }
    fail();
  }

  @Test
  public void test15(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( -3 )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,-3, 0.001);
  }

  @Test
  public void test16(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( 0 / 0 )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,Double.NaN, 0.001);
  }

  @Test
  public void test17(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( 0 ^ 0 )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,Double.NaN, 0.001);
  }

  @Test
  public void test18(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( ( 5 / 0 ) - ( 9 / 0 ) )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,Double.NaN, 0.001);
  }



  @Test
  public void test19(){
    InFixEvaluator i = new InFixEvaluator();
    double answer = 0;
    try {
      answer = i.evaluator("( 0 * ( 9 / 0 ) )");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      fail();
    }
    assertEquals(answer,Double.NaN, 0.001);
  }








}