package org.balicki.RegistroCompleto.repository;

import org.balicki.RegistroCompleto.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Hereda de JpaRepository para poder utilizar
 * los metodos para bbdd
 */
@Repository
public interface PaisRepositorio extends JpaRepository<Pais, Long> {
    /**
     * Declaracion de un metodo de abstracto
     * para listar un Pais por id
     * @param id
     * @return
     */
    Pais findPaisById(long id);

    /**
     * Declaracion de un metodo de abstracto
     * para listar un Pais por nombre
     * @param nombre
     * @return
     */
    Pais findPaisByNombre(String nombre);
}
