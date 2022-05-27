package org.balicki.RegistroCompleto.service;

import org.balicki.RegistroCompleto.entity.Usuario;
import java.util.List;

/**
 * Declaro un servicio para realizar
 * funciones de bbdd con el objecto
 * de la clase Usuario
 */
public interface UsuarioServicio {
    /**
     * Devuelve una lista de objetos de la clase Usuario
     * @return
     * @throws Exception
     */
    List<Usuario> listarTodo() throws Exception;

    /**
     * Devuelve un objeto de la clase Usuario
     * al pasarle un id especifico
     * @param id
     * @return
     * @throws Exception
     */
    Usuario listarUsuarioPorId(long id) throws Exception;

    /**
     * Guarda un objeto de la clase Usuario
     * en especifico
     * @param usuario
     * @throws Exception
     */
    void guardarUsuario(Usuario usuario) throws Exception;

    /**
     * Borra un objeto de la clase Usuario
     * al pasarle un id que lo referencia
     * @param id
     * @throws Exception
     */
    void borrarPorId(long id) throws Exception;

    /**
     * Borra todos los objetos de la clase Usuario
     * @throws Exception
     */
    void borrarTodo() throws Exception;
}
