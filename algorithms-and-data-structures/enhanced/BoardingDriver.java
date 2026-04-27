package iT145PetBoardingSystem;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardingDriver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PetBoardingManager manager = new PetBoardingManager();
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    addPet(scanner, manager);
                    break;

                case 2:
                    System.out.println();
                    System.out.println("All Boarding Pets");
                    manager.displayAllPets();
                    break;

                case 3:
                    searchByPetName(scanner, manager);
                    break;

                case 4:
                    searchByOwnerName(scanner, manager);
                    break;

                case 5:
                    manager.sortPetsByAge();
                    System.out.println("Pets sorted by age.");
                    break;

                case 6:
                    manager.sortPetsByDaysStay();
                    System.out.println("Pets sorted by days staying.");
                    break;

                case 7:
                    removePet(scanner, manager);
                    break;

                case 8:
                    System.out.println("Exiting program.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 8.");
            }

            System.out.println();
        }

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("Pet Boarding System Menu");
        System.out.println("1. Add a pet");
        System.out.println("2. Display all pets");
        System.out.println("3. Search for a pet by name");
        System.out.println("4. Search for pets by owner name");
        System.out.println("5. Sort pets by age");
        System.out.println("6. Sort pets by days staying");
        System.out.println("7. Remove a pet by name");
        System.out.println("8. Exit");
    }

    public static void addPet(Scanner scanner, PetBoardingManager manager) {
        System.out.println("Choose pet type");
        System.out.println("1. Dog");
        System.out.println("2. Cat");

        int petChoice = readInt(scanner, "Enter your choice: ");

        String petName = readNonEmptyString(scanner, "Enter pet name: ");
        int petAge = readInt(scanner, "Enter pet age: ");
        int daysStay = readInt(scanner, "Enter number of days staying: ");
        String ownerName = readNonEmptyString(scanner, "Enter owner name: ");

        if (petChoice == 1) {
            boolean grooming = readYesNo(scanner, "Does the dog need grooming? (yes/no): ");
            Dog dog = new Dog("Dog", petName, petAge, daysStay, ownerName, grooming);
            manager.addPet(dog);
            System.out.println("Dog added successfully.");
        } else if (petChoice == 2) {
            Cat cat = new Cat("Cat", petName, petAge, daysStay, ownerName);
            manager.addPet(cat);
            System.out.println("Cat added successfully.");
        } else {
            System.out.println("Invalid pet type. Pet was not added.");
        }
    }

    public static void searchByPetName(Scanner scanner, PetBoardingManager manager) {
        String petName = readNonEmptyString(scanner, "Enter the pet name to search for: ");
        Pet foundPet = manager.searchPetByName(petName);

        if (foundPet != null) {
            System.out.println("Pet found");
            foundPet.displayPetInfo();
        } else {
            System.out.println("No pet found with that name.");
        }
    }

    public static void searchByOwnerName(Scanner scanner, PetBoardingManager manager) {
        String ownerName = readNonEmptyString(scanner, "Enter the owner name to search for: ");
        ArrayList<Pet> matches = manager.searchPetsByOwner(ownerName);

        if (matches.isEmpty()) {
            System.out.println("No pets found for that owner.");
        } else {
            System.out.println("Matching pets");
            for (Pet pet : matches) {
                pet.displayPetInfo();
                System.out.println();
            }
        }
    }

    public static void removePet(Scanner scanner, PetBoardingManager manager) {
        String petName = readNonEmptyString(scanner, "Enter the pet name to remove: ");
        boolean removed = manager.removePetByName(petName);

        if (removed) {
            System.out.println("Pet removed successfully.");
        } else {
            System.out.println("No pet found with that name.");
        }
    }

    public static int readInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();

                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Please enter a non negative number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a whole number.");
                scanner.nextLine();
            }
        }
    }

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        String input;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be blank.");
            }
        }
    }

    public static boolean readYesNo(Scanner scanner, String prompt) {
        String input;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter yes or no.");
            }
        }
    }
}