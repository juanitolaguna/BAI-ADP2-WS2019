package adp4;

import algs4.ST;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Dokumentenaehnlichkeit {

    private static ST<String, int[]> trigramme = new ST<>(); // abb, [3,4,5]


    private static List<String> readFiles(String[] listOfFiles) {
        // Get List of  Documents
        List<String> lines = new ArrayList<>();
        List<String> words = new ArrayList<>();
        int numOfDocs = listOfFiles.length;

        for (int i = 0; i < numOfDocs; i++) { // im dokument
            File file = new File(listOfFiles[i]);

            if (file.exists()) {
                try {
                    lines = Files.readAllLines(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String l : lines) { // each line in doc
                    String[] wordsAry = l.split(" ");
                    for (int j = 0; j < wordsAry.length; j++) { // each word in doc
                        for (int k = 0; k < numOfDocs; k++) {

                            //getTrigramms(wordsAry[j], k, numOfDocs);
                        }

                    }
                }
            }
        }
        return words;
    }

    private List<String> getTrigramms(String s) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < s.length() / 3; i++) {
            String trigramm = s.substring(i * 3, i * 3 + 3);
            list.add(trigramm);
        }
        return list;

    }


    public static void main(String[] args) {
        String[] docs = {"doc1.txt", "doc2.txt", "doc3.txt"};

        List<String> words = readFiles(docs);

        System.out.println(words);
        System.out.println();

//        trigramme.forEach(e-> {
//            System.out.println(e);
//        });


    }


}
