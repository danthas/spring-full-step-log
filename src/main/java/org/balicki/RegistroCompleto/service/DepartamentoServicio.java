package org.balicki.RegistroCompleto.service;

import org.balicki.RegistroCompleto.entity.Departamento;
import java.util.List;

/**
 * Declaro un servicio para realizar
 * funciones de bbdd con el objecto
 * de la clase Departamento
 */
public interface DepartamentoServicio {
    /**
     * Devuelve una lista de objetos de la clase Departamento
     * @return
     * @throws Exception
     */
    List<Departamento> listarTodo() throws Exception;

    /**
     * Devuelve un objeto de la clase Departamento
     * al pasarle un id especifico
     * @param id
     * @return
     * @throws Exception
     */
    Departamento listarDepartamentoPorId(long id) throws Exception;

    /**
     * Guarda un objeto de la clase Departamento
     * en especifico
     * @param departamento
     * @throws Exception
     */
    void guardarDepartamento(Departamento departamento) throws Exception;

    /**
     * Borra un objeto de la clase Departamento
     * al pasarle un id que lo referencia
     * @param id
     * @throws Exception
     */
    void borrarPorId(long id) throws Exception;

    /**
     * Borra todos los objetos de la clase Departamento
     * @throws Exception
     */
    void borrarTodo() throws Exception;
}
