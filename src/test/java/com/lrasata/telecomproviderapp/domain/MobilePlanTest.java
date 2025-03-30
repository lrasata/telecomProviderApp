package com.lrasata.telecomproviderapp.domain;

import static com.lrasata.telecomproviderapp.domain.MobilePlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.lrasata.telecomproviderapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MobilePlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobilePlan.class);
        MobilePlan mobilePlan1 = getMobilePlanSample1();
        MobilePlan mobilePlan2 = new MobilePlan();
        assertThat(mobilePlan1).isNotEqualTo(mobilePlan2);

        mobilePlan2.setId(mobilePlan1.getId());
        assertThat(mobilePlan1).isEqualTo(mobilePlan2);

        mobilePlan2 = getMobilePlanSample2();
        assertThat(mobilePlan1).isNotEqualTo(mobilePlan2);
    }
}
