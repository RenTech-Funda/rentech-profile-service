package com.floweytech.agrotrack.profile.application.internal.queryservice;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;
import com.floweytech.agrotrack.profile.domain.services.ProfileQueryService;
import com.floweytech.agrotrack.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> getByProfileId(ProfileId profileId) {
        return profileRepository.findByProfileId(profileId);
    }

    @Override
    public Optional<Profile> getByUserId(UserId userId) {
        return profileRepository.findByUserId(userId);
    }

    @Override
    public Optional<Profile> getByPersonName(String firstName, String lastName) {
        return profileRepository.findByPersonName_FirstNameAndPersonName_LastName(firstName, lastName);
    }

    @Override
    public List<Profile> searchByName(String searchTerm) {
        return profileRepository.searchByName(searchTerm);
    }
}
