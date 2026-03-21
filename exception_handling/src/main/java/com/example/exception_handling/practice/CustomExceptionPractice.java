package com.example.exception_handling.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomExceptionPractice {

    /**
     * Simulates withdrawing money from an account.
     * Throws our custom InsufficientFundsException if the amount is greater than the balance.
     */
    public void withdrawMoney(double balance, double amountToWithdraw) {
        System.out.println("Attempting to withdraw: $" + amountToWithdraw + " from balance: $" + balance);
        
        if (amountToWithdraw > balance) {
            // Throw custom unchecked exception
            throw new InsufficientFundsException("Cannot withdraw $" + amountToWithdraw + ". Insufficient balance.");
        }
        
        double remainingBalance = balance - amountToWithdraw;
        System.out.println("Withdrawal successful. Remaining balance: $" + remainingBalance);
    }

    /**
     * Demonstrates using try-with-resources for automatic resource management.
     * This avoids the need for a finally block to close the BufferedReader.
     */
    public void readConfigFile(String filePath) {
        // The BufferedReader is automatically closed when the try block exits!
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            System.out.println("Read from config: " + line);
        } catch (IOException e) {
            System.err.println("Failed to read the config file. Reason: " + e.getMessage());
        }
    }
}
