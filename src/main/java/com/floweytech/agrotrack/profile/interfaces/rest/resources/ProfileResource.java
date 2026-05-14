package com.floweytech.agrotrack.profile.interfaces.rest.resources;

public record ProfileResource(
        Long id,
        Long profileId,
        Long userId,
        String firstName,
        String lastName,
        String fullName,
        String photoUrl
) {
}
