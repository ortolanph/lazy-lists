package pt.pauloortolan.lists;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class LazyListBenchmark {

    static void main() throws IOException, RunnerException {

        Options opt = new OptionsBuilder()
                .include(LazyListBenchmark.class.getSimpleName())
                .addProfiler("gc")
                .addProfiler("stack", "lines=5;top=10")
                .addProfiler("comp")
                .build();
        new Runner(opt).run();
    }

    private List<String> items;

    @Param({"100", "1000", "10000"})
    private int size;

    @Setup
    public void setup() {
        items = java.util.stream.IntStream.range(0, size)
                .mapToObj(String::valueOf)
                .toList();
    }

    @Benchmark
    public int simpleTraversal() {
        LazyList<String> list = SimpleLazyList.of(items);
        int count = 0;
        while (!list.isEmpty()) {
            list = list.tail();
            count++;
        }
        return count;
    }

    @Benchmark
    public int partitionedTraversal() {
        LazyList<List<String>> list = PartitionedLazyList.of(items, 10);
        int count = 0;
        while (!list.isEmpty()) {
            list = list.tail();
            count++;
        }
        return count;
    }
}