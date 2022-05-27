package org.balicki.RegistroCompleto.service.impl;

import org.balicki.RegistroCompleto.entity.Usuario;
import org.balicki.RegistroCompleto.exception.ExcepcionPersonalizada;
import org.balicki.RegistroCompleto.exception.InformacionExcepciones;
import org.balicki.RegistroCompleto.exception.mysql.TraductorErroresMySQL;
import org.balicki.RegistroCompleto.repository.UsuarioRepositorio;
import org.balicki.RegistroCompleto.service.UsuarioServicio;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.sql.SQLException;
import java.util.List;

/**
 * Creamos una clase que va implementar el servicio que
 * hemos creado anteriormente (implementamos los metodos)
 * TODO ESTO HACE RELACION A LA BBDD
 */
@Service
public class UsuarioServicioImpl implements UsuarioServicio {
    /**
     * UsuarioRepositorio va a ser el que nos permita
     * realizar funciones para la bbdd
     * Anotacion Autowired para la inyeccion de dependencias
     */
    @Autowired
    private UsuarioRepositorio repositorio;

    /**
     * Implementacion del metodo del servicio
     * para listar todos los Usuarios
     * Nos devolvera los Usuarios ordenados alfabeticamente
     * por el campo apellidos
     * @return
     * @throws Exception
     */
    @Override
    public List<Usuario> listarTodo() throws Exception {
        List<Usuario> listaUsuarios = null;
        try {
            listaUsuarios = repositorio.findAll(Sort.by(Sort.Direction.ASC, "apellidos"));
        } catch (CannotCreateTransactionException cCTE) {
            JDBCConnectionException jdbcCE = (JDBCConnectionException) cCTE.getCause();
            System.err.println("SQL state: " + jdbcCE.getSQLState());
            InformacionExcepciones.muestraInformacionExcepcion(cCTE);
            throw new ExcepcionPersonalizada("bd.errorConexion", jdbcCE);
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
            throw e;
        }
        return listaUsuarios;
    }

    /**
     * Implementacion del metodo del servicio
     * para listar un Usuario por un id
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Usuario listarUsuarioPorId(long id) throws Exception {
        Usuario usuario = null;
        try {
            usuario = repositorio.findUsuarioById(id);
        } catch (CannotCreateTransactionException cCTE) {
            JDBCConnectionException jdbcCE = (JDBCConnectionException) cCTE.getCause();
            System.err.println("SQL state: " + jdbcCE.getSQLState());
            InformacionExcepciones.muestraInformacionExcepcion(cCTE);
            throw new ExcepcionPersonalizada("bd.errorConexion", jdbcCE);
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
            throw e;
        }
        return usuario;
    }

    /**
     * Implementacion del metodo del servicio
     * para guardar un Usuario deseado
     * @param usuario
     * @throws Exception
     */
    @Override
    public void guardarUsuario(Usuario usuario) throws Exception {
        try {
            repositorio.save(usuario);
        } catch (
                DataIntegrityViolationException dIVE) {
            ConstraintViolationException cVE;
            PropertyValueException pVE;
            DataException dE;
            SQLException sqlE;
            String mensajeUsuario;
            if (dIVE.getCause() instanceof ConstraintViolationException) {
                cVE = (ConstraintViolationException) dIVE.getCause();
                System.err.println("Error code: " + cVE.getErrorCode());
                System.err.println("SQL: " + cVE.getSQL());
                System.err.println("SQL state: " + cVE.getSQLState());
                System.err.println("Constraint name: " + cVE.getConstraintName());
                sqlE = cVE.getSQLException();
                System.err.println("Detail SQL message: " + sqlE.getMessage());
                mensajeUsuario = TraductorErroresMySQL.devuelveTextoExcepcion(cVE.getErrorCode());
                throw new ExcepcionPersonalizada(mensajeUsuario, dIVE);
            }
            if (dIVE.getCause() instanceof PropertyValueException) {
                pVE = (PropertyValueException) dIVE.getCause();
                System.err.println("Entity name: " + pVE.getEntityName());
                System.err.println("Property name: " + pVE.getPropertyName());
            }
            if (dIVE.getCause() instanceof DataException) {
                dE = (DataException) dIVE.getCause();
                System.err.println("Error code: " + dE.getErrorCode());
                System.err.println("SQL: " + dE.getSQL());
                System.err.println("SQL state: " + dE.getSQLState());
                sqlE = dE.getSQLException();
                mensajeUsuario = TraductorErroresMySQL.devuelveTextoExcepcion(dE.getErrorCode());
                throw new ExcepcionPersonalizada(mensajeUsuario, dIVE);
            }
            InformacionExcepciones.muestraInformacionExcepcion(dIVE);

        } catch (JDBCConnectionException jdbcCE) {
            InformacionExcepciones.muestraInformacionExcepcion(jdbcCE);
        } catch (CannotCreateTransactionException cCTE) {
            JDBCConnectionException jdbcCE = (JDBCConnectionException) cCTE.getCause();
            System.err.println("SQL state: " + jdbcCE.getSQLState());
            InformacionExcepciones.muestraInformacionExcepcion(cCTE);
            throw new ExcepcionPersonalizada("bd.errorConexion", jdbcCE);
        } catch (
                DataAccessResourceFailureException dARFE) {
            InformacionExcepciones.muestraInformacionExcepcion(dARFE);
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
        }
    }

