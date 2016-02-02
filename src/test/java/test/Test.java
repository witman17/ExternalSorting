package test;

import algorithms.naturaljoin.NaturalCombiner;
import algorithms.naturaljoin.NaturalSplitter;
import algorithms.simplejoin.SimpleCombiner;
import algorithms.simplejoin.SimpleSplitter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Witold on 2016-02-02.
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Test {


    private NaturalSplitter naturalSplitter;
    private SimpleSplitter simpleSplitter;
    private NaturalCombiner naturalCombiner;
    private SimpleCombiner simpleCombiner;

    public static void main(String[] args) {
        Options opt = new OptionsBuilder().include(Test.class.getSimpleName())
                .warmupIterations(10)
                .measurementIterations(20)
                .output("test.txt")
                .build();
        Options opt2 = new OptionsBuilder().include(seriesTest.class.getSimpleName())
                .warmupIterations(10)
                .measurementIterations(20)
                .output("test.txt")
                .build();
        try {
            new Runner(opt2).run();
            System.out.println("Done!");
        } catch (RunnerException e) {
            e.printStackTrace();
        }

    }

    @Setup
    public void setup() {
//        naturalSplitter = new NaturalSplitter("s.txt","nA.txt", "nB.txt");
//        simpleSplitter = new SimpleSplitter("s.txt","sA.txt", "sB.txt");
        naturalCombiner = new NaturalCombiner("nA.txt", "nB.txt", "nR.txt");
        simpleCombiner = new SimpleCombiner("sA.txt", "sB.txt", "sR.txt");

    }

    @Benchmark
    public void naturalBenchmark() {
        try {
//            naturalSplitter.split();
            naturalCombiner.combine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public void simpleBenchmark() {
        try {
//            simpleSplitter.split(1);
            simpleCombiner.combine(1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
