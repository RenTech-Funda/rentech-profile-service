package com.floweytech.agrotrack.profile.shared.domain.model.events;

public record UserRegisteredEvent(
        Long userId,
        String firstName,
        String lastName,
        String photoUrl
) {
}