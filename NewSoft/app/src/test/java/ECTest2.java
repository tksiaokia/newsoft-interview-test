import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ECTest2 {
    /*******************
     Question 2
     *******************/
    public static void main(String[] arg) {
        //run test case below
    }

    @Test
    public void recursiveSumAllTestCase() {
        recursiveSumAll(5,15);
        recursiveSumAll(9,45);

        recursiveSumAll(10,55);//extra test case

        recursiveSumAll(9,2);//test error


    }
    private void recursiveSumAll(int num,int expectedOutput){
        int output = recursiveSumAll(num);
        System.out.println("Num: "+ num +" Output: "+ output +" ExpectedOutput:"+ expectedOutput);
        assertEquals(recursiveSumAll(num), expectedOutput);
    }

    private int recursiveSumAll(int num) {
        if(num == 0)//if 0, just return 0
            return  0;

        return (num*(num+1))/2;//use formula instead of forloop
    }


}
