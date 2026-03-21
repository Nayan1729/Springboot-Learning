package com.example.collection_framework;

import java.util.*;

public class CollectionChoicePractice {

    public static void main(String[] args) {
        System.out.println("--- Choosing the right collection practice ---");

        // 1. Need a frequently accessed ordered list of elements that can change size? -> ArrayList
        List<String> userNames = new ArrayList<>();
        userNames.add("Alice");
        userNames.add("Bob");
        userNames.add("Charlie");
        System.out.println("ArrayList of users: " + userNames);

        // 2. Need to ensure all elements are unique and order doesn't matter? -> HashSet
        Set<Integer> uniqueIds = new HashSet<>();
        uniqueIds.add(101);
        uniqueIds.add(102);
        uniqueIds.add(101); // Duplicate ignored
        System.out.println("HashSet of IDs (unique): " + uniqueIds);

        // 3. Need unique elements sorted in their natural order? -> TreeSet
        Set<String> sortedNames = new TreeSet<>(userNames);
        System.out.println("TreeSet of names (sorted): " + sortedNames);

        // 4. Need to associate keys with values, order doesn't matter? -> HashMap
        Map<String, Integer> userAges = new HashMap<>();
        userAges.put("Alice", 28);
        userAges.put("Bob", 35);
        System.out.println("HashMap of user ages: " + userAges);

        // 5. Need keys associated with values, sorted by keys? -> TreeMap
        Map<String, Integer> sortedUserAges = new TreeMap<>(userAges);
        System.out.println("TreeMap of user ages (sorted by keys): " + sortedUserAges);
    }
}
