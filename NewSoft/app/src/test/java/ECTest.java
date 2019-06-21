import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ECTest {
    /*******************
     Question 1
     *******************/
    public static void main(String[] arg) {
        //run test case below

    }

    @Test
    public void getLongestStringTestCase() {
        String[] strings = new String[]{"4999", "longestString", "23", "notLongest"};
        testGetLongestString(strings, new String[]{"longestString"});//test only 1 longest

        strings = new String[]{"4999", "longestString", "23", "notLongest", "longestStrin1"};
        testGetLongestString(strings, new String[]{"longestString", "longestStrin1"});//test more than 1 longest

        strings = new String[]{"4999", "4999"};
        testGetLongestString(strings, new String[]{"4999","4999"});//test same value

        strings = new String[]{};

        //can comment
        testGetLongestString(strings, new String[]{});//test empty
    }


    private ArrayList<String> getLongestString(String[] arrays) {
        //might contain more than 1 longeststring, so using array
        ArrayList<String> longestStringList = new ArrayList<>();

        if (arrays.length > 0) {
            //assume first one is longest string
            longestStringList.add(arrays[0]);

            //loop through and find longest string
            String s;
            for (int i = 1; i < arrays.length; i++) {//start from 1 since we ady assume 0 is longest string
                s = arrays[i];

                ArrayList<String> cloned = (ArrayList<String>) longestStringList.clone();

                for (String longestString : cloned) {
                    if (s.length() > longestString.length()) {//if new S longer, then reset list and add it
                        longestStringList.clear();
                        longestStringList.add(s);
                    } else if (s.length() == longestString.length()) {//add to list if same length
                        longestStringList.add(s);
                    }
                }

            }

            return longestStringList;
        }
        return null;
    }


    private void testGetLongestString(String[] strings, String[] expectedOutput) {
        ArrayList<String> longestStringList = getLongestString(strings);
        if (longestStringList != null) {
            //print value
            System.out.println(longestStringList);

            assertEquals(longestStringList.toArray(), expectedOutput);//test
        } else {
            fail("getLongestStringTestCase: dun have longest string");
        }
    }


}
