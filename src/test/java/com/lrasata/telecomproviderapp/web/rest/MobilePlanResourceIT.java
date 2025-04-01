package com.lrasata.telecomproviderapp.web.rest;

import static com.lrasata.telecomproviderapp.domain.MobilePlanAsserts.*;
import static com.lrasata.telecomproviderapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrasata.telecomproviderapp.IntegrationTest;
import com.lrasata.telecomproviderapp.domain.MobilePlan;
import com.lrasata.telecomproviderapp.repository.MobilePlanRepository;
import com.lrasata.telecomproviderapp.service.dto.MobilePlanDTO;
import com.lrasata.telecomproviderapp.service.mapper.MobilePlanMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MobilePlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MobilePlanResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_INTERNET_DATA_IN_GB = 1;
    private static final Integer UPDATED_INTERNET_DATA_IN_GB = 2;

    private static final Boolean DEFAULT_UNLIMITED_SMS_AND_CALLS = false;
    private static final Boolean UPDATED_UNLIMITED_SMS_AND_CALLS = true;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mobile-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MobilePlanRepository mobilePlanRepository;

    @Autowired
    private MobilePlanMapper mobilePlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMobilePlanMockMvc;

    private MobilePlan mobilePlan;

    private MobilePlan insertedMobilePlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobilePlan createEntity() {
        return new MobilePlan()
            .name(DEFAULT_NAME)
            .internetDataInGB(DEFAULT_INTERNET_DATA_IN_GB)
            .unlimitedSmsAndCalls(DEFAULT_UNLIMITED_SMS_AND_CALLS)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobilePlan createUpdatedEntity() {
        return new MobilePlan()
            .name(UPDATED_NAME)
            .internetDataInGB(UPDATED_INTERNET_DATA_IN_GB)
            .unlimitedSmsAndCalls(UPDATED_UNLIMITED_SMS_AND_CALLS)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION);
    }

    @BeforeEach
    public void initTest() {
        mobilePlan = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedMobilePlan != null) {
            mobilePlanRepository.delete(insertedMobilePlan);
            insertedMobilePlan = null;
        }
    }

    @Test
    @Transactional
    void createMobilePlan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MobilePlan
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);
        var returnedMobilePlanDTO = om.readValue(
            restMobilePlanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mobilePlanDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MobilePlanDTO.class
        );

        // Validate the MobilePlan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMobilePlan = mobilePlanMapper.toEntity(returnedMobilePlanDTO);
        assertMobilePlanUpdatableFieldsEquals(returnedMobilePlan, getPersistedMobilePlan(returnedMobilePlan));

        insertedMobilePlan = returnedMobilePlan;
    }

    @Test
    @Transactional
    void createMobilePlanWithExistingId() throws Exception {
        // Create the MobilePlan with an existing ID
        mobilePlan.setId(1L);
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMobilePlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mobilePlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mobilePlan.setName(null);

        // Create the MobilePlan, which fails.
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        restMobilePlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mobilePlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInternetDataInGBIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mobilePlan.setInternetDataInGB(null);

        // Create the MobilePlan, which fails.
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        restMobilePlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mobilePlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnlimitedSmsAndCallsIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mobilePlan.setUnlimitedSmsAndCalls(null);

        // Create the MobilePlan, which fails.
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        restMobilePlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mobilePlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mobilePlan.setPrice(null);

        // Create the MobilePlan, which fails.
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        restMobilePlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mobilePlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMobilePlans() throws Exception {
        // Initialize the database
        insertedMobilePlan = mobilePlanRepository.saveAndFlush(mobilePlan);

        // Get all the mobilePlanList
        restMobilePlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobilePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].internetDataInGB").value(hasItem(DEFAULT_INTERNET_DATA_IN_GB)))
            .andExpect(jsonPath("$.[*].unlimitedSmsAndCalls").value(hasItem(DEFAULT_UNLIMITED_SMS_AND_CALLS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getMobilePlan() throws Exception {
        // Initialize the database
        insertedMobilePlan = mobilePlanRepository.saveAndFlush(mobilePlan);

        // Get the mobilePlan
        restMobilePlanMockMvc
            .perform(get(ENTITY_API_URL_ID, mobilePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mobilePlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.internetDataInGB").value(DEFAULT_INTERNET_DATA_IN_GB))
            .andExpect(jsonPath("$.unlimitedSmsAndCalls").value(DEFAULT_UNLIMITED_SMS_AND_CALLS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingMobilePlan() throws Exception {
        // Get the mobilePlan
        restMobilePlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMobilePlan() throws Exception {
        // Initialize the database
        insertedMobilePlan = mobilePlanRepository.saveAndFlush(mobilePlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mobilePlan
        MobilePlan updatedMobilePlan = mobilePlanRepository.findById(mobilePlan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMobilePlan are not directly saved in db
        em.detach(updatedMobilePlan);
        updatedMobilePlan
            .name(UPDATED_NAME)
            .internetDataInGB(UPDATED_INTERNET_DATA_IN_GB)
            .unlimitedSmsAndCalls(UPDATED_UNLIMITED_SMS_AND_CALLS)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION);
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(updatedMobilePlan);

        restMobilePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mobilePlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mobilePlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMobilePlanToMatchAllProperties(updatedMobilePlan);
    }

    @Test
    @Transactional
    void putNonExistingMobilePlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mobilePlan.setId(longCount.incrementAndGet());

        // Create the MobilePlan
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobilePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mobilePlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mobilePlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMobilePlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mobilePlan.setId(longCount.incrementAndGet());

        // Create the MobilePlan
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilePlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mobilePlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMobilePlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mobilePlan.setId(longCount.incrementAndGet());

        // Create the MobilePlan
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilePlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mobilePlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMobilePlanWithPatch() throws Exception {
        // Initialize the database
        insertedMobilePlan = mobilePlanRepository.saveAndFlush(mobilePlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mobilePlan using partial update
        MobilePlan partialUpdatedMobilePlan = new MobilePlan();
        partialUpdatedMobilePlan.setId(mobilePlan.getId());

        partialUpdatedMobilePlan
            .name(UPDATED_NAME)
            .internetDataInGB(UPDATED_INTERNET_DATA_IN_GB)
            .unlimitedSmsAndCalls(UPDATED_UNLIMITED_SMS_AND_CALLS)
            .price(UPDATED_PRICE);

        restMobilePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMobilePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMobilePlan))
            )
            .andExpect(status().isOk());

        // Validate the MobilePlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMobilePlanUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMobilePlan, mobilePlan),
            getPersistedMobilePlan(mobilePlan)
        );
    }

    @Test
    @Transactional
    void fullUpdateMobilePlanWithPatch() throws Exception {
        // Initialize the database
        insertedMobilePlan = mobilePlanRepository.saveAndFlush(mobilePlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mobilePlan using partial update
        MobilePlan partialUpdatedMobilePlan = new MobilePlan();
        partialUpdatedMobilePlan.setId(mobilePlan.getId());

        partialUpdatedMobilePlan
            .name(UPDATED_NAME)
            .internetDataInGB(UPDATED_INTERNET_DATA_IN_GB)
            .unlimitedSmsAndCalls(UPDATED_UNLIMITED_SMS_AND_CALLS)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION);

        restMobilePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMobilePlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMobilePlan))
            )
            .andExpect(status().isOk());

        // Validate the MobilePlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMobilePlanUpdatableFieldsEquals(partialUpdatedMobilePlan, getPersistedMobilePlan(partialUpdatedMobilePlan));
    }

    @Test
    @Transactional
    void patchNonExistingMobilePlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mobilePlan.setId(longCount.incrementAndGet());

        // Create the MobilePlan
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobilePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mobilePlanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mobilePlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMobilePlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mobilePlan.setId(longCount.incrementAndGet());

        // Create the MobilePlan
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilePlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mobilePlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMobilePlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mobilePlan.setId(longCount.incrementAndGet());

        // Create the MobilePlan
        MobilePlanDTO mobilePlanDTO = mobilePlanMapper.toDto(mobilePlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMobilePlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mobilePlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MobilePlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMobilePlan() throws Exception {
        // Initialize the database
        insertedMobilePlan = mobilePlanRepository.saveAndFlush(mobilePlan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mobilePlan
        restMobilePlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, mobilePlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mobilePlanRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected MobilePlan getPersistedMobilePlan(MobilePlan mobilePlan) {
        return mobilePlanRepository.findById(mobilePlan.getId()).orElseThrow();
    }

    protected void assertPersistedMobilePlanToMatchAllProperties(MobilePlan expectedMobilePlan) {
        assertMobilePlanAllPropertiesEquals(expectedMobilePlan, getPersistedMobilePlan(expectedMobilePlan));
    }

    protected void assertPersistedMobilePlanToMatchUpdatableProperties(MobilePlan expectedMobilePlan) {
        assertMobilePlanAllUpdatablePropertiesEquals(expectedMobilePlan, getPersistedMobilePlan(expectedMobilePlan));
    }
}
