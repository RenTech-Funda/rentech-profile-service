package com.floweytech.agrotrack.profile.application.internal.commandservice;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.domain.model.commands.CreateProfileCommand;
import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePersonNameCommand;
import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePhotoUrlCommand;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;
import com.floweytech.agrotrack.profile.domain.services.ProfileCommandService;
import com.floweytech.agrotrack.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(CreateProfileCommand command) {
        var userId = new UserId(command.userId());

        if (profileRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("Profile with user id " + command.userId() + " already exists");
        }

        var profile = new Profile(
                command.userId(),
                command.firstName(),
                command.lastName(),
                command.photoUrl()
        );
        var savedProfile = profileRepository.save(profile);
        return Optional.of(savedProfile);
    }

    @Override
    public Optional<Profile> handle(UpdatePersonNameCommand command) {
        var profileId = new ProfileId(command.profileId());
        var profile = profileRepository.findByProfileId(profileId);

        if (profile.isEmpty()) {
            throw new IllegalArgumentException("Profile with id " + command.profileId() + " not found");
        }

        var profileToUpdate = profile.get();
        profileToUpdate.UpdatePersonName(command);
        var updatedProfile = profileRepository.save(profileToUpdate);
        return Optional.of(updatedProfile);
    }

    @Override
    public Optional<Profile> handle(UpdatePhotoUrlCommand command) {
        var profileId = new ProfileId(command.profileId());
        var profile = profileRepository.findByProfileId(profileId);

        if (profile.isEmpty()) {
            throw new IllegalArgumentException("Profile with id " + command.profileId() + " not found");
        }

        var profileToUpdate = profile.get();
        profileToUpdate.UpdatePhotoUrl(command);
        var updatedProfile = profileRepository.save(profileToUpdate);
        return Optional.of(updatedProfile);
    }
}
