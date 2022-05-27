package org.balicki.RegistroCompleto.service;

import org.balicki.RegistroCompleto.entity.Pais;
import java.util.List;

/**
 * Declaro un servicio para realizar
 * funciones de bbdd con el objecto
 * de la clase Pais
 */
public interface PaisServicio {
    /**
     * Devuelve una lista de objetos de la clase Pais
     * @return
     * @throws Exception
     */
    List<Pais> listarTodo() throws Exception;

    /**
     * Devuelve un objeto de la clase Pais
     * al pasarle un id especifico
     * @param id
     * @return
     * @throws Exception
     */
    Pais listarPaisPorId(long id) throws Exception;

    /**
     * Devuelve un objeto de la clase Pais
     * al pasarle un nombre especifico
     * @param nombre
     * @return
     * @throws Exception
     */
    Pais listarPaisPorNombre(String nombre) throws Exception;

    /**
     * Guarda un objeto de la clase Pais
     * en especifico
     * @param pais
     * @throws Exception
     */
    void guardarPais(Pais pais) throws Exception;

    /**
     * Borra un objeto de la clase Pais
     * al pasarle un id que lo referencia
     * @param id
     * @throws Exception
     */
    void borrarPorId(long id) throws Exception;

    /**
     * Borra todos los objetos de la clase Pais
     * @throws Exception
     */
    void borrarTodo() throws Exception;
}
