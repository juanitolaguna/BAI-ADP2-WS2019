package adp4;

import algs4.TST;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class DocumentSimilarity {

    private int numberOfFiles;
    private TST<int[]> trigrammTree ;

    public DocumentSimilarity(String path){
        trigrammTree = new TST<>();
        numberOfFiles = Objects.requireNonNull(new File(path).listFiles()).length;
        fillTST(path);
    }


//    public List<String> getTrigramms(String word){
//        List<String> trigramms = new ArrayList<>();
//        if(word.length() < 3){
//            return trigramms;
//        }
//        trigramms.add(word.substring(0,3));
//        trigramms.addAll(getTrigramms(word.substring(1)));
//        System.out.println(trigramms);
//        return trigramms;
//    }

    private List<String> getTrigramms(String s) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < s.length() / 3; i++) {
            String trigramm = s.substring(i * 3, i * 3 + 3);
            list.add(trigramm);
        }
        System.out.println(list);
        return list;

    }

    private void fillTST(String pathToFiles){
        int documentIndex = 0;
        for(File file : new File(pathToFiles).listFiles()){
            System.err.println(file.getName()+" -> "+documentIndex);
            List<String> words = new ArrayList<>();
            try {
                for(String line : Files.readAllLines(file.toPath())){
                    words.addAll(Arrays.asList(line.split("\\s")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(String word: words){
                List<String> trigramms = getTrigramms(word);


                int finalDocumentIndex = documentIndex;
                trigramms.forEach(trigramm -> {
                    if(!trigrammTree.contains(trigramm)){
                        trigrammTree.put(trigramm,new int[numberOfFiles]);
                    }
                    trigrammTree.get(trigramm)[finalDocumentIndex]++;
                });
            }
            documentIndex++;
        }
    }
    public void distanceForAllDocuments(){
        for(int i = 0; i < numberOfFiles; i++){
            for (int j = i+1; j < numberOfFiles; j++){
                System.out.println("Distance between "+i+" <-> "+j+": "+distanceBetweenDocuments(i,j));
            }
        }
    }

    public double distanceBetweenDocuments(int documentNumberA, int documentNumberB){
        int distance = 0;
        for(String trigramm : trigrammTree.keys()){
            distance += Math.pow(trigrammTree.get(trigramm)[documentNumberA] - trigrammTree.get(trigramm)[documentNumberB],2);
        }
        return Math.sqrt(distance);
    }

    public static void main(String[] args) {
        DocumentSimilarity ds = new DocumentSimilarity("./testdocu/");
        ds.distanceForAllDocuments();
    }
}
