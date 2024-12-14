package com.aiep.evaluacion.service;

import com.aiep.evaluacion.domain.Jefe;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.Jefe}.
 */
public interface JefeService {
    /**
     * Save a jefe.
     *
     * @param jefe the entity to save.
     * @return the persisted entity.
     */
    Jefe save(Jefe jefe);

    /**
     * Updates a jefe.
     *
     * @param jefe the entity to update.
     * @return the persisted entity.
     */
    Jefe update(Jefe jefe);

    /**
     * Partially updates a jefe.
     *
     * @param jefe the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Jefe> partialUpdate(Jefe jefe);

    /**
     * Get all the jefes.
     *
     * @return the list of entities.
     */
    List<Jefe> findAll();

    /**
     * Get the "id" jefe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Jefe> findOne(Long id);

    /**
     * Delete the "id" jefe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
