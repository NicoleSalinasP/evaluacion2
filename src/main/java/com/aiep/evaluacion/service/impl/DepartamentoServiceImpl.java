package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Departamento;
import com.aiep.evaluacion.repository.DepartamentoRepository;
import com.aiep.evaluacion.service.DepartamentoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Departamento}.
 */
@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

    private static final Logger LOG = LoggerFactory.getLogger(DepartamentoServiceImpl.class);

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public Departamento save(Departamento departamento) {
        LOG.debug("Request to save Departamento : {}", departamento);
        return departamentoRepository.save(departamento);
    }

    @Override
    public Departamento update(Departamento departamento) {
        LOG.debug("Request to update Departamento : {}", departamento);
        return departamentoRepository.save(departamento);
    }

    @Override
    public Optional<Departamento> partialUpdate(Departamento departamento) {
        LOG.debug("Request to partially update Departamento : {}", departamento);

        return departamentoRepository
            .findById(departamento.getId())
            .map(existingDepartamento -> {
                if (departamento.getNombreDepartamento() != null) {
                    existingDepartamento.setNombreDepartamento(departamento.getNombreDepartamento());
                }
                if (departamento.getUbicacionDepartamento() != null) {
                    existingDepartamento.setUbicacionDepartamento(departamento.getUbicacionDepartamento());
                }
                if (departamento.getPresupuestoDepartamento() != null) {
                    existingDepartamento.setPresupuestoDepartamento(departamento.getPresupuestoDepartamento());
                }

                return existingDepartamento;
            })
            .map(departamentoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> findAll() {
        LOG.debug("Request to get all Departamentos");
        return departamentoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Departamento> findOne(Long id) {
        LOG.debug("Request to get Departamento : {}", id);
        return departamentoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Departamento : {}", id);
        departamentoRepository.deleteById(id);
    }
}
