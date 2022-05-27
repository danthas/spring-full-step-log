package org.balicki.RegistroCompleto.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Anotacion Entity para confirmar que es una entidad
 * Anotacion Table para que nos cree una tabla con el
 * nombre seleccionado en este caso usuario y en
 * la bbdd registro_completo
 * Tambien va a tener una constraint de unicidad en
 * el atributo dni
 */
@Entity
@Table(name = "usuario", schema = "registro_completo",
        uniqueConstraints = {
            @UniqueConstraint(name = "uq_dni", columnNames = {"dni"}),
            @UniqueConstraint(name = "uq_correo", columnNames = {"correo"})
        })
public class Usuario {
    /**
     * Atributo id generado automaticamente y autoincremental
     * Asignamos el nombre de la columna
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    /**
     * Atributo dni con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.dni.notNull}")
    @NotBlank(message = "{validUser.dni.notBlank}")
    @Pattern(regexp = "[A-Za-z][0-9]{7,8}[A-Za-z]", message = "{validUser.dni.pattern}")
    @Column(name = "dni")
    private String dni;
    /**
     * Atributo nombre con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.nombre.notNull}")
    @NotBlank(message = "{validUser.nombre.notBlank}")
    @Column(name = "nombre")
    private String nombre;
    /**
     * Atributo apellidos con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.apellidos.notNull}")
    @NotBlank(message = "{validUser.apellidos.notBlank}")
    @Column(name = "apellido")
    private String apellidos;
    /**
     * Atributo genero con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.genero.notNull}")
    @NotBlank(message = "{validUser.genero.notBlank}")
    @Column(name = "genero")
    private String genero;
    /**
     * Atributo correo con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.correo.notNull}")
    @NotBlank(message = "{validUser.correo.notBlank}")
    @Email(regexp = "([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+", message = "{validUser.correo.email}")
    @Column(name = "correo")
    private String correo;
    /**
     * Atributo clave con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.clave.notNull}")
    @NotBlank(message = "{validUser.clave.notBlank}")
    @Pattern(regexp = "^(?=(?:.*\\d))(?=.*[A-Z])(?=.*[a-z])(?=.*[.,*!?¿¡/#$%&_])\\S{6,12}$", message = "{validUser.clave.pattern}")
    @Column(name = "clave")
    private String clave;
    /**
     * Atributo fecha nacimiento con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.fechaNac.notNull}")
    @Column(name = "fecha_nac")
    private LocalDate fechaNac;
    /**
     * Atributo salario con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.salario.notNull}")
    @DecimalMin(value = "1000.0", message = "{validUser.salario.decimalMin}")
    @Column(name = "salario")
    private float salario;
    /**
     * Atributo departamento con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.departamento.notNull}")
    @OneToOne(targetEntity = Departamento.class)
    private Departamento departamento;
    /**
     * Atributo pais con sus validaciones
     * Asignamos el nombre de la columna
     */
    @NotNull(message = "{validUser.pais.notNull}")
    @OneToOne(targetEntity = Pais.class)
    private Pais pais;

    /**
     * Constructor vacio
     */
    public Usuario() {
    }

    /**
     * Contructor con todos los atributos
     * @param id
     * @param dni
     * @param nombre
     * @param apellidos
     * @param genero
     * @param correo
     * @param clave
     * @param fechaNac
     * @param salario
     * @param departamento
     * @param pais
     */
    public Usuario(long id,
                   String dni,
                   String nombre,
                   String apellidos,
                   String genero,
                   String correo,
                   String clave,
                   LocalDate fechaNac,
                   float salario,
                   Departamento departamento,
                   Pais pais) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.correo = correo;
        this.clave = clave;
        this.fechaNac = fechaNac;
        this.salario = salario;
        this.departamento = departamento;
        this.pais = pais;
    }

    /**
     * Contructor con los atributos:
     * @param dni
     * @param nombre
     * @param apellidos
     * @param genero
     * @param correo
     * @param clave
     * @param fechaNac
     * @param salario
     * @param departamento
     * @param pais
     */
    public Usuario(String dni,
                   String nombre,
                   String apellidos,
                   String genero,
                   String correo,
                   String clave,
                   LocalDate fechaNac,
                   float salario,
                   Departamento departamento,
                   Pais pais) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.correo = correo;
        this.clave = clave;
        this.fechaNac = fechaNac;
        this.salario = salario;
        this.departamento = departamento;
        this.pais = pais;
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
     * Getter del dni
     * @return
     */
    public String getDni() {
        return dni;
    }

    /**
     * Setter del dni
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Getter del nombre
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /***
     * Getter del nombre
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de apellidos
     * @return
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Setter de apellidos
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Getter de genero
     * @return
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Setter de genero
     * @param genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Getter del correo
     * @return
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Setter del correo
     * @param correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Getter de la clave
     * @return
     */
    public String getClave() {
        return clave;
    }

    /**
     * Setter de la clave
     * @param clave
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Getter de fecha de nacimiento
     * @return
     */
    public LocalDate getFechaNac() {
        return fechaNac;
    }

    /**
     * Setter de fecha de nacimiento
     * @param fechaNac
     */
    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    /**
     * Getter del salario
     * @return
     */
    public float getSalario() {
        return salario;
    }

    /**
     * Setter del salario
     * @param salario
     */
    public void setSalario(float salario) {
        this.salario = salario;
    }

    /**
     * Getter del departamento
     * @return
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * Setter del departamento
     * @param departamento
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * Getter del pais
     * @return
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * Setter del pais
     * @param pais
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
