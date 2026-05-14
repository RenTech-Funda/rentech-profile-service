package com.floweytech.agrotrack.profile.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProfileId(Long value) {

    public ProfileId() {
        this(null);
    }
    public ProfileId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("ProfileId must be a positive non-null value.");
        }
    }

}
