package com.floweytech.agrotrack.profile.domain.model.commands;

public record CreateProfileCommand(
    Long userId,
    String firstName,
    String lastName,
    String photoUrl
) {
}
