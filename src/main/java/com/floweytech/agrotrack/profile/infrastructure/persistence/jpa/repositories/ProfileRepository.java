package com.floweytech.agrotrack.profile.infrastructure.persistence.jpa.repositories;

import com.floweytech.agrotrack.profile.domain.model.aggregates.Profile;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByProfileId(ProfileId profileId);
    Optional<Profile> findByUserId(UserId userId);
    Optional<Profile> findByPersonName_FirstNameAndPersonName_LastName(String firstName, String lastName);
    boolean existsByProfileId(ProfileId profileId);
    boolean existsByUserId(UserId userId);

    @Query("SELECT p FROM Profile p WHERE " +
           "LOWER(p.personName.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.personName.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(CONCAT(p.personName.firstName, ' ', p.personName.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Profile> searchByName(@Param("searchTerm") String searchTerm);
}
