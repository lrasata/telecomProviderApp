package com.lrasata.telecomproviderapp.service;

import com.lrasata.telecomproviderapp.domain.MobilePlan;
import com.lrasata.telecomproviderapp.repository.MobilePlanRepository;
import com.lrasata.telecomproviderapp.service.dto.MobilePlanDTO;
import com.lrasata.telecomproviderapp.service.mapper.MobilePlanMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.lrasata.telecomproviderapp.domain.MobilePlan}.
 */
@Service
@Transactional
public class MobilePlanService {

    private static final Logger LOG = LoggerFactory.getLogger(MobilePlanService.class);

    private final MobilePlanRepository mobilePlanRepository;

    private final MobilePlanMapper mobilePlanMapper;

    public MobilePlanService(MobilePlanRepository mobilePlanRepository, MobilePlanMapper mobilePlanMapper) {
        this.mobilePlanRepository = mobilePlanRepository;
        this.mobilePlanMapper = mobilePlanMapper;
    }

    /**
     * Save a mobilePlan.
     *
     * @param mobilePlanDTO the entity to save.
     * @return the persisted entity.
     */
    public MobilePlanDTO save(MobilePlanDTO mobilePlanDTO) {
        LOG.debug("Request to save MobilePlan : {}", mobilePlanDTO);
        MobilePlan mobilePlan = mobilePlanMapper.toEntity(mobilePlanDTO);
        mobilePlan = mobilePlanRepository.save(mobilePlan);
        return mobilePlanMapper.toDto(mobilePlan);
    }

    /**
     * Update a mobilePlan.
     *
     * @param mobilePlanDTO the entity to save.
     * @return the persisted entity.
     */
    public MobilePlanDTO update(MobilePlanDTO mobilePlanDTO) {
        LOG.debug("Request to update MobilePlan : {}", mobilePlanDTO);
        MobilePlan mobilePlan = mobilePlanMapper.toEntity(mobilePlanDTO);
        mobilePlan = mobilePlanRepository.save(mobilePlan);
        return mobilePlanMapper.toDto(mobilePlan);
    }

    /**
     * Partially update a mobilePlan.
     *
     * @param mobilePlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MobilePlanDTO> partialUpdate(MobilePlanDTO mobilePlanDTO) {
        LOG.debug("Request to partially update MobilePlan : {}", mobilePlanDTO);

        return mobilePlanRepository
            .findById(mobilePlanDTO.getId())
            .map(existingMobilePlan -> {
                mobilePlanMapper.partialUpdate(existingMobilePlan, mobilePlanDTO);

                return existingMobilePlan;
            })
            .map(mobilePlanRepository::save)
            .map(mobilePlanMapper::toDto);
    }

    /**
     * Get all the mobilePlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MobilePlanDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all MobilePlans");
        return mobilePlanRepository.findAll(pageable).map(mobilePlanMapper::toDto);
    }

    /**
     * Get one mobilePlan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MobilePlanDTO> findOne(Long id) {
        LOG.debug("Request to get MobilePlan : {}", id);
        return mobilePlanRepository.findById(id).map(mobilePlanMapper::toDto);
    }

    /**
     * Delete the mobilePlan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete MobilePlan : {}", id);
        mobilePlanRepository.deleteById(id);
    }
}
