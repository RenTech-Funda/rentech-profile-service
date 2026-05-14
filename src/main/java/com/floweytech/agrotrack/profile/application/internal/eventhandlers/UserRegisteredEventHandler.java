package com.floweytech.agrotrack.profile.application.internal.eventhandlers;

import com.floweytech.agrotrack.profile.domain.model.commands.CreateProfileCommand;
import com.floweytech.agrotrack.profile.domain.services.ProfileCommandService;
import com.floweytech.agrotrack.profile.shared.domain.model.events.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserRegisteredEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserRegisteredEventHandler.class);
    private final ProfileCommandService profileCommandService;

    public UserRegisteredEventHandler(ProfileCommandService profileCommandService) {
        this.profileCommandService = profileCommandService;
    }

    @EventListener
    public void on(UserRegisteredEvent event) {
        logger.info("Handling UserRegisteredEvent for userId: {}", event.userId());

        var command = new CreateProfileCommand(
                event.userId(),
                event.firstName(),
                event.lastName(),
                event.photoUrl()
        );

        try {
            var profile = profileCommandService.handle(command);
            if (profile.isPresent()) {
                logger.info("Profile created successfully for userId: {}", event.userId());
            } else {
                logger.warn("Profile creation returned empty for userId: {}", event.userId());
            }
        } catch (Exception e) {
            logger.error("Error creating profile for userId: {}", event.userId(), e);
        }
    }
}
