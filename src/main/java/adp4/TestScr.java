package adp4;

import java.util.ArrayList;
import java.util.List;

public class TestScr {
    private Character letter;

    public static int hashM(Character letter) {
        return (1 * letter.hashCode() % (22));
    }

    public static int hash(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 31) + s.charAt(i);
        }
        return hash;
    }

    public static int hash(int [] intArray) {
        int h = 0;
        int m = intArray.length;
        int r = 3;
        int q = 997;
        for (int i = 0; i < m; i++) {
            h = (r * h + intArray[i]) % q;
        }
        return h;
    }

    public static boolean compareString(String one, String two) {
        return true;
    }

    public static void main(String[] args) {
//        Character[] charList = new Character[]{'V', 'E', 'R', 'Y', 'I', 'M', 'P', 'O', 'T', 'A', 'N', 'S'};
//        for (int i = 0; i < charList.length - 1; i++) {
//            System.out.println(charList[i] + " " + charList[i].hashCode() + " -> " + hashM(charList[i]));
//            //System.out.println(hashM(charList[i]));
//        }
//
        String one = "AaAa";
//        String two = "BBBB";
//        System.out.println(hash(one) == hash(two));
        String three = one.substring(1);

        int[] arry1 = {1, 3, 2, 2, 1};
        int[] arry2 = {1, 1, 3, 2, 2};
        System.out.println(hash(arry1));
        System.out.println(hash(arry2));
        System.out.println((2428 % 997));
        System.out.println(three);
    }
}
