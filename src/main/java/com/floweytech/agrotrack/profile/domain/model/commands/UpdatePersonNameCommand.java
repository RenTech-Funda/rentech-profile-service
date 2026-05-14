package com.floweytech.agrotrack.profile.domain.model.commands;

public record UpdatePersonNameCommand(
    Long profileId,
    String firstName,
    String lastName
) {
}
