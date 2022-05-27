package org.balicki.RegistroCompleto.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Clase con constructor para el inicio de sesion
 */
public class UsuarioLoginDTO {
    /**
     * Atributo correo con sus validaciones
     */
    @NotNull(message = "{validLogin.correo.notNull}")
    @NotBlank(message = "{validLogin.correo.notBlank}")
    @Email(regexp = "([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+", message = "{validLogin.correo.email}")
    private String correo;
    /**
     * Atributo clave con sus validaciones
     */
    @NotNull(message = "{validLogin.clave.notNull}")
    @NotBlank(message = "{validLogin.clave.notBlank}")
    @Pattern(regexp = "^(?=(?:.*\\d))(?=.*[A-Z])(?=.*[a-z])(?=.*[.,*!?¿¡/#$%&_])\\S{6,12}$", message = "{validLogin.clave.pattern}")
    private String clave;

    /**
     * Constructor vacio
     */
    public UsuarioLoginDTO() {
    }

    /**
     * Contructor con todos los atributos
     * @param nombre
     * @param clave
     */
    public UsuarioLoginDTO(String nombre, String clave) {
        this.correo = nombre;
        this.clave = clave;
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
}
