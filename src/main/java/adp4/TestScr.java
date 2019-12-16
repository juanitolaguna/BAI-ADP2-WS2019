package adp4;

import java.util.ArrayList;
import java.util.List;

public class TestScr {
    private Character letter;

    public static int hashM(Character letter) {
        return (11 * letter.hashCode() % 12);
    }

    public static void main(String[] args) {
        Character[] charList = new Character[]{'V', 'E', 'R', 'Y', 'I', 'M', 'P', 'O', 'T', 'A', 'N', 'S'};
        for (int i = 0; i < charList.length - 1; i++) {
            System.out.println(hashM(charList[i]));
            //System.out.println(hashM(charList[i]));
        }
    }
}
