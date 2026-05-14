package com.floweytech.agrotrack.profile.domain.model.aggregates;

import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePersonNameCommand;
import com.floweytech.agrotrack.profile.domain.model.commands.UpdatePhotoUrlCommand;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.PersonName;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;
import com.floweytech.agrotrack.profile.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Profile extends AuditableAbstractAggregateRoot<Profile> {
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "profile_id", unique = true))
    private ProfileId profileId;
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", unique = true, nullable = false))
    private UserId userId;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))
    })
    private PersonName personName;
    @Column(name = "photo_url")
    private String photoUrl;

    protected Profile() {
    }

    public Profile(Long userId, String firstName, String lastName, String photoUrl) {
        this.userId = new UserId(userId);
        this.personName = new PersonName(firstName, lastName);
        this.photoUrl = photoUrl;
    }

    @PostPersist
    protected void onPostPersist() {
        this.profileId = new ProfileId(this.getId());
    }

    public void UpdatePhotoUrl(UpdatePhotoUrlCommand command) {
        this.photoUrl = command.photoUrl();
    }

    public void UpdatePersonName(UpdatePersonNameCommand command) {
        this.personName = new PersonName(command.firstName(), command.lastName());
    }

}
