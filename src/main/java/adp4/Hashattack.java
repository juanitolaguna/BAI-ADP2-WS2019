package adp4;

import algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Hashattack {

    public static int hashCode(String string) {
        int hash = 0;
        for (int i = 0; i < string.length(); i++) {
            hash = (hash * 31) + string.charAt(i);
        }
        return hash;
    }

    private List<String> generateStringsInput(int n) {
        String[] values = {"Aa", "BB"};

        List<String> strings = new ArrayList<>();
        generateStrings(n, 0, strings, "", values);

        return strings;
    }

    private void generateStrings(int n, int currentIndex, List<String> strings, String currentString, String[] values) {
        if (currentIndex == n) {
            strings.add(currentString);
            return;
        }

        for (String value : values) {
            String newValue = currentString + value;
            int newIndex = currentIndex + 1;
            //System.out.println("Hier: " + newValue);

            generateStrings(n, newIndex, strings, newValue, values);
        }
    }

    public static void main(String[] args) {
        Hashattack hashAttack = new Hashattack();
        List<String> hashAttackInput = hashAttack.generateStringsInput(3);

        for (String string : hashAttackInput) {
            StdOut.println(string);
        }

        hashAttackInput.add("Fehler");
        hashAttackInput.add("Noch ein Fehler");


        String first = hashAttackInput.get(0);
        String result = hashAttackInput.stream().filter((e) -> hashCode(e) != hashCode(first)).findFirst().orElse("No items");
        System.out.println(result);
    }

}