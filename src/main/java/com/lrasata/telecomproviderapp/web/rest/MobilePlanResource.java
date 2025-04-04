package com.lrasata.telecomproviderapp.web.rest;

import com.lrasata.telecomproviderapp.repository.MobilePlanRepository;
import com.lrasata.telecomproviderapp.security.AuthoritiesConstants;
import com.lrasata.telecomproviderapp.service.MobilePlanService;
import com.lrasata.telecomproviderapp.service.dto.MobilePlanDTO;
import com.lrasata.telecomproviderapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.lrasata.telecomproviderapp.domain.MobilePlan}.
 */
@RestController
@RequestMapping("/api/mobile-plans")
public class MobilePlanResource {

    private static final Logger LOG = LoggerFactory.getLogger(MobilePlanResource.class);

    private static final String ENTITY_NAME = "mobilePlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MobilePlanService mobilePlanService;

    private final MobilePlanRepository mobilePlanRepository;

    public MobilePlanResource(MobilePlanService mobilePlanService, MobilePlanRepository mobilePlanRepository) {
        this.mobilePlanService = mobilePlanService;
        this.mobilePlanRepository = mobilePlanRepository;
    }

    /**
     * {@code POST  /mobile-plans} : Create a new mobilePlan.
     *
     * @param mobilePlanDTO the mobilePlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobilePlanDTO, or with status {@code 400 (Bad Request)} if the mobilePlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MobilePlanDTO> createMobilePlan(@Valid @RequestBody MobilePlanDTO mobilePlanDTO) throws URISyntaxException {
        LOG.debug("REST request to save MobilePlan : {}", mobilePlanDTO);
        if (mobilePlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new mobilePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mobilePlanDTO = mobilePlanService.save(mobilePlanDTO);
        return ResponseEntity.created(new URI("/api/mobile-plans/" + mobilePlanDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, mobilePlanDTO.getId().toString()))
            .body(mobilePlanDTO);
    }

    /**
     * {@code PUT  /mobile-plans/:id} : Updates an existing mobilePlan.
     *
     * @param id the id of the mobilePlanDTO to save.
     * @param mobilePlanDTO the mobilePlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobilePlanDTO,
     * or with status {@code 400 (Bad Request)} if the mobilePlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobilePlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MobilePlanDTO> updateMobilePlan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MobilePlanDTO mobilePlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MobilePlan : {}, {}", id, mobilePlanDTO);
        if (mobilePlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mobilePlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mobilePlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mobilePlanDTO = mobilePlanService.update(mobilePlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mobilePlanDTO.getId().toString()))
            .body(mobilePlanDTO);
    }

    /**
     * {@code PATCH  /mobile-plans/:id} : Partial updates given fields of an existing mobilePlan, field will ignore if it is null
     *
     * @param id the id of the mobilePlanDTO to save.
     * @param mobilePlanDTO the mobilePlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobilePlanDTO,
     * or with status {@code 400 (Bad Request)} if the mobilePlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mobilePlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mobilePlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MobilePlanDTO> partialUpdateMobilePlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MobilePlanDTO mobilePlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MobilePlan partially : {}, {}", id, mobilePlanDTO);
        if (mobilePlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mobilePlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mobilePlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MobilePlanDTO> result = mobilePlanService.partialUpdate(mobilePlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mobilePlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mobile-plans} : get all the mobilePlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobilePlans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MobilePlanDTO>> getAllMobilePlans(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MobilePlans");
        Page<MobilePlanDTO> page = mobilePlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mobile-plans/:id} : get the "id" mobilePlan.
     *
     * @param id the id of the mobilePlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobilePlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MobilePlanDTO> getMobilePlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MobilePlan : {}", id);
        Optional<MobilePlanDTO> mobilePlanDTO = mobilePlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mobilePlanDTO);
    }

    /**
     * {@code DELETE  /mobile-plans/:id} : delete the "id" mobilePlan.
     *
     * @param id the id of the mobilePlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteMobilePlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MobilePlan : {}", id);
        mobilePlanService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