    /**
     * Implementacion del metodo del servicio
     * para borrar un Usuario por el id
     * @param id
     * @throws Exception
     */
    @Override
    public void borrarPorId(long id) throws Exception {
        try {
            repositorio.deleteById(id);
        } catch (DataIntegrityViolationException dIVE) {
            ConstraintViolationException cVE;
            PropertyValueException pVE;
            DataException dE;
            SQLException sqlE;
            String mensajeUsuario;
            if (dIVE.getCause() instanceof ConstraintViolationException) {
                cVE = (ConstraintViolationException) dIVE.getCause();
                System.err.println("Error code: " + cVE.getErrorCode());
                System.err.println("SQL: " + cVE.getSQL());
                System.err.println("SQL state: " + cVE.getSQLState());
                System.err.println("Constraint name: " + cVE.getConstraintName());
                sqlE = cVE.getSQLException();
                System.err.println("Detail SQL message: " + sqlE.getMessage());
                mensajeUsuario = TraductorErroresMySQL.devuelveTextoExcepcion(cVE.getErrorCode());
                throw new ExcepcionPersonalizada(mensajeUsuario, dIVE);
            }
            if (dIVE.getCause() instanceof PropertyValueException) {
                pVE = (PropertyValueException) dIVE.getCause();
                System.err.println("Entity name: " + pVE.getEntityName());
                System.err.println("Property name: " + pVE.getPropertyName());
            }
            if (dIVE.getCause() instanceof DataException) {
                dE = (DataException) dIVE.getCause();
                System.err.println("Error code: " + dE.getErrorCode());
                System.err.println("SQL: " + dE.getSQL());
                System.err.println("SQL state: " + dE.getSQLState());
                sqlE = dE.getSQLException();
                mensajeUsuario = TraductorErroresMySQL.devuelveTextoExcepcion(dE.getErrorCode());
                throw new ExcepcionPersonalizada(mensajeUsuario, dIVE);
            }
            InformacionExcepciones.muestraInformacionExcepcion(dIVE);
        } catch (JDBCConnectionException jdbcCE) {
            InformacionExcepciones.muestraInformacionExcepcion(jdbcCE);
        } catch (CannotCreateTransactionException cCTE) {
            JDBCConnectionException jdbcCE = (JDBCConnectionException) cCTE.getCause();
            System.err.println("SQL state: " + jdbcCE.getSQLState());
            InformacionExcepciones.muestraInformacionExcepcion(cCTE);
            throw new ExcepcionPersonalizada("bd.errorConexion", jdbcCE);
        } catch (DataAccessResourceFailureException dARFE) {
            InformacionExcepciones.muestraInformacionExcepcion(dARFE);
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
        }
    }

    /**
     * Implementacion del metodo del servicio
     * para borrar todos los Usuarios
     * @throws Exception
     */
    @Override
    public void borrarTodo() throws Exception {
        try {
            repositorio.deleteAll();
        } catch (DataIntegrityViolationException dIVE) {
            ConstraintViolationException cVE;
            PropertyValueException pVE;
            DataException dE;
            SQLException sqlE;
            String mensajeUsuario;
            if (dIVE.getCause() instanceof ConstraintViolationException) {
                cVE = (ConstraintViolationException) dIVE.getCause();
                System.err.println("Error code: " + cVE.getErrorCode());
                System.err.println("SQL: " + cVE.getSQL());
                System.err.println("SQL state: " + cVE.getSQLState());
                System.err.println("Constraint name: " + cVE.getConstraintName());
                sqlE = cVE.getSQLException();
                System.err.println("Detail SQL message: " + sqlE.getMessage());
                mensajeUsuario = TraductorErroresMySQL.devuelveTextoExcepcion(cVE.getErrorCode());
                throw new ExcepcionPersonalizada(mensajeUsuario, dIVE);
            }
            if (dIVE.getCause() instanceof PropertyValueException) {
                pVE = (PropertyValueException) dIVE.getCause();
                System.err.println("Entity name: " + pVE.getEntityName());
                System.err.println("Property name: " + pVE.getPropertyName());
            }
            if (dIVE.getCause() instanceof DataException) {
                dE = (DataException) dIVE.getCause();
                System.err.println("Error code: " + dE.getErrorCode());
                System.err.println("SQL: " + dE.getSQL());
                System.err.println("SQL state: " + dE.getSQLState());
                sqlE = dE.getSQLException();
                mensajeUsuario = TraductorErroresMySQL.devuelveTextoExcepcion(dE.getErrorCode());
                throw new ExcepcionPersonalizada(mensajeUsuario, dIVE);
            }
            InformacionExcepciones.muestraInformacionExcepcion(dIVE);
        } catch (JDBCConnectionException jdbcCE) {
            InformacionExcepciones.muestraInformacionExcepcion(jdbcCE);
        } catch (CannotCreateTransactionException cCTE) {
            JDBCConnectionException jdbcCE = (JDBCConnectionException) cCTE.getCause();
            System.err.println("SQL state: " + jdbcCE.getSQLState());
            InformacionExcepciones.muestraInformacionExcepcion(cCTE);
            throw new ExcepcionPersonalizada("bd.errorConexion", jdbcCE);
        } catch (DataAccessResourceFailureException dARFE) {
            InformacionExcepciones.muestraInformacionExcepcion(dARFE);
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
        }
    }
}
