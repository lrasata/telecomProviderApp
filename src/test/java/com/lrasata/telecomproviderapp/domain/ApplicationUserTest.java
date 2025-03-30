package com.lrasata.telecomproviderapp.domain;

import static com.lrasata.telecomproviderapp.domain.ApplicationUserTestSamples.*;
import static com.lrasata.telecomproviderapp.domain.MobilePlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lrasata.telecomproviderapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUser.class);
        ApplicationUser applicationUser1 = getApplicationUserSample1();
        ApplicationUser applicationUser2 = new ApplicationUser();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);

        applicationUser2.setId(applicationUser1.getId());
        assertThat(applicationUser1).isEqualTo(applicationUser2);

        applicationUser2 = getApplicationUserSample2();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);
    }

    @Test
    void chosenMobilePlanTest() {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        MobilePlan mobilePlanBack = getMobilePlanRandomSampleGenerator();

        applicationUser.setChosenMobilePlan(mobilePlanBack);
        assertThat(applicationUser.getChosenMobilePlan()).isEqualTo(mobilePlanBack);

        applicationUser.chosenMobilePlan(null);
        assertThat(applicationUser.getChosenMobilePlan()).isNull();
    }
}
