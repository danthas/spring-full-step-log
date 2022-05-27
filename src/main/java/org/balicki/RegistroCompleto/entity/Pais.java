package org.balicki.RegistroCompleto.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

/**
 * Anotacion Entity para confirmar que es una entidad
 * Anotacion Table para que nos cree una tabla con el
 * nombre seleccionado en este caso pais y en
 * la bbdd registro_completo
 * Tambien va a tener una constraint de unicidad en
 * el atributo nombre
 */
@Entity
@Table(name = "pais", schema = "registro_completo",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_nombre", columnNames = {"nombre"})
        })
public class Pais {
    /**
     * Atributo id generado automaticamente y autoincremental
     * Asignamos el nombre de la columna
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    /**
     * Atributo siglas con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validPais.siglas.notNull}")
    @NotBlank(message = "{validPais.siglas.notBlank}")
    @Column(name = "siglas")
    private String siglas;
    /**
     * Atributo nombre con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validPais.nombre.notNull}")
    @NotBlank(message = "{validPais.nombre.notBlank}")
    @Column(name = "nombre")
    private String nombre;
    /**
     * Atributo poblacion con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validPais.poblacion.notNull}")
    @Min(value = 800, message = "{validPais.poblacion.min}")
    @Column(name = "poblacion")
    private int poblacion;
    /**
     * Atributo superficie con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validPais.superficie.notNull}")
    @DecimalMin(value = "500.0", message = "{validUser.superficie.decimalMin}")
    @Column(name = "superficie")
    private double superficie;
    /**
     * Atributo frontera con sus validaciones
     * Relacion ManyToMany para que se relacione con el mismo
     */
//    @NotNull(message = "{validPais.frontera.notNull}")
//    @NotEmpty(message = "{validPais.frontera.notEmpty}")
    @ManyToMany(targetEntity = Pais.class)
    private List<Pais> frontera;

    /**
     * Constructor vacio
     */
    public Pais() {
    }

    /**
     * Contructor con todos los atributos
     *
     * @param id
     * @param siglas
     * @param nombre
     * @param poblacion
     * @param superficie
     */
    public Pais(long id,
                String siglas,
                String nombre,
                int poblacion,
                double superficie) {
        this.id = id;
        this.siglas = siglas;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.superficie = superficie;
    }

    /**
     * Contructor con los atributos:
     *
     * @param siglas
     * @param nombre
     * @param poblacion
     * @param superficie
     * @param frontera
     */
    public Pais(String siglas,
                String nombre,
                int poblacion,
                double superficie,
                List<Pais> frontera) {
        this.siglas = siglas;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.superficie = superficie;
        this.frontera = frontera;
    }

    /**
     * Contructor con los atributos:
     *
     * @param siglas
     * @param nombre
     * @param poblacion
     * @param superficie
     */
    public Pais(String siglas,
                String nombre,
                int poblacion,
                double superficie) {
        this.siglas = siglas;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.superficie = superficie;
    }

    /**
     * Getter del id
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Setter del id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter de siglas
     *
     * @return
     */
    public String getSiglas() {
        return siglas;
    }

    /**
     * Setter de siglas
     *
     * @param siglas
     */
    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    /**
     * Getter del nombre
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de la poblacion
     *
     * @return
     */
    public int getPoblacion() {
        return poblacion;
    }

    /**
     * Setter de la poblacion
     *
     * @param poblacion
     */
    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    /**
     * Getter de la superficie
     *
     * @return
     */
    public double getSuperficie() {
        return superficie;
    }

    /**
     * Setter de la superficie
     *
     * @param superficie
     */
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * Getter de la superficie
     *
     * @return
     */
    public List<Pais> getFrontera() {
        return frontera;
    }

    /**
     * Setter de la superficie
     *
     * @param frontera
     */
    public void setFrontera(List<Pais> frontera) {
        this.frontera = frontera;
    }
}

