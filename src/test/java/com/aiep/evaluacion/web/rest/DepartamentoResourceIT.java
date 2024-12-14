package com.aiep.evaluacion.web.rest;

import static com.aiep.evaluacion.domain.DepartamentoAsserts.*;
import static com.aiep.evaluacion.web.rest.TestUtil.createUpdateProxyForBean;
import static com.aiep.evaluacion.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.evaluacion.IntegrationTest;
import com.aiep.evaluacion.domain.Departamento;
import com.aiep.evaluacion.repository.DepartamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DepartamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepartamentoResourceIT {

    private static final String DEFAULT_NOMBRE_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION_DEPARTAMENTO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRESUPUESTO_DEPARTAMENTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRESUPUESTO_DEPARTAMENTO = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/departamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentoMockMvc;

    private Departamento departamento;

    private Departamento insertedDepartamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamento createEntity() {
        return new Departamento()
            .nombreDepartamento(DEFAULT_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(DEFAULT_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(DEFAULT_PRESUPUESTO_DEPARTAMENTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamento createUpdatedEntity() {
        return new Departamento()
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(UPDATED_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);
    }

    @BeforeEach
    public void initTest() {
        departamento = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDepartamento != null) {
            departamentoRepository.delete(insertedDepartamento);
            insertedDepartamento = null;
        }
    }

    @Test
    @Transactional
    void createDepartamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Departamento
        var returnedDepartamento = om.readValue(
            restDepartamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamento)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Departamento.class
        );

        // Validate the Departamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDepartamentoUpdatableFieldsEquals(returnedDepartamento, getPersistedDepartamento(returnedDepartamento));

        insertedDepartamento = returnedDepartamento;
    }

    @Test
    @Transactional
    void createDepartamentoWithExistingId() throws Exception {
        // Create the Departamento with an existing ID
        departamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamento)))
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartamentos() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreDepartamento").value(hasItem(DEFAULT_NOMBRE_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].ubicacionDepartamento").value(hasItem(DEFAULT_UBICACION_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].presupuestoDepartamento").value(hasItem(sameNumber(DEFAULT_PRESUPUESTO_DEPARTAMENTO))));
    }

    @Test
    @Transactional
    void getDepartamento() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        // Get the departamento
        restDepartamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, departamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamento.getId().intValue()))
            .andExpect(jsonPath("$.nombreDepartamento").value(DEFAULT_NOMBRE_DEPARTAMENTO))
            .andExpect(jsonPath("$.ubicacionDepartamento").value(DEFAULT_UBICACION_DEPARTAMENTO))
            .andExpect(jsonPath("$.presupuestoDepartamento").value(sameNumber(DEFAULT_PRESUPUESTO_DEPARTAMENTO)));
    }

    @Test
    @Transactional
    void getNonExistingDepartamento() throws Exception {
        // Get the departamento
        restDepartamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartamento() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamento
        Departamento updatedDepartamento = departamentoRepository.findById(departamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDepartamento are not directly saved in db
        em.detach(updatedDepartamento);
        updatedDepartamento
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(UPDATED_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);

        restDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepartamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartamentoToMatchAllProperties(updatedDepartamento);
    }

    @Test
    @Transactional
    void putNonExistingDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamento using partial update
        Departamento partialUpdatedDepartamento = new Departamento();
        partialUpdatedDepartamento.setId(departamento.getId());

        partialUpdatedDepartamento
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);

        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the Departamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartamento, departamento),
            getPersistedDepartamento(departamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamento using partial update
        Departamento partialUpdatedDepartamento = new Departamento();
        partialUpdatedDepartamento.setId(departamento.getId());

        partialUpdatedDepartamento
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(UPDATED_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);

        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the Departamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoUpdatableFieldsEquals(partialUpdatedDepartamento, getPersistedDepartamento(partialUpdatedDepartamento));
    }

    @Test
    @Transactional
    void patchNonExistingDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(departamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Departamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartamento() throws Exception {
        // Initialize the database
        insertedDepartamento = departamentoRepository.saveAndFlush(departamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the departamento
        restDepartamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, departamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departamentoRepository.count();
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

    protected Departamento getPersistedDepartamento(Departamento departamento) {
        return departamentoRepository.findById(departamento.getId()).orElseThrow();
    }

    protected void assertPersistedDepartamentoToMatchAllProperties(Departamento expectedDepartamento) {
        assertDepartamentoAllPropertiesEquals(expectedDepartamento, getPersistedDepartamento(expectedDepartamento));
    }

    protected void assertPersistedDepartamentoToMatchUpdatableProperties(Departamento expectedDepartamento) {
        assertDepartamentoAllUpdatablePropertiesEquals(expectedDepartamento, getPersistedDepartamento(expectedDepartamento));
    }
}
