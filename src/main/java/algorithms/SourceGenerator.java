package algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Witold on 2015-10-26.
 */
public class SourceGenerator {

    protected String fileName;
    protected BufferedWriter writer;

    public SourceGenerator(String fileName) {
        this.fileName = fileName;
        this.writer = null;
    }

    public void generateUniform(int kilobytesNumber) throws IOException {
        init();
        Random random = new Random();
        for (int i = kilobytesNumber * 1000; i > 0; i--) {
            writer.write(random.nextInt(9));
            writer.newLine();
        }
        close();
    }

    // TODO przemyœleæ dzia³anie tego generatora Czy ma sens
    public void generateFullyUnsorted(int kilobytesNumber) throws IOException {
        init();
        int currentMax = 9;
        int buffer;
        Random random = new Random();
        for (int i = kilobytesNumber; i > 0; i--) {
            buffer = random.nextInt(currentMax);
            writer.write(buffer);
            if (buffer < currentMax) currentMax = buffer;
        }
        close();
    }

    private void init() throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName));
    }

    private void close() throws IOException {
        writer.close();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
