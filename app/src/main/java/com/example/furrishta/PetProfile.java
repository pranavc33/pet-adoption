package com.example.furrishta;

public class PetProfile {
    private String animalType;
    private String ownerName;
    private String ownerContact;
    private String petName;
    private String petAge;
    private String petBreed;
    private String petDescription;

    public PetProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(PetProfile.class)
    }

    public PetProfile(String animalType, String ownerName, String ownerContact, String petName, String petAge, String petBreed, String petDescription) {
        this.animalType = animalType;
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petDescription = petDescription;
    }

    // Getters and setters
    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }
}
