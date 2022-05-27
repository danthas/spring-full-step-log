package org.balicki.RegistroCompleto.service.impl;

import org.balicki.RegistroCompleto.entity.Departamento;
import org.balicki.RegistroCompleto.exception.ExcepcionPersonalizada;
import org.balicki.RegistroCompleto.exception.InformacionExcepciones;
import org.balicki.RegistroCompleto.exception.mysql.TraductorErroresMySQL;
import org.balicki.RegistroCompleto.repository.DepartamentoRepositorio;
import org.balicki.RegistroCompleto.service.DepartamentoServicio;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
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
public class DepartamentoServicioImpl implements DepartamentoServicio {
    /**
     * DepartamentoRepositorio va a ser el que nos permita
     * realizar funciones para la bbdd
     * Anotacion Autowired para la inyeccion de dependencias
     */
    @Autowired
    private DepartamentoRepositorio repositorio;

    /**
     * Implementacion del metodo del servicio
     * para listar todos los Departamentos
     * @return
     * @throws Exception
     */
    @Override
    public List<Departamento> listarTodo() throws Exception {
        List<Departamento> listaDepartamentos = null;
        try {
            listaDepartamentos = repositorio.findAll();
        } catch (CannotCreateTransactionException cCTE) {
            JDBCConnectionException jdbcCE = (JDBCConnectionException) cCTE.getCause();
            System.err.println("SQL state: " + jdbcCE.getSQLState());
            InformacionExcepciones.muestraInformacionExcepcion(cCTE);
            throw new ExcepcionPersonalizada("bd.errorConexion", jdbcCE);
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
            throw e;
        }
        return listaDepartamentos;
    }

    /**
     * Implementacion del metodo del servicio
     * para listar un Departamento por un id
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Departamento listarDepartamentoPorId(long id) throws Exception {
        Departamento departamento = null;
        try {
            departamento = repositorio.findDepartamentoById(id);
        } catch (CannotCreateTransactionException cCTE) {
            JDBCConnectionException jdbcCE = (JDBCConnectionException) cCTE.getCause();
            System.err.println("SQL state: " + jdbcCE.getSQLState());
            InformacionExcepciones.muestraInformacionExcepcion(cCTE);
            throw new ExcepcionPersonalizada("bd.errorConexion", jdbcCE);
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
            throw e;
        }
        return departamento;
    }

    /**
     * Implementacion del metodo del servicio
     * para guardar un Departamento deseado
     * @param departamento
     * @throws Exception
     */
    @Override
    public void guardarDepartamento(Departamento departamento) throws Exception {
        try {
            repositorio.save(departamento);
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
     * para borrar un Departamento por el id
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
     * para borrar todos los Departamentos
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
