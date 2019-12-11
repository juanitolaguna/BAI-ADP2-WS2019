package adp2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VectorGenerator {

    public static List<VectorADT> generate(int amount, int dimension) {
        return generate(amount, dimension, 0, 100);
    }

    public static List<VectorADT> generate(int amount, int dimension, int min, int max) {
        List<VectorADT> vectors = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int[] values = new int[dimension];
            for (int j = 0; j < dimension; j++) {
                values[j] = random.nextInt((max - min) + 1) + min;
            }
            vectors.add(new VectorADT(values));
        }
        return vectors;
    }
}