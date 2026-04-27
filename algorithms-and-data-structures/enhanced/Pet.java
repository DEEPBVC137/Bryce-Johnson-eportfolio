package iT145PetBoardingSystem;

public class Pet {
    private String petType;
    private String petName;
    private int petAge;
    private int daysStay;
    private String ownerName;

    public Pet() {
        this.petType = "Unknown";
        this.petName = "Unknown";
        this.petAge = 0;
        this.daysStay = 1;
        this.ownerName = "Unknown";
    }

    public Pet(String petType, String petName, int petAge, int daysStay, String ownerName) {
        setPetType(petType);
        setPetName(petName);
        setPetAge(petAge);
        setDaysStay(daysStay);
        setOwnerName(ownerName);
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        if (petType == null || petType.trim().isEmpty()) {
            this.petType = "Unknown";
        } else {
            this.petType = petType.trim();
        }
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        if (petName == null || petName.trim().isEmpty()) {
            this.petName = "Unknown";
        } else {
            this.petName = petName.trim();
        }
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        if (petAge < 0) {
            this.petAge = 0;
        } else {
            this.petAge = petAge;
        }
    }

    public int getDaysStay() {
        return daysStay;
    }

    public void setDaysStay(int daysStay) {
        if (daysStay < 1) {
            this.daysStay = 1;
        } else {
            this.daysStay = daysStay;
        }
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        if (ownerName == null || ownerName.trim().isEmpty()) {
            this.ownerName = "Unknown";
        } else {
            this.ownerName = ownerName.trim();
        }
    }

    public void displayPetInfo() {
        System.out.println("Pet Type: " + petType);
        System.out.println("Pet Name: " + petName);
        System.out.println("Pet Age: " + petAge);
        System.out.println("Days Staying: " + daysStay);
        System.out.println("Owner Name: " + ownerName);
    }
}