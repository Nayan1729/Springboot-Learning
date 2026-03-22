import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Exercise 8: Implement async data processing with CompletableFuture.
 * Practice 4: CompletableFuture for async programming.
 * 
 * This class demonstrates various async programming techniques in Java.
 */
public class CompletableFuturePractice {

    public static void main(String[] args) {
        System.out.println("Starting Async Data Processing Exercises...");

        // 1. Basic Async Execution
        basicAsync();

        // 2. Chaining Tasks (thenApply, thenAccept)
        chainingTasks();

        // 3. Combining Tasks (thenCombine)
        combiningTasks();

        // 4. Multiple Parallel Tasks (allOf)
        multipleParallelTasks();

        // 5. Error Handling (exceptionally)
        errorHandlingExample();

        // Small delay to ensure all async tasks finish before JVM exits (in a real app, use Thread.join() or awaitTermination())
        sleep(5000);
        System.out.println("All exercises completed.");
    }

    private static void basicAsync() {
        System.out.println("\n--- Basic Async ---");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            sleep(500);
            System.out.println("Basic async task executed in: " + Thread.currentThread().getName());
        });
        
        // Wait for it because we are in a main method (usually not done this way)
        future.join();
    }

    private static void chainingTasks() {
        System.out.println("\n--- Chaining Tasks (thenApply, thenAccept) ---");
        
        CompletableFuture<String> chain = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user data...");
            sleep(1000);
            return "John Doe";
        }).thenApply(name -> {
            System.out.println("Formatting name...");
            return "Hello, " + name.toUpperCase();
        }).thenApply(greeting -> {
            System.out.println("Adding timestamp...");
            return greeting + " (at " + System.currentTimeMillis() + ")";
        });

        chain.thenAccept(result -> System.out.println("Final Result: " + result));
    }

    private static void combiningTasks() {
        System.out.println("\n--- Combining Tasks (thenCombine) ---");
        
        CompletableFuture<Double> priceFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Getting product price...");
            sleep(800);
            return 19.99;
        });

        CompletableFuture<Double> taxFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Getting tax rate...");
            sleep(1200);
            return 0.15; // 15% tax
        });

        CompletableFuture<Double> totalFuture = priceFuture.thenCombine(taxFuture, (price, tax) -> price * (1 + tax));

        totalFuture.thenAccept(total -> System.out.println("Total Price including tax: " + total));
    }

    private static void multipleParallelTasks() {
        System.out.println("\n--- Multiple Parallel Tasks (allOf) ---");

        List<String> userIds = Arrays.asList("1", "2", "3", "4", "5");

        List<CompletableFuture<String>> futures = userIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> {
                    System.out.println("Processing User " + id);
                    sleep(1000);
                    return "User-" + id + "-Processed";
                }))
                .collect(Collectors.toList());

        // CompletableFuture.allOf returns Void, wait for ALL futures to complete.
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        // To get the values back into a list
        CompletableFuture<List<String>> allResults = allOfFuture.thenApply(v -> {
            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        });

        allResults.thenAccept(results -> System.out.println("Processed IDs: " + results));
    }

    private static void errorHandlingExample() {
        System.out.println("\n--- Error Handling (exceptionally) ---");

        CompletableFuture<Integer> fallbackFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Dangerous computation...");
            sleep(500);
            if (true) throw new RuntimeException("Oops! Something went wrong.");
            return 100;
        }).exceptionally(ex -> {
            System.out.println("Error caught: " + ex.getMessage());
            return 0; // Fallback value
        });

        fallbackFuture.thenAccept(val -> System.out.println("Result (with fallback): " + val));
    }

    private static void sleep(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
