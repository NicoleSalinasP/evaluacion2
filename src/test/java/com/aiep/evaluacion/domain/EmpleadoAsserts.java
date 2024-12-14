package com.aiep.evaluacion.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EmpleadoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpleadoAllPropertiesEquals(Empleado expected, Empleado actual) {
        assertEmpleadoAutoGeneratedPropertiesEquals(expected, actual);
        assertEmpleadoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpleadoAllUpdatablePropertiesEquals(Empleado expected, Empleado actual) {
        assertEmpleadoUpdatableFieldsEquals(expected, actual);
        assertEmpleadoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpleadoAutoGeneratedPropertiesEquals(Empleado expected, Empleado actual) {
        assertThat(expected)
            .as("Verify Empleado auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpleadoUpdatableFieldsEquals(Empleado expected, Empleado actual) {
        assertThat(expected)
            .as("Verify Empleado relevant properties")
            .satisfies(e -> assertThat(e.getNombreEmpleado()).as("check nombreEmpleado").isEqualTo(actual.getNombreEmpleado()))
            .satisfies(e -> assertThat(e.getApellidoEmpleado()).as("check apellidoEmpleado").isEqualTo(actual.getApellidoEmpleado()))
            .satisfies(e -> assertThat(e.getTelefono()).as("check telefono").isEqualTo(actual.getTelefono()))
            .satisfies(e -> assertThat(e.getCorreo()).as("check correo").isEqualTo(actual.getCorreo()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpleadoUpdatableRelationshipsEquals(Empleado expected, Empleado actual) {
        assertThat(expected)
            .as("Verify Empleado relationships")
            .satisfies(e -> assertThat(e.getDepartamento()).as("check departamento").isEqualTo(actual.getDepartamento()));
    }
}
