package com.floweytech.agrotrack.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class PersonName {
    private String firstName;
    private String lastName;

    protected PersonName() {}

    public PersonName(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonName that = (PersonName) o;
        return firstName.equals(that.firstName) && lastName.equals(that.lastName);
    }
    @Override
    public int hashCode() {
        return 31 * firstName.hashCode() + lastName.hashCode();
    }
    @Override
    public String toString() {
        return getFullName();
    }

}
