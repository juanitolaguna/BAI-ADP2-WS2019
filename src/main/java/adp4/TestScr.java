package adp4;

import java.util.ArrayList;
import java.util.List;

public class TestScr {
    private Character letter;

    public static int hashM(Character letter) {
        return (2 * Character.getNumericValue(letter)) % 6;
    }

    public static void main(String[] args) {
        Character[] charList = new Character[]{'V', 'E', 'R', 'Y', 'I', 'M', 'P', 'O', 'R', 'T', 'A', 'N', 'T', 'P', 'E', 'R', 'S', 'O', 'N'};
        for (int i = 0; i < charList.length - 1; i++) {
            System.out.println(charList[i].hashCode());
            System.out.println(hashM(charList[i]));
        }
    }
}
