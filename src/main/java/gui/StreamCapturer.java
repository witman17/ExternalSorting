package gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Witold on 2016-01-22.
 */
public class StreamCapturer extends OutputStream {
    private PrintStream oldStream;
    private StreamConsumer consumer;
    private StringBuilder builder;

    public StreamCapturer(PrintStream oldStream, StreamConsumer consumer) {
        this.oldStream = oldStream;
        this.consumer = consumer;
        this.builder = new StringBuilder(64);
    }

    @Override
    public void write(int b) throws IOException {
        char c = (char) b;
        String value = Character.toString(c);
        builder.append(value);
        if (value.equals("\n")) {
            consumer.consume(builder.toString());
            builder.delete(0, builder.length());
        }
        oldStream.write(b);
    }
}
