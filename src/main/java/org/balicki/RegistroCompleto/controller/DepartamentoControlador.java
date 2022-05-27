package org.balicki.RegistroCompleto.controller;

import org.balicki.RegistroCompleto.entity.Departamento;
import org.balicki.RegistroCompleto.model.Colecciones;
import org.balicki.RegistroCompleto.service.DepartamentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * Anotacion Controller para configurar esta clase como controlador
 * Anotacion RequestMapping se utiliza el mapeo a nivel de clase
 */
@Controller
@RequestMapping("departamento")
public class DepartamentoControlador {
    /**
     * DepartamentoServicio va a ser el que nos
     * permita usar todos los metodos relacionados
     * con la bbdd
     */
    @Autowired
    private DepartamentoServicio servicio;
    /**
     * MessageSource va a ser el encargado de
     * sustraer mensajes del messages.properties
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Si no hay ninguna cookie existente creo las requeridas
     * Enviamos los parametros a la vista que tienen relacion con la cabecera
     * Enviamos la lista de departamentos para mostrarlos en la vista
     * @param _id
     * @param sesionHttp
     * @param solicitudHttp
     * @param respuestaHttp
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("listarDep")
    public ModelAndView listarDepartamento(@CookieValue(value = "_id", required = false) String _id,
                                           HttpSession sesionHttp,
                                           HttpServletRequest solicitudHttp,
                                           HttpServletResponse respuestaHttp,
                                           RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("listarDep");
            String ip = null;
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String navegador = solicitudHttp.getHeader("user-agent");
            Colecciones.generaCookies(_id, sesionHttp, solicitudHttp, respuestaHttp, mAV);
            mAV.addObject("ultimoUser", Colecciones.ultimoUser);
            mAV.addObject("contador", Colecciones.cont);
            mAV.addObject("ip", ip);
            mAV.addObject("navegador", navegador);
            try {
                mAV.addObject("departamentos", servicio.listarTodo());
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validDepartamento.lista.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Enviamos un objeto departamento vacio a la vista para hacer de formulario
     * @return
     */
    @GetMapping("nuevoDep")
    public ModelAndView agregarDepartamento() {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("nuevoDep");
            mAV.addObject("departamento", new Departamento());
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Comprobamos si el departamento introducido tiene errores los devolvera
     * a la vista con los errores correspondientes
     * Comprobamos si el cliente esta creando o modificando un departamento
     * para enviarle correctamente su respectivo mensaje, redirigimos al listado
     * @param departamento
     * @param br
     * @param atributosRedirigidos
     * @return
     */
    @PostMapping("nuevoDep")
    public ModelAndView guardarDepartamento(@Valid @ModelAttribute Departamento departamento,
                                            BindingResult br,
                                            RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            if (br.hasErrors()) {
                mAV.addObject("departamento", departamento);
                mAV.setViewName("nuevoDep");
            } else {
                try {
                    servicio.guardarDepartamento(departamento);
                    if (Colecciones.modifica) {
                        atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                                messageSource.getMessage("validDepartamento.cambiar.ok", null,
                                        "ERROR: Label not found", LocaleContextHolder.getLocale()));
                        Colecciones.modifica = false;
                    } else {
                        atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                                messageSource.getMessage("validDepartamento.alta.ok", null,
                                        "ERROR: Label not found", LocaleContextHolder.getLocale()));
                    }
                } catch (Exception e) {
                    atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                            messageSource.getMessage("validDepartamento.alta.nok", null,
                                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
                }
                mAV.setViewName("redirect:/departamento/listarDep");
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Recuperamos el id del objeto que vayamos a editar indicando en la ruta
     * editarDep/ seguido del id recogido
     * Indicamos al metodo de guardarDepartamento de nuevo que estamos modificando
     * @param id
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("editarDep/{id}")
    public ModelAndView editarDepartamento(@PathVariable long id,
                                           RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("nuevoDep");
            try {
                mAV.addObject("departamento", servicio.listarDepartamentoPorId(id));
                Colecciones.modifica = true;
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validDepartamento.cambiar.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Recuperamos el id del objeto que vayamos a eliminar indicando en la ruta
     * eliminarDep/ seguido del id recogido
     * @param id
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("eliminarDep/{id}")
    public ModelAndView eliminarDepartamento(@PathVariable long id,
                                             RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("redirect:/departamento/listarDep");
            try {
                servicio.borrarPorId(id);
                atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                        messageSource.getMessage("validDepartamento.borrar.ok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validDepartamento.borrar.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        }  else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Le decimos al servicio que vamos a eliminar todos los departamentos
     * IMPORTANTE, si hay un usuario creado con ese departamento saltara
     * excepcion, ya que no se puede borrar un departamento que tiene referenciado
     * a un usuario
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("eliminarDeps")
    public ModelAndView eliminarDepartamentos(RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("redirect:/departamento/listarDep");
            try {
                servicio.borrarTodo();
                atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                        messageSource.getMessage("validDepartamento.borrarTodo.ok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validDepartamento.borrarTodo.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        }  else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }
}
