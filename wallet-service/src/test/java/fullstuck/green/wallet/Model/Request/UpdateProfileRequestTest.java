package fullstuck.green.wallet.Model.Request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProfileRequestTest {

    @Test
    void testUpadeProfileRequest() {
        UpdateProfileRequest updateProfileRequest = UpdateProfileRequest.builder()
                .birthDate("12-11-2000")
                .name("acla")
                .profileImageUrl("url")
                .phoneNumber("9172498213")
                .id("id")
                .build();
        UpdateProfileRequest updateProfileRequestGetSet = new UpdateProfileRequest();
        updateProfileRequestGetSet.setId(updateProfileRequest.getId());
        updateProfileRequestGetSet.setName(updateProfileRequest.getName());
        updateProfileRequestGetSet.setProfileImageUrl(updateProfileRequest.getProfileImageUrl());
        updateProfileRequestGetSet.setBirthDate(updateProfileRequest.getBirthDate());
        assertNotNull(updateProfileRequestGetSet);
    }
}