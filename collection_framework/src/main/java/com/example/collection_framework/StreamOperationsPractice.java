package com.example.collection_framework;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamOperationsPractice {

        public static void main(String[] args) {
                List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                System.out.println("Original numbers: " + numbers);

                // 1. filter: Get only even numbers
                List<Integer> evens = numbers.stream()
                                .filter(n -> n % 2 == 0)
                                .collect(Collectors.toList());
                System.out.println("Even numbers (filter): " + evens);

                // 2. map: Square all numbers
                List<Integer> squares = numbers.stream()
                                .map(n -> n * n)
                                .toList();
                System.out.println("Squared numbers (map): " + squares);

                // 3. reduce: Sum all numbers
                int sum = numbers.stream()
                                .reduce(0, (a, b) -> a + b); // or Integer::sum
                System.out.println("Sum of numbers (reduce): " + sum);

                // 4. collect: Grouping operations (combined map, filter, collect)
                // Get squares of even numbers
                List<Integer> evenSquares = numbers.stream()
                                .filter(n -> n % 2 == 0)
                                .map(n -> n * n)
                                .toList();
                System.out.println("Squares of even numbers: " + evenSquares);
        }
}
