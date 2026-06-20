package com.floweytech.agrotrack.profile.interfaces.rest;

import com.floweytech.agrotrack.profile.domain.model.valueobjects.ProfileId;
import com.floweytech.agrotrack.profile.domain.model.valueobjects.UserId;
import com.floweytech.agrotrack.profile.domain.services.ProfileCommandService;
import com.floweytech.agrotrack.profile.domain.services.ProfileQueryService;
import com.floweytech.agrotrack.profile.interfaces.rest.resources.ProfileResource;
import com.floweytech.agrotrack.profile.interfaces.rest.resources.UpdatePersonNameResource;
import com.floweytech.agrotrack.profile.interfaces.rest.resources.UpdatePhotoUrlResource;
import com.floweytech.agrotrack.profile.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.floweytech.agrotrack.profile.interfaces.rest.transform.UpdatePersonNameCommandFromResourceAssembler;
import com.floweytech.agrotrack.profile.interfaces.rest.transform.UpdatePhotoUrlCommandFromResourceAssembler;
import com.floweytech.agrotrack.profile.shared.infrastructure.security.AuthenticatedUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfileController {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public ProfileController(ProfileQueryService profileQueryService,
                             ProfileCommandService profileCommandService,
                             AuthenticatedUserProvider authenticatedUserProvider) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Operation(summary = "Get profile by profile id", description = "Get a profile by its profile id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResource> getProfileByProfileId(@PathVariable Long profileId) {
        var profile = profileQueryService.getByProfileId(new ProfileId(profileId));
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!authenticatedUserProvider.canAccessUser(profile.get().getUserId().value())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @Operation(summary = "Get profile by user id", description = "Get a profile by its user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResource> getProfileByUserId(@PathVariable Long userId) {
        if (!authenticatedUserProvider.canAccessUser(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var profile = profileQueryService.getByUserId(new UserId(userId));
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @Operation(summary = "Search profiles by name", description = "Search profiles by first name, last name, or full name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProfileResource>> searchProfilesByName(
            @RequestParam String name) {
        if (!authenticatedUserProvider.isAdministrator()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var profiles = profileQueryService.searchByName(name);
        var resources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Update person name", description = "Update the person name of a profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person name updated"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @PutMapping("/{profileId}/person-name")
    public ResponseEntity<ProfileResource> updatePersonName(
            @PathVariable Long profileId,
            @RequestBody UpdatePersonNameResource resource) {
        var existingProfile = profileQueryService.getByProfileId(new ProfileId(profileId));
        if (existingProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!authenticatedUserProvider.canAccessUser(existingProfile.get().getUserId().value())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var command = UpdatePersonNameCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var profile = profileCommandService.handle(command);
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @Operation(summary = "Update photo URL", description = "Update the photo URL of a profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photo URL updated"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    @PutMapping("/{profileId}/photo-url")
    public ResponseEntity<ProfileResource> updatePhotoUrl(
            @PathVariable Long profileId,
            @RequestBody UpdatePhotoUrlResource resource) {
        var existingProfile = profileQueryService.getByProfileId(new ProfileId(profileId));
        if (existingProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!authenticatedUserProvider.canAccessUser(existingProfile.get().getUserId().value())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var command = UpdatePhotoUrlCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
        var profile = profileCommandService.handle(command);
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }
}
