package com.lrasata.telecomproviderapp.service.mapper;

import static com.lrasata.telecomproviderapp.domain.MobilePlanAsserts.*;
import static com.lrasata.telecomproviderapp.domain.MobilePlanTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MobilePlanMapperTest {

    private MobilePlanMapper mobilePlanMapper;

    @BeforeEach
    void setUp() {
        mobilePlanMapper = new MobilePlanMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMobilePlanSample1();
        var actual = mobilePlanMapper.toEntity(mobilePlanMapper.toDto(expected));
        assertMobilePlanAllPropertiesEquals(expected, actual);
    }
}
