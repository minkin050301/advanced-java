import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimeCounterParallel {
    // counts primes in interval [from, to]
    public static int countPrimesInInterval(final int from, final int to) {
        int count = 0;
        for (int i = from; i <= to; i++)
            if (PrimeTester.isPrime(i))
                ++count;
        return count;
    }

    public static int countPrimesParallel(final int until, final int numThreads)
            throws ExecutionException, InterruptedException {

        List<Integer> results = new ArrayList<>();
        List<Future<Integer>> futures = new ArrayList<>();

        try (ExecutorService execServ = Executors.newFixedThreadPool(numThreads)) {
            int elementsPerThread = (until - 1) / numThreads;

            // divide interval into numThreads sub-intervals and add a future for each
            for (int i = 0; i < numThreads; i++) {
                int start = 2 + elementsPerThread * i;
                futures.add(execServ.submit(() ->
                        countPrimesInInterval(start, Math.min(start + elementsPerThread - 1, until))));
            }

            for (Future<Integer> future : futures)
                results.add(future.get());
        }

        // result is the sum of partial results
        return results.stream().reduce(0, Integer::sum);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int target = Integer.parseInt(args[0]);
        final int numThreads = Integer.parseInt(args[1]);

        final long startTime = System.currentTimeMillis();
        int count = countPrimesParallel(target, numThreads);
        final long endTime = System.currentTimeMillis();
        System.out.println("Duration for interval [2, " + target + "] is "
                + (endTime - startTime) + " ms\n" + count + " primes");
    }
}
