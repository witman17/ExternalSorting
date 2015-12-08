package algorithms.polyphasesort;

import algorithms.naturaljoin.NaturalCombiner;
import algorithms.naturaljoin.SeriesReader;

import java.io.*;

/**
 * Created by Witold on 2015-11-02.
 */
public class PolyphaseCombiner extends NaturalCombiner {
    protected static final int INPUTA = 1;
    protected static final int INPUTB = 2;
    protected int inputASeriesNumber;
    protected int inputBSeriesNumber;
    protected int outputSeriesNumber;

    public PolyphaseCombiner(String[] files, int inputASeriesNumber, int inputBSeriesNumber) {
        super(files[0], files[1], files[2]);
        this.inputASeriesNumber = inputASeriesNumber;
        this.inputBSeriesNumber = inputBSeriesNumber;
        this.outputSeriesNumber = 0;
    }

    public PolyphaseCombiner(String inputA, String inputB, String output, int inputASeriesNumber, int inputBSeriesNumber) {
        super(inputA, inputB, output);
        this.inputASeriesNumber = inputASeriesNumber;
        this.inputBSeriesNumber = inputBSeriesNumber;
        this.outputSeriesNumber = 0;

    }

    public PolyphaseCombiner(String inputA, String inputB, String output, int inputBufferSize, int outputBufferSize, int inputASeriesNumber, int inputBSeriesNumber) {
        super(inputA, inputB, output, inputBufferSize, outputBufferSize);
        this.inputASeriesNumber = inputASeriesNumber;
        this.inputBSeriesNumber = inputBSeriesNumber;
    }

    @Override
    public void combine() throws IOException {
        log.info("START - POLYPHASE SORT");
        init();
        //pobranie pierwszych dwoch elementow
        String bufferA = seriesReaderA.getSeriesElement();
        String bufferB = seriesReaderB.getSeriesElement();
        while (inputASeriesNumber + inputBSeriesNumber + outputSeriesNumber > 1) {
            //zamiana plik�w IO je�li wymagana
            if (inputASeriesNumber <= 0) {
                reInit(INPUTA);
                bufferA = seriesReaderA.getSeriesElement();
            }
            if (inputBSeriesNumber <= 0) {
                reInit(INPUTB);
                bufferB = seriesReaderB.getSeriesElement();
            }
            // por�wnanie dwoch element�w serii, przepisanie mniejszego
            while (bufferA != null && bufferB != null && !seriesReaderA.isSeriesEnded() && !seriesReaderB.isSeriesEnded()) {
                if (bufferA.compareTo(bufferB) < 0) {
                    writer.write(bufferA);
                    bufferA = seriesReaderA.getSeriesElement();
                } else {
                    writer.write(bufferB);
                    bufferB = seriesReaderB.getSeriesElement();
                }
                writer.newLine();
            }
            //dopisanie do pozosta�ych element�w, serri kt�ra si� nie sko�czy�a
            while (!seriesReaderA.isSeriesEnded() && bufferA != null) {
                writer.write(bufferA);
                writer.newLine();
                bufferA = seriesReaderA.getSeriesElement();
            }
            while (!seriesReaderB.isSeriesEnded() && bufferB != null) {
                writer.write(bufferB);
                writer.newLine();
                bufferB = seriesReaderB.getSeriesElement();
            }
            seriesReaderA.resetSeriesEnd();
            seriesReaderB.resetSeriesEnd();
            inputASeriesNumber--;
            inputBSeriesNumber--;
            outputSeriesNumber++;
        }
        close();
        log.info("END - POLYPHASE SORT");
    }

    @Override
    protected void init() throws IOException {
        super.init();
    }

    protected void reInit(int inputLasted) throws IOException {
        String temp;
        if (inputLasted == INPUTA) {
            temp = inputA;
            inputA = output;
            output = temp;
            inputASeriesNumber = outputSeriesNumber;
            outputSeriesNumber = 0;
            readerA.close();
            writer.close();
            readerA = new BufferedReader(new FileReader(inputA));
            seriesReaderA = new SeriesReader(readerA);
            writer = new BufferedWriter(new FileWriter(output));
        }
        if (inputLasted == INPUTB) {
            temp = inputB;
            inputB = output;
            output = temp;
            inputBSeriesNumber = outputSeriesNumber;
            outputSeriesNumber = 0;
            readerB.close();
            writer.close();
            readerB = new BufferedReader(new FileReader(inputB));
            seriesReaderB = new SeriesReader(readerB);
            writer = new BufferedWriter(new FileWriter(output));

        }

    }

    @Override
    protected void close() throws IOException {
        super.close();

    }

    public String getInputA() {
        return this.inputA;
    }

    public String getInputB() {
        return this.inputB;
    }
}
