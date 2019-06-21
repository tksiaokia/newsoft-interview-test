import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ECTest3 {
    /*******************
     Question 3
     *******************/
    public static void main(String[] arg) {
        //run test case below
    }

    @Test
    public void recursiveSumAllTestCase() {
        int[] foo = new int[]{2, 5, 9, 13, 16, 19, 20, 21};
        int[] bar = new int[]{1, 5, 7, 8, 12, 22, 29};

        testGetLeastKNumber(foo,bar,4, new int[]{1, 2, 5, 5});
        testGetLeastKNumber(foo,bar,6, new int[]{1, 2, 5, 5,7,8});
        testGetLeastKNumber(foo,bar,7, new int[]{1, 2, 5, 5,7,8,9});

        //can comment to failed test case
        testGetLeastKNumber(foo,bar,100, new int[]{1, 2, 5, 5,7,8,9});//test invalid k
        testGetLeastKNumber(foo,bar,7, new int[]{1, 2, 5, 5,7,8,13});//test error
    }

    private int[] getLeastKNumber(int[] foo, int[] bar, int k) {
        //mix them
        int[] mix = mixArray(foo, bar);

        //sort them
        sortArray(mix);

        //get first k
        int[] leastNumbers = new int[k];
        for(int i = 0; i < k; i++){
            leastNumbers[i] = mix[i];
        }
        return  leastNumbers;

    }

    private int[] mixArray(int[] foo, int[] bar) {//low level of combine array instead use some build in function
        int[] mix = new int[foo.length + bar.length];
        for (int i = 0; i < mix.length; i++) {
            if (i < foo.length) {
                mix[i] = foo[i];
            } else {
                mix[i] = bar[i - foo.length];
            }
        }
        return mix;
    }

    private void sortArray(int[] arrays) {
        int temp;
        for (int i = 1; i < arrays.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arrays[j] < arrays[j - 1]) {
                    temp = arrays[j];
                    //shift to infront
                    arrays[j] = arrays[j - 1];
                    arrays[j - 1] = temp;
                }
            }
        }
    }

    private void testGetLeastKNumber(int[] foo, int[] bar,int k, int[] expectedOutput) {
        if(foo.length + bar.length > k){
            //print value
            int[] output = getLeastKNumber(foo,bar,k);
            System.out.println(Arrays.toString(output));
            assertEquals(Arrays.toString(output), Arrays.toString(expectedOutput));//test

        }else{
            fail("testGetLeastKNumber: invalid k size");
        }
    }
}
