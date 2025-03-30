package com.lrasata.telecomproviderapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.lrasata.telecomproviderapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MobilePlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobilePlanDTO.class);
        MobilePlanDTO mobilePlanDTO1 = new MobilePlanDTO();
        mobilePlanDTO1.setId(1L);
        MobilePlanDTO mobilePlanDTO2 = new MobilePlanDTO();
        assertThat(mobilePlanDTO1).isNotEqualTo(mobilePlanDTO2);
        mobilePlanDTO2.setId(mobilePlanDTO1.getId());
        assertThat(mobilePlanDTO1).isEqualTo(mobilePlanDTO2);
        mobilePlanDTO2.setId(2L);
        assertThat(mobilePlanDTO1).isNotEqualTo(mobilePlanDTO2);
        mobilePlanDTO1.setId(null);
        assertThat(mobilePlanDTO1).isNotEqualTo(mobilePlanDTO2);
    }
}
