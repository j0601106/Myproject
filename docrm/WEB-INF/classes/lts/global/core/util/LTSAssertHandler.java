package lts.global.core.util;

import gov.fdc.library.exception.LTSDefineException;
import java.io.PrintStream;


















































public class LTSAssertHandler
{
  public static void assertWithType(String text, String require, String validateType, String length)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  







  public static void idno(String text) {}
  






  public static void unid(String text)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  



  public static void http(String text)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  



  public static void email(String text)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  



  public static void cdate(String text)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  



  public static void wdate(String text)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  



  public static void require(String text)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  




  public static void length(String text, int len)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  




  public static void greateThen(long a, long b)
  {
    if (a > b) {
      System.out.println("[a值:" + a + ",b值:" + b + "]");
      return;
    }
    throw new LTSDefineException(" greateThen 待轉換");
  }
  





  public static void lessThen(long a, long b)
  {
    if (a < b) {
      System.out.println("[a值:" + a + ",b值:" + b + "]");
      return;
    }
    throw new LTSDefineException(" lessThen 待轉換");
  }
  





  public static void equal(long a, long b)
  {
    if (a == b) {
      System.out.println("[a值:" + a + ",b值:" + b + "]");
      return;
    }
    throw new LTSDefineException(" equal 待轉換");
  }
  









  public static void range(long inp, long min, long max, boolean minEqual, boolean maxEqual)
  {
    if ((minEqual) && (maxEqual)) {
      if ((inp >= min) && (inp <= max)) {
        System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      }
      
    }
    else if (maxEqual) {
      if ((inp > min) && (inp <= max)) {
        System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      }
    }
    else if (minEqual) {
      if ((inp >= min) && (inp < max)) {
        System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      }
      

    }
    else if ((inp > min) && (inp < max)) {
      System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      return;
    }
    

    throw new LTSDefineException("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 待轉換");
  }
  




  public static void greateThen(double a, double b)
  {
    if (a > b) {
      System.out.println("[a值:" + a + ",b值:" + b + "]");
      return;
    }
    
    throw new LTSDefineException(" greateThen 待轉換");
  }
  

  public static void lessThen(double a, double b)
  {
    if (a < b) {
      System.out.println("[a值:" + a + ",b值:" + b + "]");
      return;
    }
    
    throw new LTSDefineException(" lessThen 待轉換");
  }
  




  public static void equal(double a, double b)
  {
    if (a == b) {
      System.out.println("[a值:" + a + ",b值:" + b + "]");
      return;
    }
    
    throw new LTSDefineException(" equal 待轉換");
  }
  








  public static void range(double inp, double min, double max, boolean minEqual, boolean maxEqual)
  {
    if ((minEqual) && (maxEqual)) {
      if ((inp >= min) && (inp <= max)) {
        System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      }
      
    }
    else if (maxEqual) {
      if ((inp > min) && (inp <= max)) {
        System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      }
    }
    else if (minEqual) {
      if ((inp >= min) && (inp < max)) {
        System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      }
      

    }
    else if ((inp > min) && (inp < max)) {
      System.out.println("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 內");
      return;
    }
    

    throw new LTSDefineException("[最小值:" + min + "最大值:" + max + "輸入值:" + inp + "] range 待轉換");
  }
  








  public static void dateRange(String inp, String min, String max, boolean minEqual, boolean maxEqual)
  {
    System.out.println(" ### TODO not implement !! ### ");
  }
  
  public static void assertString(String text, boolean require, String validateType, String length) {}
  
  public static void assertString(String text, boolean require, String validateType) {}
  
  public static void assertString(String text, boolean require) {}
  
  public static void ltsAssert(double inp, boolean require, String validateType, String length) {}
  
  public static void ltsAssert(double inp, boolean require, String validateType) {}
  
  public static void ltsAssert(double inp, boolean require) {}
  
  public static void ltsAssert(long inp, boolean require, String validateType, String length) {}
  
  public static void ltsAssert(long inp, boolean require, String validateType) {}
  
  public static void ltsAssert(long inp, boolean require) {}
  
  public static void main(String[] args) {}
}
