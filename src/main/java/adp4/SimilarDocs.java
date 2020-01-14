package adp4;

import algs4.TST;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimilarDocs {

    private int numofDocs;
    private TST<int[]> trigrammTree;

    public SimilarDocs(String path) {
        trigrammTree = new TST<>();
        numofDocs = Objects.requireNonNull(new File(path).listFiles()).length;
        readFiles(path);
    }

    public static void main(String[] args) {
        SimilarDocs sd = new SimilarDocs("./docs/");

//        int[] trigramm = sd.getTrigrammTree().get("Lor");
//        StringBuilder output = new StringBuilder();
//        for (int i = 0; i < trigramm.length; i++) {
//            output.append(" " + trigramm[i]);
//        }
//        System.out.println(output);

        sd.euclideanDistanceBetweenAll();
    }

    public TST<int[]> getTrigrammTree() {
        return trigrammTree;
    }

    private List<String> getTrigramms(String s) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (i < s.length() - 2) {
                String trigramm = s.substring(i, i + 3);
                list.add(trigramm);
            }
        }
//        System.out.println(list);
        return list;
    }

    private void readFiles(String filePaths) {
        int documentIndex = 0;
        for (File file : new File(filePaths).listFiles()) {
            System.out.println(file.getName() + " -> " + documentIndex);
            List<String> words = new ArrayList<>();
            try {
                for (String line : Files.readAllLines(file.toPath())) {
                    words.addAll(Arrays.asList(line.split("\\s")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            for (String word : words) {
                int currentDocument = documentIndex;
                List<String> trigramms = getTrigramms(word);

                trigramms.forEach(trigramm -> {
                    if (!trigrammTree.contains(trigramm)) {
                        trigrammTree.put(trigramm, new int[numofDocs]);
                    }
                    trigrammTree.get(trigramm)[currentDocument]++;
                });
            }
            documentIndex++;
        }
    }

    public void euclideanDistanceBetweenAll() {
        for (int i = 0; i < numofDocs; i++) {
            for (int j = i + 1; j < numofDocs; j++) {
                System.out.println("Distance between " + i + " <-> " + j + ": " + euclideanDistance(i, j));
            }
        }
    }

    public double euclideanDistance(int documentNumberA, int documentNumberB) {
        int distance = 0;
        for (String trigramm : trigrammTree.keys()) {
            distance += Math.pow(trigrammTree.get(trigramm)[documentNumberA] - trigrammTree.get(trigramm)[documentNumberB], 2);
        }
        return Math.sqrt(distance);
    }
}
