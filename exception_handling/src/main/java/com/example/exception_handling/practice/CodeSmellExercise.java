package com.example.exception_handling.practice;


/**
 * This class demonstrates "Code Smells" and how to refactor them 
 * based on Clean Code (Chapters 1-3).
 */
public class CodeSmellExercise {

    // =========================================================================
    // BEFORE: Bad Code (Contains Smells)
    // =========================================================================
    
    /**
     * Code Smells present:
     * 1. Meaningless names (p, b, amt, d)
     * 2. Method doing more than one thing: validates user, calculates discount, charges money, saves DB.
     * 3. Magic Numbers (0.10)
     * 4. Mixing levels of abstraction.
     * 5. Poor formatting and no structure.
     */
    public boolean process(String p, double amt, boolean b) {
        if (p != null && !p.isEmpty()) {
            if (b) {
                double d = amt - (amt * 0.10); // magic number for 10% discount
                System.out.println("Charging user: " + d);
                // simulate save
                System.out.println("Saving to DB");
                return true;
            } else {
                System.out.println("Charging user: " + amt);
                // simulate save
                System.out.println("Saving to DB");
                return true;
            }
        }
        return false;
    }

    // =========================================================================
    // AFTER: Clean Code (Refactored)
    // =========================================================================

    private static final double PREMIUM_DISCOUNT_RATE = 0.10;

    /**
     * Better Name: Intention-revealing.
     * Doing One Thing: Processing checkout logic step-by-step using smaller functions.
     * Formatting: Code is organized top-down. Constants replace magic numbers.
     */
    public void processCheckout(String userId, double amount, boolean isPremiumUser) {
        validateUser(userId);
        double finalAmount = calculateFinalAmount(amount, isPremiumUser);
        chargeUser(finalAmount);
        saveTransactionToDatabase(userId, finalAmount);
    }

    private void validateUser(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Invalid internal User ID.");
        }
    }

    private double calculateFinalAmount(double baseAmount, boolean isPremiumUser) {
        if (isPremiumUser) {
            return baseAmount - (baseAmount * PREMIUM_DISCOUNT_RATE);
        }
        return baseAmount;
    }

    private void chargeUser(double finalAmount) {
        // One level of abstraction. Just stating we charge the user.
        System.out.println("Charging user an amount of: $" + finalAmount);
    }

    private void saveTransactionToDatabase(String userId, double amount) {
        // One level of abstraction.
        System.out.println("Saving transaction of $" + amount + " for user " + userId + " to database.");
    }
}
