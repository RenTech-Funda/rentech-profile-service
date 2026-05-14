package com.floweytech.agrotrack.profile.application.internal.outboundservices.acl;

import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;
import com.floweytech.agrotrack.profile.domain.services.ProfileQueryService;
import com.floweytech.agrotrack.profile.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of Profile Context Facade for ACL
 */
@Service
public class ProfileContextFacadeImpl implements ProfileContextFacade {
    private final ProfileQueryService profileQueryService;

    public ProfileContextFacadeImpl(ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    @Override
    public Optional<Long> getProfileIdByUserId(Long userId) {
        var profile = profileQueryService.getByUserId(new UserId(userId));
        return profile.map(p -> p.getProfileId() != null ? p.getProfileId().value() : null);
    }

    @Override
    public boolean existsByProfileId(Long profileId) {
        var profile = profileQueryService.getByProfileId(new com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId(profileId));
        return profile.isPresent();
    }
}

