# Courier Service - Coding Challenge

## Overview
This java **Command Line Interface(CLI)** calculates the **total delivery cost** and **estimated delivery time** for a set of packages, considering base cost, weight, distance, and available vehicles. The solution adheres to SOLID principles and is designed for scalability.

## Features
- Computes the **total delivery cost** for each package, including applicable discounts.
- Estimates the **delivery time** using available vehicles, optimizing package grouping for efficiency.
- Supports multiple **offer codes** with predefined discount rules.
- Handles invalid offer codes gracefully.
- Ensures efficient package delivery by maximizing vehicle utilization.

## Prerequisites
- **Java 11+**
- **Gradle (Recommended for building & running)**
- **JUnit (For unit testing, if required)**

## Installation
Clone the repository and open it in Android Studio:
```bash
$ git clone <repo-url>
$ cd courierservice
```

Build the project and run it on an emulator or physical device. 

## Usage 
Launch the app and input required package and vechicle details through the provided UI. 

```bash
base_delivery_cost no_of_packages
pkg_id1 pkg_weight1_in_kg distance1_in_km offer_code1
...
no_of_vehicles max_speed max_carriable_weight
```

## Example Input
```bash
100 3
PKG1 5 5 OFR001
PKG2 15 5 OFR002
PKG3 10 100 OFR003
2 70 200
```

## Example Output
```bash
PKG1 0 175 3.98
PKG2 0 275 1.78
PKG3 35 665 1.42
```

### Business Logic 
## 1. Delivery Cost Calculation

Formula : 
```bash
Total Delivery Cost = Base Cost + (Weight * 10) + (Distance * 5) - Discount
```

    - Only one offer code is applicable per package.

    - If an invalid offer code is provided, the discount is 0.

If an invalid offer code is provided, the discount is 0.2. Delivery Time Calculation

    - Vehicles deliver packages in batches while respecting their weight capacity.

    - Vehicles always travel at max speed and return to the source before the next trip.
 
**Priority**:

    1.Maximize number of packages per trip.

    2.Prefer heavier packages when tie occurs.

    3.Prioritize shortest delivery time if weights are the same.


### Assumption 
- All destinations can be reached in a single route.
  
- Vehicles are always available for delivery.

- Input format follows the given specifications.

### 1. Installation & Running the application

```bash
git clone <repo-url>
cd courierservice
```

### 2. Compile the project: 

```bash
 javac -d out src/main/java/com/roninprogrammer/courierservice/app/CourierService.java
```

### 3. Run the application:
```bash
java -cp out com.roninprogrammer.courierservice.app.CourierService
```


### Testing
Run unit tests using JUnit.
```bash
./gradlew test
```

### Design Principles Followed
- SOLID Principles: Ensures maintainability and scalability.
- Test-Driven Development (TDD): Includes test cases for reliability.
- Clean Code Practices: Readable and well-documented code structure.


## Future Enhancements

- Add dynamic offer codes through configuration.

- Implement API integration for real-time data.

- Enhance error handling for unexpected inputs.|

## Author 
Vicknesh Balsubramaniam 

## Disclaimer 
Please do not upload this solution to public repositories like GitHub, GitLab, or Bitbucket as per Everest Engineering's confidentiality requirements.

