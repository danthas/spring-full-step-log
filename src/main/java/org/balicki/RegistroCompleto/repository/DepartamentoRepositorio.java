package org.balicki.RegistroCompleto.repository;

import org.balicki.RegistroCompleto.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Hereda de JpaRepository para poder utilizar
 * los metodos para bbdd
 */
@Repository
public interface DepartamentoRepositorio extends JpaRepository<Departamento, Long> {
    /**
     * Declaracion de un metodo de abstracto
     * para listar un Departamento por id
     * @param id
     * @return
     */
    Departamento findDepartamentoById(long id);
}
