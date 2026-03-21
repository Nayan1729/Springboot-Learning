package com.example.collection_framework;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Employee {
    String name;
    String department;
    double salary;
}

public class ObjectProcessingPractice {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("John", "HR", 50000),
                new Employee("Jane", "IT", 85000),
                new Employee("Mike", "IT", 75000),
                new Employee("Alice", "Finance", 90000),
                new Employee("Bob", "HR", 45000));
        // 1. Filter: IT Employees earning more than 80000
        List<Employee> highEarnersIT = employees.stream()
                .filter(e -> e.getDepartment().equals("IT") && e.getSalary() > 80000)
                .toList();
        System.out.println("High earning IT employees: " + highEarnersIT);

        // 2. Map: Get list of employee names sorted alphabetically
        List<String> employeeNames = employees.stream()
                .map(Employee::getName)
                .sorted()
                .toList();
        System.out.println("Sorted employee names: " + employeeNames);

        // 3. Grouping: Group employees by Department using Collectors.groupingBy
        Map<String, List<Employee>> byDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println("Employees grouped by department: " + byDepartment);

        // 4. Reduce/Averaging: Calculate average salary for all employees
        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        System.out.println("Average company salary: $" + averageSalary);
    }
}
