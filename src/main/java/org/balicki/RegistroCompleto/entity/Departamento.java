package org.balicki.RegistroCompleto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Anotacion Entity para confirmar que es una entidad
 * Anotacion Table para que nos cree una tabla con el
 * nombre seleccionado en este caso departamento y en
 * la bbdd registro_completo
 * Tambien va a tener una constraint de unicidad en
 * el atributo numero y nombre
 */
@Entity
@Table(name = "departamento", schema = "registro_completo",
        uniqueConstraints = {
            @UniqueConstraint(name = "uq_numero", columnNames = {"numero"}),
            @UniqueConstraint(name = "uq_nombre", columnNames = {"nombre"})
        })
public class Departamento {
    /**
     * Atributo id generado automaticamente y autoincremental
     * Asignamos el nombre de la columna
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    /**
     * Atributo numero con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validDepartamento.numero.notNull}")
    @NotBlank(message = "{validDepartamento.numero.notBlank}")
    @Column(name = "numero")
    private String numero;
    /**
     * Atributo nombre con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validDepartamento.nombre.notNull}")
    @NotBlank(message = "{validDepartamento.nombre.notBlank}")
    @Column(name = "nombre")
    private String nombre;
    /**
     * Atributo localidad con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validDepartamento.localidad.notNull}")
    @NotBlank(message = "{validDepartamento.localidad.notBlank}")
    @Column(name = "localidad")
    private String localidad;

    /**
     * Constructor vacio
     */
    public Departamento() {
    }

    /**
     * Contructor con todos los atributos
     * @param id
     * @param numeroDepartamento
     * @param nombre
     * @param localidad
     */
    public Departamento(long id,
                        String numeroDepartamento,
                        String nombre,
                        String localidad) {
        this.id = id;
        this.numero = numeroDepartamento;
        this.nombre = nombre;
        this.localidad = localidad;
    }

    /**
     * Contructor con los atributos:
     * @param numero
     * @param nombre
     * @param localidad
     */
    public Departamento(String numero,
                        String nombre,
                        String localidad) {
        this.numero = numero;
        this.nombre = nombre;
        this.localidad = localidad;
    }

    /**
     * Getter del id
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Setter del id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter del numero
     * @return
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Setter del numero
     * @param numero
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Getter del nombre
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la localidad
     * @return
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Setter de la localidad
     * @param localidad
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
}
