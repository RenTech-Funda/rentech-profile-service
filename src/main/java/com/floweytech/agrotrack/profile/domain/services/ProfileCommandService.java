package com.floweytech.agrotrack.profile.domain.services;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.domain.model.commands.CreateProfileCommand;
import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePersonNameCommand;
import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePhotoUrlCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdatePersonNameCommand command);
    Optional<Profile> handle(UpdatePhotoUrlCommand command);
}
