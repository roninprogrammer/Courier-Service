package com.roninprogrammer.courierservice.app;

import com.roninprogrammer.courierservice.model.Parcel;
import com.roninprogrammer.courierservice.model.Vehicle;
import com.roninprogrammer.courierservice.services.DeliveryCostCalculator;
import com.roninprogrammer.courierservice.services.DeliveryCostCalculatorTest;
import com.roninprogrammer.courierservice.services.OfferService;
import com.roninprogrammer.courierservice.services.OfferServiceTest;

import java.util.ArrayList;
import java.util.Comparator;
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
                        if (!executor.isShutdown()) {
                            executor.shutdownNow();
                        }
                        return;
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
         double baseDeliveryCost = getValidDouble(scanner, "Enter Base Delivery Cost: ");
        
                List<Parcel> packages = getPackagesInput(scanner);
                for (Parcel pkg : packages) {
                    double cost = baseDeliveryCost + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
                    pkg.setDiscount(OfferService.getDiscount(pkg, cost));
                    pkg.setTotalCost(cost - pkg.getDiscount());
                }
        
                displayCostSummary(baseDeliveryCost, packages);
                System.out.println("Calculation Complete! Thank you for using Kiki's Courier Service!\n");
            }
        
            private static double getValidDouble(Scanner scanner, String prompt) {
                double value;
                while (true) {
                    System.out.print(prompt);
                    if (scanner.hasNextDouble()) {
                        value = scanner.nextDouble();
                        if (value > 0) break;
                        else System.out.println("Value must be positive.");
                    } else {
                        System.out.println("Invalid input! Please enter a valid number.");
                        scanner.next(); // Consume invalid input
                    }
                }
                return value; 
               
            }
        
        private static void processDeliveryTimeEstimation(Scanner scanner) {
         double baseDeliveryCost = getValidDouble(scanner, "Enter Base Delivery Cost: ");

        List<Parcel> packages = getPackagesInput(scanner);

        //Make sure total cost is calc before calling deliverycostcalculator

        for (Parcel pkg : packages) {
            double cost = baseDeliveryCost + (pkg.getWeight() * 10) + (pkg.getDistance() * 5);
            pkg.setDiscount(OfferService.getDiscount(pkg, cost));
            pkg.setTotalCost(cost - pkg.getDiscount());
        }

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
        DeliveryCostCalculator.calculate(packages, vehicles);
        roundOffDeliveryTimes(packages);
        displayDeliverySummary(packages);
        displayDetailDelivery(packages, vehicles);
    }

    private static void roundOffDeliveryTimes(List<Parcel> packages) {
        for (int i = 0; i < packages.size(); i++) {
            packages.get(i).setEstimatedDeliveryTime(
                Math.round(packages.get(i).getEstimatedDeliveryTime() * 100.0) / 100.0
            );
        }
    }

    private static List<Parcel> getPackagesInput(Scanner scanner) {
        int numberOfPackages = getValidInt(scanner, "Enter Number of Packages: ");
                List<Parcel> packages = new ArrayList<>();
                for (int i = 0; i < numberOfPackages; i++) {
                    System.out.print("Enter Package Details (ID, Weight, Distance, Offer Code): ");
                    String id = scanner.next();
                    
                    double weight = getValidDouble(scanner, "Enter package weight (kg): ");
                    double distance = getValidDouble(scanner, "Enter package distance (km): ");
                    String offerCode = scanner.next(); // Offer code can be any string
                    
                    packages.add(new Parcel(id, weight, distance, offerCode));
                }
                return packages;
            }
        

            private static int getValidInt(Scanner scanner, String prompt) {
                int value;
                while (true) {
                    System.out.print(prompt);
                    if (scanner.hasNextInt()) {
                        value = scanner.nextInt();
                        if (value > 0) break;
                        else System.out.println("Number must be positive.");
                    } else {
                        System.out.println("Invalid input! Please enter a valid integer.");
                        scanner.next(); 
                    }
                }
                return value;
            }
        
    
        private static void displayCostSummary(double baseDeliveryCost, List<Parcel> packages) {
        System.out.println("\n Package Delivery Cost Summary ");
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-10s |\n", "Package", "Discount", "Total Cost");
        System.out.println("------------------------------------------------------------");
        for (Parcel pkg : packages) {
            System.out.printf("|  %-10s | %-10s | %-10s |\n", 
            pkg.getId(), formatNumber(pkg.getDiscount()), formatNumber(pkg.getTotalCost()));
        }
        System.out.println("------------------------------------------------------------\n");
        for (Parcel pkg : packages) {
            System.out.println("Package: " + pkg.getId());
            System.out.println("Base Delivery Cost: " + formatNumber(baseDeliveryCost));
            System.out.println("Weight: " + formatNumber(pkg.getWeight())  + "kg | Distance: " + formatNumber(pkg.getDistance()) + "km");
            System.out.println("Offer Code: " + pkg.getOfferCode());
        
            if (pkg.getDiscount() == 0) {
                System.out.println("Discount: (Offer not applicable as criteria not met)");
            } else {
                System.out.printf("Discount Applied: - %.2f\n", formatNumber(pkg.getDiscount()));
            }
        
            // Delivery Cost Breakdown
            double weightCost = pkg.getWeight() * 10;
            double distanceCost = pkg.getDistance() * 5;
            System.out.printf("  %.2f + (%.2f * 10) + (%.2f * 5)\n", baseDeliveryCost, pkg.getWeight(), pkg.getDistance());
            System.out.printf("  Total Cost Before Discount: %.2f\n", baseDeliveryCost + (weightCost) + (distanceCost));
            System.out.printf("  Discount: -%.2f\n", pkg.getDiscount());
            System.out.printf("  Final Total Cost: %.2f\n", pkg.getTotalCost());;
            
        System.out.println("------------------------------------------------------------\n");
        }
    }

    private static void displayDeliverySummary(List<Parcel> packages) {
          packages.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
        System.out.println("\n Package Delivery Time Summary ");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("| %-10s | %-10s | %-10s | %-10s |\n", "Package", "Discount", "Total Cost", "Est. Time");
        System.out.println("-----------------------------------------------------------");
        for (Parcel pkg : packages) {
            System.out.printf("| %-10s | %-10.2f | %-10.2f | %-10.2f |\n", 
                    pkg.getId(), pkg.getDiscount(), pkg.getTotalCost(), pkg.getEstimatedDeliveryTime());
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("Calculation Complete! Thank you for using Kiki's Courier Service!");
    }

    private static void displayDetailDelivery(List<Parcel> packages, List<Vehicle> vehicles){
        System.out.println("\n  Detailed Package Delivery Breakdown ");
    System.out.println("-----------------------------------------------------------");

    double currentTime = 0.0;
    List<Parcel> remainingPackages = new ArrayList<>(packages);

    while (!remainingPackages.isEmpty()) {
        vehicles.sort(Comparator.comparingDouble(Vehicle::getAvailableAt));
        Vehicle availableVehicle = vehicles.get(0);
        
        List<Parcel> assignedPackages = new ArrayList<>();
        double totalWeight = 0.0;
        
        for (Parcel pkg : new ArrayList<>(remainingPackages)) {
            if (totalWeight + pkg.getWeight() <= availableVehicle.getMaxWeight()) {
                assignedPackages.add(pkg);
                totalWeight += pkg.getWeight();
            }
        }
        
        remainingPackages.removeAll(assignedPackages);

        if (!assignedPackages.isEmpty()) {
            double maxDeliveryTime = assignedPackages.stream()
                    .mapToDouble(pkg -> pkg.getDistance() / availableVehicle.getSpeed())
                    .max()
                    .orElse(0.0);

            double roundedTime = Math.round(maxDeliveryTime * 100.0) / 100.0;
            availableVehicle.setAvailableAt(currentTime + 2 * roundedTime);
            
            System.out.printf("\n  Vehicle Assigned: %s\n", availableVehicle);
            System.out.println("  Packages Assigned:");
            assignedPackages.forEach(pkg -> System.out.printf("   - %s\n", pkg));
            System.out.printf(" Estimated Delivery Time: %.2f hrs\n", roundedTime);

            currentTime += roundedTime;
        }
    }

    System.out.println("-----------------------------------------------------------");
    System.out.println("All packages delivered successfully!");
    }

    private static Object formatNumber(double number) {
        return (number % 1 == 0) ? (int) number : number; 
    }
    

}
