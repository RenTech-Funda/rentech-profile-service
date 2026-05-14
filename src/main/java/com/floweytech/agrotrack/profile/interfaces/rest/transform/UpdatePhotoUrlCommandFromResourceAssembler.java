package com.floweytech.agrotrack.profile.interfaces.rest.transform;

import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePhotoUrlCommand;
import com.floweytech.agrotrack.profile.interfaces.rest.resources.UpdatePhotoUrlResource;

public class UpdatePhotoUrlCommandFromResourceAssembler {
    public static UpdatePhotoUrlCommand toCommandFromResource(Long profileId, UpdatePhotoUrlResource resource) {
        return new UpdatePhotoUrlCommand(
                profileId,
                resource.photoUrl()
        );
    }
}

