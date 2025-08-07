package JavaProcessingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {
    private final BufferedWriter writer;

    public ResultWriter(String fileName) throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName, true));
    }

    public synchronized void write(String result) {
        try {
            writer.write(result + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error writing result: " + e.getMessage());
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
