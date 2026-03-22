import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Exercise 9: Use ExecutorService for parallel tasks.
 * Demonstrates how to divide a large workload into small parallel units and collect their results.
 */
public class ParallelTasksDemo {

    public static void main(String[] args) {
        System.out.println("Starting Parallel Tasks with ExecutorService...");

        int dataSize = 1000;
        int numTasks = 10;
        int poolSize = 4;

        // Use a fixed pool for task management
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        List<Future<Integer>> futures = new ArrayList<>();

        System.out.println("Submitting " + numTasks + " parallel tasks to process " + dataSize + " items...");

        for (int i = 0; i < numTasks; i++) {
            final int taskId = i;
            Callable<Integer> task = () -> {
                int start = taskId * (dataSize / numTasks);
                int end = (taskId + 1) * (dataSize / numTasks);
                System.out.println("Task-" + taskId + " processing range: [" + start + ", " + end + ") in: " + Thread.currentThread().getName());
                
                // Simulate some work
                int taskResult = 0;
                for (int j = start; j < end; j++) {
                    taskResult += j;
                    simulateWork(2); // 2ms per item (total 200ms)
                }
                return taskResult;
            };
            
            futures.add(executor.submit(task));
        }

        // Processing results from the futures
        int totalSum = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                // Future.get() blocks until the specific task finishes
                Integer result = futures.get(i).get();
                System.out.println("Task-" + i + " result: " + result);
                totalSum += result;
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Task failed: " + e.getMessage());
            }
        }

        System.out.println("\nAll tasks completed. Global Sum: " + totalSum);

        // ALWAYS shut down the executor
        System.out.println("Shutting down executor...");
        executor.shutdown();

        try {
            // Wait for it to finalize
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("Main thread exiting.");
    }

    private static void simulateWork(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
