package com.floweytech.agrotrack.profile.domain.model.aggregates;

import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePersonNameCommand;
import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePhotoUrlCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    @DisplayName("Debería actualizar el nombre de la persona correctamente")
    void shouldUpdatePersonNameCorrectly() {
        // Arrange
        Profile profile = new Profile(1L, "Diego", "Silva", "https://photo.com/diego");
        UpdatePersonNameCommand command = new UpdatePersonNameCommand(1L, "Diesoks", "Tech");

        // Act
        profile.UpdatePersonName(command);

        // Assert
        assertEquals("Diesoks", profile.getPersonName().getFirstName());
        assertEquals("Tech", profile.getPersonName().getLastName());
    }

    @Test
    @DisplayName("Debería actualizar la URL de la foto correctamente")
    void shouldUpdatePhotoUrlCorrectly() {
        // Arrange
        Profile profile = new Profile(1L, "Diego", "Silva", "https://photo.com/diego");
        UpdatePhotoUrlCommand command = new UpdatePhotoUrlCommand(1L, "https://photo.com/new-avatar");

        // Act
        profile.UpdatePhotoUrl(command);

        // Assert
        assertEquals("https://photo.com/new-avatar", profile.getPhotoUrl());
    }
}