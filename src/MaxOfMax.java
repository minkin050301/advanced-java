import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MaxOfMax {
    public int calculateMax(Collection<List<Integer>> blocksOfNumbers, int numberThreads)
            throws ExecutionException, InterruptedException {

        if (blocksOfNumbers.size() == 0) {
            return Integer.MIN_VALUE;
        }

        List<Integer> results = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<>();

        // try with resources ensures the executor service is closed afterward
        try (ExecutorService execServ = Executors.newFixedThreadPool(numberThreads)) {
            for (List<Integer> block : blocksOfNumbers)
                futures.add(execServ.submit(() -> findMax(block)));

            for (Future<Integer> future : futures)
                results.add(future.get());
        }

        return findMax(results);
    }

    private Integer findMax(Collection<Integer> numbers) {
        Integer maxValue = Integer.MIN_VALUE;
        for (Integer number : numbers) {
            if (number > maxValue) {
                maxValue = number;
            }
        }
        return maxValue;
    }

    private Integer findMaxStream(Collection<Integer> numbers) {
        return numbers.parallelStream()
                .mapToInt(x -> x)
                .max()
                .orElse(Integer.MIN_VALUE);
    }
}
