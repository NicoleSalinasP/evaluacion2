package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Jefe;
import com.aiep.evaluacion.repository.JefeRepository;
import com.aiep.evaluacion.service.JefeService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Jefe}.
 */
@Service
@Transactional
public class JefeServiceImpl implements JefeService {

    private static final Logger LOG = LoggerFactory.getLogger(JefeServiceImpl.class);

    private final JefeRepository jefeRepository;

    public JefeServiceImpl(JefeRepository jefeRepository) {
        this.jefeRepository = jefeRepository;
    }

    @Override
    public Jefe save(Jefe jefe) {
        LOG.debug("Request to save Jefe : {}", jefe);
        return jefeRepository.save(jefe);
    }

    @Override
    public Jefe update(Jefe jefe) {
        LOG.debug("Request to update Jefe : {}", jefe);
        return jefeRepository.save(jefe);
    }

    @Override
    public Optional<Jefe> partialUpdate(Jefe jefe) {
        LOG.debug("Request to partially update Jefe : {}", jefe);

        return jefeRepository
            .findById(jefe.getId())
            .map(existingJefe -> {
                if (jefe.getNombreJefe() != null) {
                    existingJefe.setNombreJefe(jefe.getNombreJefe());
                }
                if (jefe.getTelefono() != null) {
                    existingJefe.setTelefono(jefe.getTelefono());
                }

                return existingJefe;
            })
            .map(jefeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Jefe> findAll() {
        LOG.debug("Request to get all Jefes");
        return jefeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Jefe> findOne(Long id) {
        LOG.debug("Request to get Jefe : {}", id);
        return jefeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Jefe : {}", id);
        jefeRepository.deleteById(id);
    }
}
