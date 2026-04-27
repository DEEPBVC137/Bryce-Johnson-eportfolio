package iT145PetBoardingSystem;

public class Dog extends Pet {
    private boolean grooming;

    public Dog() {
        super();
        this.grooming = false;
    }

    public Dog(String petType, String petName, int petAge, int daysStay, String ownerName, boolean grooming) {
        super(petType, petName, petAge, daysStay, ownerName);
        this.grooming = grooming;
    }

    public boolean isGrooming() {
        return grooming;
    }

    public void setGrooming(boolean grooming) {
        this.grooming = grooming;
    }

    @Override
    public void displayPetInfo() {
        super.displayPetInfo();
        System.out.println("Grooming Required: " + (grooming ? "Yes" : "No"));
    }
}