# Pseudocode for Courier Service Application

##  Overview
This document outlines the **pseudocode logic** for the **Courier Service CLI application**, which calculates **total delivery costs** and **estimated delivery times** based on package details and available vehicles.

---

## ** Program Structure**
### 1 **Define Offer Codes and Discount Criteria**
- **Class:** `OfferCodes`
- **Class:** `OfferDetails` (Stores discount percentages and eligibility rules)

### 22⃣ **Define `Package` Class**
- **Properties:**
    - `id`
    - `weight`
    - `distance`
    - `offerCode`
    - `discount`
    - `totalCost`
    - `deliveryTime`
- **Methods:** Getters and Setters

### 3 **Define `Vehicle` Class**
- **Properties:**
    - `speed`
    - `maxWeight`
    - `availableAt`
- **Methods:** Getters and Setters

### 4 **Define `OfferService` Class**
- **Method:** `getDiscount(pkg, cost)`
    - Determines applicable **discount** based on **offer code**.

### 5 **Define `DeliveryTimeCalculator` Class**
- **Method:** `calculate(packages, vehicles)`
    - Sort packages by **weight** (descending order).
    - Assign packages to the **vehicle with the least available time**.
    - Update **package delivery time** and **vehicle availability time**.

---

## ** Main Application Workflow (`CourierService` Class)**
### 1 **Initialize Logger and ExecutorService**
- Used for logging errors and handling multi-threading.

### 2 **Display User Menu (Infinite Loop)**
```plaintext
1. Calculate Offer Criteria
2. Calculate Estimated Time for Your Package
3. Exit
