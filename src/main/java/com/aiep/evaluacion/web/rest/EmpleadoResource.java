package com.aiep.evaluacion.web.rest;

import com.aiep.evaluacion.domain.Empleado;
import com.aiep.evaluacion.repository.EmpleadoRepository;
import com.aiep.evaluacion.service.EmpleadoService;
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
 * REST controller for managing {@link com.aiep.evaluacion.domain.Empleado}.
 */
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmpleadoResource.class);

    private static final String ENTITY_NAME = "empleado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpleadoService empleadoService;

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoResource(EmpleadoService empleadoService, EmpleadoRepository empleadoRepository) {
        this.empleadoService = empleadoService;
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * {@code POST  /empleados} : Create a new empleado.
     *
     * @param empleado the empleado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empleado, or with status {@code 400 (Bad Request)} if the empleado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado empleado) throws URISyntaxException {
        LOG.debug("REST request to save Empleado : {}", empleado);
        if (empleado.getId() != null) {
            throw new BadRequestAlertException("A new empleado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        empleado = empleadoService.save(empleado);
        return ResponseEntity.created(new URI("/api/empleados/" + empleado.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, empleado.getId().toString()))
            .body(empleado);
    }

    /**
     * {@code PUT  /empleados/:id} : Updates an existing empleado.
     *
     * @param id the id of the empleado to save.
     * @param empleado the empleado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empleado,
     * or with status {@code 400 (Bad Request)} if the empleado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empleado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Empleado empleado
    ) throws URISyntaxException {
        LOG.debug("REST request to update Empleado : {}, {}", id, empleado);
        if (empleado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empleado = empleadoService.update(empleado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleado.getId().toString()))
            .body(empleado);
    }

    /**
     * {@code PATCH  /empleados/:id} : Partial updates given fields of an existing empleado, field will ignore if it is null
     *
     * @param id the id of the empleado to save.
     * @param empleado the empleado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empleado,
     * or with status {@code 400 (Bad Request)} if the empleado is not valid,
     * or with status {@code 404 (Not Found)} if the empleado is not found,
     * or with status {@code 500 (Internal Server Error)} if the empleado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Empleado> partialUpdateEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Empleado empleado
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Empleado partially : {}, {}", id, empleado);
        if (empleado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Empleado> result = empleadoService.partialUpdate(empleado);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleado.getId().toString())
        );
    }

    /**
     * {@code GET  /empleados} : get all the empleados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empleados in body.
     */
    @GetMapping("")
    public List<Empleado> getAllEmpleados() {
        LOG.debug("REST request to get all Empleados");
        return empleadoService.findAll();
    }

    /**
     * {@code GET  /empleados/:id} : get the "id" empleado.
     *
     * @param id the id of the empleado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empleado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleado(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Empleado : {}", id);
        Optional<Empleado> empleado = empleadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empleado);
    }

    /**
     * {@code DELETE  /empleados/:id} : delete the "id" empleado.
     *
     * @param id the id of the empleado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Empleado : {}", id);
        empleadoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
