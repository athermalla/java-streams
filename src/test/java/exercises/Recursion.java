package exercises;

import org.junit.Test;

public class Recursion {



//Example 1 : Sum of Natural Numbers
    @Test
    public void  method1(){

        int total = sum_rec(5);
        System.out.println(total);

    }

    private int sum_rec(int n) {

        if(n == 0 ) return 0;
        else {
            return n+ sum_rec(n-1);
        }
    }

    //Example 2 : isPalindrome()
    @Test
    public void  method2(){

        String check = "abcdba";
        boolean total = isPalindrome(check);
        System.out.println(total);

    }

    private boolean isPalindrome(String str) {

        System.out.println(str);
        if(str.length() <=1 ) return true;
        if(str.charAt(0) != str.charAt(str.length()-1)) {
            return false;
        }
        return isPalindrome(str.substring(1,str.length()-1));

    }
}
