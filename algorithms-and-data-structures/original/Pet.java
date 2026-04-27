package iT145PetBoardingSystem;

public class Pet {
    private String petType;
    private String petName;
    private int petAge;
    private int daysStay;
    private String ownerName;

    public Pet() {
    }

    public Pet(String petType, String petName, int petAge, int daysStay, String ownerName) {
        this.petType = petType;
        this.petName = petName;
        this.petAge = petAge;
        this.daysStay = daysStay;
        this.ownerName = ownerName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public int getDaysStay() {
        return daysStay;
    }

    public void setDaysStay(int daysStay) {
        this.daysStay = daysStay;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void displayPetInfo() {
        System.out.println("Pet Type: " + petType);
        System.out.println("Pet Name: " + petName);
        System.out.println("Pet Age: " + petAge);
        System.out.println("Days Staying: " + daysStay);
        System.out.println("Owner Name: " + ownerName);
    }
}