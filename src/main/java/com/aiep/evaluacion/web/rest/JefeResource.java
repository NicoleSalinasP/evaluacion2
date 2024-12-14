package com.aiep.evaluacion.web.rest;

import com.aiep.evaluacion.domain.Jefe;
import com.aiep.evaluacion.repository.JefeRepository;
import com.aiep.evaluacion.service.JefeService;
import com.aiep.evaluacion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiep.evaluacion.domain.Jefe}.
 */
@RestController
@RequestMapping("/api/jefes")
public class JefeResource {

    private static final Logger LOG = LoggerFactory.getLogger(JefeResource.class);

    private static final String ENTITY_NAME = "jefe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JefeService jefeService;

    private final JefeRepository jefeRepository;

    public JefeResource(JefeService jefeService, JefeRepository jefeRepository) {
        this.jefeService = jefeService;
        this.jefeRepository = jefeRepository;
    }

    /**
     * {@code POST  /jefes} : Create a new jefe.
     *
     * @param jefe the jefe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jefe, or with status {@code 400 (Bad Request)} if the jefe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Jefe> createJefe(@RequestBody Jefe jefe) throws URISyntaxException {
        LOG.debug("REST request to save Jefe : {}", jefe);
        if (jefe.getId() != null) {
            throw new BadRequestAlertException("A new jefe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jefe = jefeService.save(jefe);
        return ResponseEntity.created(new URI("/api/jefes/" + jefe.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jefe.getId().toString()))
            .body(jefe);
    }

    /**
     * {@code PUT  /jefes/:id} : Updates an existing jefe.
     *
     * @param id the id of the jefe to save.
     * @param jefe the jefe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jefe,
     * or with status {@code 400 (Bad Request)} if the jefe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jefe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Jefe> updateJefe(@PathVariable(value = "id", required = false) final Long id, @RequestBody Jefe jefe)
        throws URISyntaxException {
        LOG.debug("REST request to update Jefe : {}, {}", id, jefe);
        if (jefe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jefe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jefeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jefe = jefeService.update(jefe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jefe.getId().toString()))
            .body(jefe);
    }

    /**
     * {@code PATCH  /jefes/:id} : Partial updates given fields of an existing jefe, field will ignore if it is null
     *
     * @param id the id of the jefe to save.
     * @param jefe the jefe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jefe,
     * or with status {@code 400 (Bad Request)} if the jefe is not valid,
     * or with status {@code 404 (Not Found)} if the jefe is not found,
     * or with status {@code 500 (Internal Server Error)} if the jefe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Jefe> partialUpdateJefe(@PathVariable(value = "id", required = false) final Long id, @RequestBody Jefe jefe)
        throws URISyntaxException {
        LOG.debug("REST request to partial update Jefe partially : {}, {}", id, jefe);
        if (jefe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jefe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jefeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Jefe> result = jefeService.partialUpdate(jefe);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jefe.getId().toString())
        );
    }

    /**
     * {@code GET  /jefes} : get all the jefes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jefes in body.
     */
    @GetMapping("")
    public List<Jefe> getAllJefes() {
        LOG.debug("REST request to get all Jefes");
        return jefeService.findAll();
    }

    /**
     * {@code GET  /jefes/:id} : get the "id" jefe.
     *
     * @param id the id of the jefe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jefe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Jefe> getJefe(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Jefe : {}", id);
        Optional<Jefe> jefe = jefeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jefe);
    }

    /**
     * {@code DELETE  /jefes/:id} : delete the "id" jefe.
     *
     * @param id the id of the jefe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJefe(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Jefe : {}", id);
        jefeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
