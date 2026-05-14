package com.floweytech.agrotrack.profile.domain.services;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> getByProfileId(ProfileId profileId);
    Optional<Profile> getByUserId(UserId userId);
    Optional<Profile> getByPersonName(String firstName, String lastName);
    List<Profile> searchByName(String searchTerm);
}
