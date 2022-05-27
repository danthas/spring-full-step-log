package org.balicki.RegistroCompleto.exception;

public class ExcepcionPersonalizada extends Exception {
    /**
     * Excepcion lanzada
     */
    private Exception excepcionElevada;
    /**
     * Mensaje de la excepcion para el usuario
     */
    private String mensajeUsuario;

    /**
     * Constructor vacio
     */
    public ExcepcionPersonalizada() {
    }

    /**
     * Contructor con todos los atributos
     * @param mensajeUsuario
     * @param excepcionElevada
     */
    public ExcepcionPersonalizada(String mensajeUsuario,
                                  Exception excepcionElevada) {
        this.excepcionElevada = excepcionElevada;
        this.mensajeUsuario = mensajeUsuario;
    }

    /**
     * Getter de la excepcion lanzada
     * @return
     */
    public Exception getExcepcionElevada() {
        return excepcionElevada;
    }

    /**
     * Setter de la excepcion lanzada
     * @param excepcionElevada
     */
    public void setExcepcionElevada(Exception excepcionElevada) {
        this.excepcionElevada = excepcionElevada;
    }

    /**
     * Getter del mensaje
     * @return
     */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    /**
     * Setter del mensaje
     * @param mensajeUsuario
     */
    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }
}
