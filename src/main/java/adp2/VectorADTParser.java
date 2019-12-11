package adp2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VectorADTParser {
    public static Scanner getScannerForFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            String message = String.format("Invalid path '%s'.", path);
            throw new IOException(message);
        }

        return new Scanner(file);
    }

    public static List<VectorADT> fromFile(String path) throws IOException {
        Scanner sc = getScannerForFile(path);
        List<VectorADT> vectors = new ArrayList<>();
        String line;
        while (sc.hasNext()) {
            line = sc.nextLine();
            vectors.add(VectorADT.fromString(line));
        }

        return vectors;
    }

    public static void toFile(List<VectorADT> vectors, String path, boolean overwrite) throws IOException {
        File file = new File(path);
        if (overwrite && file.exists()) {
            if (!file.delete()) {
                String message = String.format("Could not delete existing file at path: %s", path);
                throw new IOException(message);
            }
        } else {
            if (!file.createNewFile()) {
                String message = String.format("File already exists at path: %s", path);
                throw new IOException(message);
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (VectorADT vector : vectors) {
                writer.write(vector + String.format("%n"));
            }
        }
    }
}
