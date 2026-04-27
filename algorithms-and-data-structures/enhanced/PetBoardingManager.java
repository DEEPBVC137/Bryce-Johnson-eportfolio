package iT145PetBoardingSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PetBoardingManager {
    private ArrayList<Pet> petList;

    public PetBoardingManager() {
        petList = new ArrayList<>();
    }

    public void addPet(Pet pet) {
        if (pet != null) {
            petList.add(pet);
        }
    }

    public void displayAllPets() {
        if (petList.isEmpty()) {
            System.out.println("No pets are currently boarding.");
            return;
        }

        for (int i = 0; i < petList.size(); i++) {
            System.out.println("Pet Record " + (i + 1));
            petList.get(i).displayPetInfo();
            System.out.println();
        }
    }

    public Pet searchPetByName(String petName) {
        for (Pet pet : petList) {
            if (pet.getPetName().equalsIgnoreCase(petName)) {
                return pet;
            }
        }
        return null;
    }

    public ArrayList<Pet> searchPetsByOwner(String ownerName) {
        ArrayList<Pet> matches = new ArrayList<>();

        for (Pet pet : petList) {
            if (pet.getOwnerName().equalsIgnoreCase(ownerName)) {
                matches.add(pet);
            }
        }

        return matches;
    }

    public boolean removePetByName(String petName) {
        for (int i = 0; i < petList.size(); i++) {
            if (petList.get(i).getPetName().equalsIgnoreCase(petName)) {
                petList.remove(i);
                return true;
            }
        }
        return false;
    }

    public void sortPetsByAge() {
        Collections.sort(petList, Comparator.comparingInt(Pet::getPetAge));
    }

    public void sortPetsByDaysStay() {
        Collections.sort(petList, Comparator.comparingInt(Pet::getDaysStay));
    }

    public int getPetCount() {
        return petList.size();
    }

    public ArrayList<Pet> getPetList() {
        return petList;
    }
}