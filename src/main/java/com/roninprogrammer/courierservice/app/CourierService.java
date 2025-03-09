package com.roninprogrammer.courierservice.app;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import com.roninprogrammer.courierservice.services.DeliveryCostCalculator;
import com.roninprogrammer.courierservice.services.DeliveryCostCalculatorTest;
import com.roninprogrammer.courierservice.services.OfferService;
import com.roninprogrammer.courierservice.services.OfferServiceTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class CourierService {
    private static final Logger logger = Logger.getLogger(CourierService.class.getName());
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMenu();
                int choice = getUserChoice(scanner);
                switch (choice) {
                    case 1 -> processCostEstimation(scanner);
                    case 2 -> processDeliveryTimeEstimation(scanner);
                    case 3 -> {
                        System.out.println("\n Thank you for using Kiki's Courier Service! Bye Bye!");
                        executor.shutdown();
                    }
                    default -> System.out.println("Invalid choice! Please select a valid option.");
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n********************************************");
        System.out.println("*          KIKI'S COURIER SERVICE          *");
        System.out.println("********************************************");
        System.out.println("1. Calculate Delivery Cost Estimation with Offers");
        System.out.println("2. Calculate Estimated Time for your Package");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void processCostEstimation(Scanner scanner) {
        System.out.print("Enter Base Delivery Cost: ");
        double baseDeliveryCost = scanner.nextDouble();

        List<Parcel> packages = getPackagesInput(scanner);
        for (Parcel pkg : packages) {
            double cost = baseDeliveryCost + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
            pkg.setDiscount(OfferService.getDiscount(pkg, cost));
            pkg.setTotalCost(cost - pkg.getDiscount());
        }

        displayCostSummary(baseDeliveryCost, packages);
    }

    private static void processDeliveryTimeEstimation(Scanner scanner) {
        System.out.print("Enter Base Delivery Cost: ");
        double baseDeliveryCost = scanner.nextDouble();

        List<Parcel> packages = getPackagesInput(scanner);

        System.out.print("Enter Number of Vehicles: ");
        int numberOfVehicles = scanner.nextInt();
        System.out.print("Enter Max Speed (km/hr): ");
        double maxSpeed = scanner.nextDouble();
        System.out.print("Enter Max Carriable Weight (kg): ");
        double maxCarriableWeight = scanner.nextDouble();

        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < numberOfVehicles; i++) {
            vehicles.add(new Vehicle(maxSpeed, maxCarriableWeight));
        }

        System.out.println("\n Processing Delivery Cost & Estimated Time...");
        DeliveryCostCalculator.calculate(packages, vehicles, scanner, executor);
        displayDeliverySummary(packages);
    }

    private static List<Parcel> getPackagesInput(Scanner scanner) {
        System.out.print("Enter Number of Packages: ");
        int numberOfPackages = scanner.nextInt();
        List<Parcel> packages = new ArrayList<>();
        for (int i = 0; i < numberOfPackages; i++) {
            System.out.print("Enter Package Details (ID, Weight, Distance, Offer Code): ");
            String id = scanner.next();
            double weight = scanner.nextDouble();
            double distance = scanner.nextDouble();
            String offerCode = scanner.next();
            packages.add(new Parcel(id, weight, distance, offerCode));
        }
        return packages;
    }

    private static void displayCostSummary(double baseDeliveryCost, List<Parcel> packages) {
        System.out.println("\n Package Delivery Cost Summary ");
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-10s | %-10s |\n", "Package", "Weight(kg)", "Distance(km)", "Offer Code");
        System.out.println("------------------------------------------------------------");
        for (Parcel pkg : packages) {
            System.out.printf("| %-10s | %-10.2f | %-10.2f | %-10s |\n", 
                    pkg.getId(), pkg.getWeight(), pkg.getDistance(), pkg.getOfferCode());
        }
        System.out.println("------------------------------------------------------------\n");
    }

    private static void displayDeliverySummary(List<Parcel> packages) {
        System.out.println("\n Package Delivery Time Summary ");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-10s | %-10s |\n", "Package", "Discount", "Total Cost", "Est. Time");
        System.out.println("-----------------------------------------------------------");
        for (Parcel pkg : packages) {
            System.out.printf("| %-10s | %-10.2f | %-10.2f | %-10.2f |\n", 
                    pkg.getId(), pkg.getDiscount(), pkg.getTotalCost(), pkg.getDeliveryTime());
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("Calculation Complete! Thank you for using Kiki's Courier Service!");
    }

}
