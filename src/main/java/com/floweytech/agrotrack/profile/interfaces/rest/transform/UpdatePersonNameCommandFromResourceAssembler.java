package com.floweytech.agrotrack.profile.interfaces.rest.transform;

import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePersonNameCommand;
import com.floweytech.agrotrack.profile.interfaces.rest.resources.UpdatePersonNameResource;

public class UpdatePersonNameCommandFromResourceAssembler {
    public static UpdatePersonNameCommand toCommandFromResource(Long profileId, UpdatePersonNameResource resource) {
        return new UpdatePersonNameCommand(
                profileId,
                resource.firstName(),
                resource.lastName()
        );
    }
}

