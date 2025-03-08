package com.roninprogrammer.courierservice.app;

import com.roninprogrammer.courierservice.model.Package;

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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n********************************************");
            System.out.println("*          KIKI'S COURIER SERVICE       *");
            System.out.println("********************************************");
            System.out.println("1. Calculate Offer Criteria");
            System.out.println("2. Calculate Estimated Time for your Package");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 3) {
                System.out.println("\n Thank you for using Kiki's Courier Service! Stay legendary!");
                executor.shutdown();
                break;
            }

            try {
                System.out.print("\n Enter Base Delivery Cost: ");
                double baseDeliveryCost = scanner.nextDouble();
                System.out.print(" Enter number of packages: ");
                int numberOfPackages = scanner.nextInt();

                List<Package> packages = new ArrayList<>();
                for (int i = 0; i < numberOfPackages; i++) {
                    System.out.print(" Enter package details (ID, Weight, Distance, Offer Code): ");
                    String id = scanner.next();
                    double weight = scanner.nextDouble();
                    double distance = scanner.nextDouble();
                    String offerCode = scanner.next();
                    packages.add(new Package(id, weight, distance, offerCode));
                }

                for (Package pkg : packages) {
                    double cost = baseDeliveryCost + (pkg.weight * 10) + (pkg.distance * 5);
                    pkg.discount = OfferService.getDiscount(pkg, cost);
                    pkg.totalCost = cost - pkg.discount;
                }

                System.out.println("\nOutput Format: pkg_id discount total_cost");
                for (Package pkg : packages) {
                    System.out.printf("%s %.2f %.2f\n", pkg.id, pkg.discount, pkg.totalCost);
                }
            } catch (Exception e) {
                logger.severe("Error processing input: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
