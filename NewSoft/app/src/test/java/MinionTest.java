import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class MinionTest {
    public static void main(String[] arg) {
        int minionIndex = 10000;//change to any index

        long startTime = System.currentTimeMillis();

        String id = findId(minionIndex);
        System.out.println(id);

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in milliseconds: " + timeElapsed);


    }
    @Test
    public void testCase() {
        assertEquals(findId(0), "23571");
        assertEquals(findId(1), "35711");
        assertEquals(findId(2), "57111");
        assertEquals(findId(3), "71113");
        assertEquals(findId(100), "19319");
        assertEquals(findId(139), "26927");
    }

    private static String findId(int n) {
        int length = n + 5;
        int startNumber = 2;
        String primeString = "";
        while (primeString.length() < length) {
            primeString = checkPrimeString(startNumber, primeString);
            startNumber++;
        }
        System.out.println(primeString);
        return primeString.substring(n, n + 5);

    }

    private static String checkPrimeString(int number, String primeString) {
        String updatedString = primeString;
        if (checkPrime(number)) {
            updatedString += number;
        }
        return updatedString;
    }

    private static boolean checkPrime(int numberToCheck) {
        int remainder;
        for (int i = 2; i <= numberToCheck / 2; i++) {
            remainder = numberToCheck % i;
            //if remainder is 0 than numberToCheckber is not prime and break loop. Elese continue loop
            if (remainder == 0) {
                return false;
            }
        }
        return true;
    }

}
