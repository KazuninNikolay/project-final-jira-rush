package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.BaseHandler;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;

import static com.javarush.jira.login.internal.web.UserTestData.USER_ID;
import static com.javarush.jira.login.internal.web.UserTestData.USER_MAIL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_PROFILE = BaseHandler.REST_URL + "/profile";

    @Autowired
    private ProfileRestController profileRestController;

    @Autowired
    ProfileMapper mapper;

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void shouldGetReturnOk() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetReturn400() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_PROFILE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void shouldReturnIsNoContentUpdateProfile() throws Exception {
        ProfileTo newProfile = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newProfile)))
                .andExpect(status().isNoContent());

        ProfileTo updated = profileRestController.get(USER_ID);
        assertEquals(updated, newProfile);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    public void shouldNegativeReturnUpdateProfile() throws Exception {
        ProfileTo beforeUpdate = profileRestController.get(USER_ID);
        perform(MockMvcRequestBuilders.put(REST_URL_PROFILE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getWithContactHtmlUnsafeTo())))
                .andExpect(status().isUnprocessableEntity());
        ProfileTo afterUpdate = profileRestController.get(USER_ID);
        assertEquals(beforeUpdate, afterUpdate);
    }
}