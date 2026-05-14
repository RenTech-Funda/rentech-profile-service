package com.floweytech.agrotrack.profile.interfaces.rest.transform;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
                entity.getId(),
                entity.getProfileId() != null ? entity.getProfileId().value() : null,
                entity.getUserId().value(),
                entity.getPersonName().getFirstName(),
                entity.getPersonName().getLastName(),
                entity.getPersonName().getFullName(),
                entity.getPhotoUrl()
        );
    }
}