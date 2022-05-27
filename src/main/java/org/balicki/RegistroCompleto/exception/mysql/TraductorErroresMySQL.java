package org.balicki.RegistroCompleto.exception.mysql;

public class TraductorErroresMySQL {
    /**
     * Metodo para mostrar los mensajes de base de datos
     * @param codigoExcepcion
     * @return
     */
    public static String devuelveTextoExcepcion(int codigoExcepcion) {
        switch (codigoExcepcion) {
            case 1062:
                return "bd.violacionUnicidad";
            case 1146:
                return "bd.tablaInexistente";
            case 1216:
                return "bd.violacionClaveAjena.modificacionRegistroDetalle";
            case 1217:
                return "bd.violacionClaveAjena.modificacionRegistroMaestro";
            case 1451:
                return "bd.violacionClaveAjena.eliminacionRegistroMaestro";
            default:
                return "bd.errorInespecifico";
        }
    }
}
