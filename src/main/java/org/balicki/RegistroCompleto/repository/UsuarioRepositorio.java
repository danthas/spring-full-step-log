package org.balicki.RegistroCompleto.repository;

import org.balicki.RegistroCompleto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Hereda de JpaRepository para poder utilizar
 * los metodos para bbdd
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    /**
     * Declaracion de un metodo de abstracto
     * para listar un Usuario por id
     * @param id
     * @return
     */
    Usuario findUsuarioById(long id);
}
