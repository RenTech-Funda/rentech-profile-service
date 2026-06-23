package com.floweytech.agrotrack.profile.application.internal.commandservice;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.domain.model.commands.CreateProfileCommand;
import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePersonNameCommand;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;
import com.floweytech.agrotrack.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileCommandServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileCommandServiceImpl profileCommandService;

    @Test
    @DisplayName("CreateProfile exitoso cuando no existe un perfil previo para el usuario")
    void handleCreateProfileSuccess() {
        // Arrange
        CreateProfileCommand command = new CreateProfileCommand(42L, "Daniel", "Crispin", "http://image.url");
        Profile mockSavedProfile = new Profile(42L, "Daniel", "Crispin", "http://image.url");

        when(profileRepository.existsByUserId(any(UserId.class))).thenReturn(false);
        when(profileRepository.save(any(Profile.class))).thenReturn(mockSavedProfile);

        // Act
        Optional<Profile> result = profileCommandService.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(new UserId(42L), result.get().getUserId());
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    @DisplayName("CreateProfile lanza excepción si el UserId ya tiene un perfil registrado")
    void handleCreateProfileThrowsExceptionWhenProfileExists() {
        // Arrange
        CreateProfileCommand command = new CreateProfileCommand(42L, "Daniel", "Crispin", "http://image.url");
        when(profileRepository.existsByUserId(any(UserId.class))).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            profileCommandService.handle(command);
        });

        assertEquals("Profile with user id 42 already exists", exception.getMessage());
        verify(profileRepository, never()).save(any(Profile.class));
    }

    @Test
    @DisplayName("UpdatePersonName lanza excepción si el perfil no existe")
    void handleUpdatePersonNameThrowsExceptionWhenNotFound() {
        // Arrange
        UpdatePersonNameCommand command = new UpdatePersonNameCommand(99L, "Henry", "Mendoza");
        when(profileRepository.findByProfileId(any(ProfileId.class))).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            profileCommandService.handle(command);
        });

        assertEquals("Profile with id 99 not found", exception.getMessage());
        verify(profileRepository, never()).save(any(Profile.class));
    }
}