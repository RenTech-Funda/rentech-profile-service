package com.floweytech.agrotrack.profile.application.internal.eventhandlers;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.domain.model.commands.CreateProfileCommand;
import com.floweytech.agrotrack.profile.domain.services.ProfileCommandService;
import com.floweytech.agrotrack.profile.shared.domain.model.events.UserRegisteredEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegisteredEventHandlerTest {

    @Mock
    private ProfileCommandService profileCommandService;

    @InjectMocks
    private UserRegisteredEventHandler userRegisteredEventHandler;

    @Test
    @DisplayName("Debería capturar el evento de RabbitMQ, procesarlo y llamar al Command Service")
    void shouldHandleUserRegisteredEventSuccessfully() {
        // Arrange
        UserRegisteredEvent event = new UserRegisteredEvent(10L, "Rafael", "Gomez", "http://avatar.com/rafa");
        Profile mockProfile = new Profile(10L, "Rafael", "Gomez", "http://avatar.com/rafa");

        when(profileCommandService.handle(any(CreateProfileCommand.class))).thenReturn(Optional.of(mockProfile));

        // Act
        userRegisteredEventHandler.on(event);

        // Assert & Captor (Verificar qué comando construyó internamente)
        ArgumentCaptor<CreateProfileCommand> commandCaptor = ArgumentCaptor.forClass(CreateProfileCommand.class);
        verify(profileCommandService, times(1)).handle(commandCaptor.capture());

        CreateProfileCommand executedCommand = commandCaptor.getValue();
        assertEquals(10L, executedCommand.userId());
        assertEquals("Rafael", executedCommand.firstName());
        assertEquals("Gomez", executedCommand.lastName());
        assertEquals("http://avatar.com/rafa", executedCommand.photoUrl());
    }

    @Test
    @DisplayName("Debería tolerar y capturar excepciones del servicio sin interrumpir el hilo del broker")
    void shouldHandleAndLogExceptionsWithoutCrashing() {
        // Arrange
        UserRegisteredEvent event = new UserRegisteredEvent(10L, "Rafael", "Gomez", "http://avatar.com/rafa");

        // Simulamos que el servicio revienta (ej. ID duplicado o error de BD)
        when(profileCommandService.handle(any(CreateProfileCommand.class)))
                .thenThrow(new IllegalArgumentException("Profile with user id 10 already exists"));

        // Act & Assert
        assertDoesNotThrow(() -> userRegisteredEventHandler.on(event));
        verify(profileCommandService, times(1)).handle(any(CreateProfileCommand.class));
    }
}