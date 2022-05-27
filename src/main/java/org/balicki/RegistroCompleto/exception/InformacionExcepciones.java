package org.balicki.RegistroCompleto.exception;

public class InformacionExcepciones {
    /**
     * Recibe una excepcion y muestra su mensaje,
     * donde se localiza ese mensaje, su causa,
     * y la clase que lanza esa excepcion
     * @param e
     */
    public static void muestraInformacionExcepcion(Exception e) {
        System.err.println("Message: " + e.getMessage());
        System.err.println("Localized message: " + e.getLocalizedMessage());
        System.err.println("Cause: " + e.getCause());
        System.err.println("Exception class: " + e.getClass());
    }
}
