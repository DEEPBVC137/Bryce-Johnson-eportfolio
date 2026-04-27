package iT145PetBoardingSystem;

public class BoardingDriver {
    public static void main(String[] args) {
        Dog dog = new Dog("Dog", "Buddy", 4, 3, "Bryce Johnson", true);
        Cat cat = new Cat("Cat", "Luna", 2, 2, "Skylin Hutchinson");

        dog.displayPetInfo();
        System.out.println();
        cat.displayPetInfo();
    }
}
