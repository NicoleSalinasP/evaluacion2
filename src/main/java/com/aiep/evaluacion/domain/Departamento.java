package com.aiep.evaluacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Departamento.
 */
@Entity
@Table(name = "departamento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_departamento")
    private String nombreDepartamento;

    @Column(name = "ubicacion_departamento")
    private String ubicacionDepartamento;

    @Column(name = "presupuesto_departamento", precision = 21, scale = 2)
    private BigDecimal presupuestoDepartamento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
    @JsonIgnoreProperties(value = { "departamento" }, allowSetters = true)
    private Set<Empleado> empleados = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "departamentos" }, allowSetters = true)
    private Jefe jefe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Departamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDepartamento() {
        return this.nombreDepartamento;
    }

    public Departamento nombreDepartamento(String nombreDepartamento) {
        this.setNombreDepartamento(nombreDepartamento);
        return this;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getUbicacionDepartamento() {
        return this.ubicacionDepartamento;
    }

    public Departamento ubicacionDepartamento(String ubicacionDepartamento) {
        this.setUbicacionDepartamento(ubicacionDepartamento);
        return this;
    }

    public void setUbicacionDepartamento(String ubicacionDepartamento) {
        this.ubicacionDepartamento = ubicacionDepartamento;
    }

    public BigDecimal getPresupuestoDepartamento() {
        return this.presupuestoDepartamento;
    }

    public Departamento presupuestoDepartamento(BigDecimal presupuestoDepartamento) {
        this.setPresupuestoDepartamento(presupuestoDepartamento);
        return this;
    }

    public void setPresupuestoDepartamento(BigDecimal presupuestoDepartamento) {
        this.presupuestoDepartamento = presupuestoDepartamento;
    }

    public Set<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        if (this.empleados != null) {
            this.empleados.forEach(i -> i.setDepartamento(null));
        }
        if (empleados != null) {
            empleados.forEach(i -> i.setDepartamento(this));
        }
        this.empleados = empleados;
    }

    public Departamento empleados(Set<Empleado> empleados) {
        this.setEmpleados(empleados);
        return this;
    }

    public Departamento addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
        empleado.setDepartamento(this);
        return this;
    }

    public Departamento removeEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
        empleado.setDepartamento(null);
        return this;
    }

    public Jefe getJefe() {
        return this.jefe;
    }

    public void setJefe(Jefe jefe) {
        this.jefe = jefe;
    }

    public Departamento jefe(Jefe jefe) {
        this.setJefe(jefe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departamento)) {
            return false;
        }
        return getId() != null && getId().equals(((Departamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departamento{" +
            "id=" + getId() +
            ", nombreDepartamento='" + getNombreDepartamento() + "'" +
            ", ubicacionDepartamento='" + getUbicacionDepartamento() + "'" +
            ", presupuestoDepartamento=" + getPresupuestoDepartamento() +
            "}";
    }
}
