package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Empleado;
import com.aiep.evaluacion.repository.EmpleadoRepository;
import com.aiep.evaluacion.service.EmpleadoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Empleado}.
 */
@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpleadoServiceImpl.class);

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado save(Empleado empleado) {
        LOG.debug("Request to save Empleado : {}", empleado);
        return empleadoRepository.save(empleado);
    }

    @Override
    public Empleado update(Empleado empleado) {
        LOG.debug("Request to update Empleado : {}", empleado);
        return empleadoRepository.save(empleado);
    }

    @Override
    public Optional<Empleado> partialUpdate(Empleado empleado) {
        LOG.debug("Request to partially update Empleado : {}", empleado);

        return empleadoRepository
            .findById(empleado.getId())
            .map(existingEmpleado -> {
                if (empleado.getNombreEmpleado() != null) {
                    existingEmpleado.setNombreEmpleado(empleado.getNombreEmpleado());
                }
                if (empleado.getApellidoEmpleado() != null) {
                    existingEmpleado.setApellidoEmpleado(empleado.getApellidoEmpleado());
                }
                if (empleado.getTelefono() != null) {
                    existingEmpleado.setTelefono(empleado.getTelefono());
                }
                if (empleado.getCorreo() != null) {
                    existingEmpleado.setCorreo(empleado.getCorreo());
                }

                return existingEmpleado;
            })
            .map(empleadoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        LOG.debug("Request to get all Empleados");
        return empleadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> findOne(Long id) {
        LOG.debug("Request to get Empleado : {}", id);
        return empleadoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Empleado : {}", id);
        empleadoRepository.deleteById(id);
    }
}
