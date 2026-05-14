package com.floweytech.agrotrack.profile.domain.model.commands;

public record UpdatePhotoUrlCommand(
    Long profileId,
    String photoUrl
) {
}
