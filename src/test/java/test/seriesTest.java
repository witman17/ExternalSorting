package test;

import algorithms.SeriesReader;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Witold on 2016-02-02.
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class seriesTest {

    SeriesReader seriesReader;
    BufferedReader seriesBufferesReader;
    BufferedReader bufferedReader;
    Blackhole blackhole;

    @Setup
    public void setup() {
        blackhole = new Blackhole();

        try {
            seriesReader = new SeriesReader(new BufferedReader(new FileReader("s.txt")));
            bufferedReader = new BufferedReader(new FileReader("s.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Benchmark
    public void naturalBenchmark() {
        try {
            for (int i = 0; i < 100000; i++) {
                blackhole.consume(seriesReader.getSeriesElement());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public void simpleBenchmark() {
        try {

            for (int i = 0; i < 100000; i++) {
                blackhole.consume(bufferedReader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
