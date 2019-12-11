package adp2;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TopNVectors {
    public static VectorADT[] topM(VectorADT star, int m, String path) throws IOException {
        VectorADT[] topM = new VectorADT[m];
        Scanner br = VectorADTParser.getScannerForFile(path);

        PriorityQueue<VectorADT> priorityQueue = new PriorityQueue<>(new VectorDistanceComparator(star));
        String line;
        while (br.hasNext()) {
            line = br.nextLine();
            priorityQueue.add(VectorADT.fromString(line));
            if (priorityQueue.size() > m) priorityQueue.poll();
        }

        for (int i = topM.length - 1; i >= 0; i--) {
            topM[i] = priorityQueue.poll();
        }

        return topM;
    }
}