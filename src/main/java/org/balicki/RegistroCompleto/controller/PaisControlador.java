package org.balicki.RegistroCompleto.controller;

import org.balicki.RegistroCompleto.entity.Pais;
import org.balicki.RegistroCompleto.exception.InformacionExcepciones;
import org.balicki.RegistroCompleto.model.Colecciones;
import org.balicki.RegistroCompleto.service.PaisServicio;
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Anotacion Controller para configurar esta clase como controlador
 * Anotacion RequestMapping se utiliza el mapeo a nivel de clase
 */
@Controller
@RequestMapping("pais")
public class PaisControlador {
    /**
     * PaisServicio va a ser el que nos
     * permita usar todos los metodos relacionados
     * con la bbdd
     */
    @Autowired
    private PaisServicio servicio;
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
     * Enviamos la lista de paises para mostrarlos en la vista
     * @param _id
     * @param sesionHttp
     * @param solicitudHttp
     * @param respuestaHttp
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("listarPais")
    public ModelAndView listarPais(@CookieValue(value = "_id", required = false) String _id,
                                   HttpSession sesionHttp,
                                   HttpServletRequest solicitudHttp,
                                   HttpServletResponse respuestaHttp,
                                   RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("listarPais");
            String ip = null;
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String navegador = solicitudHttp.getHeader("user-agent");
            Colecciones.generaCookies(_id, sesionHttp, solicitudHttp, respuestaHttp, mAV, messageSource);
            mAV.addObject("ultimoUser", Colecciones.ultimoUser);
            mAV.addObject("contador", Colecciones.cont);
            mAV.addObject("ip", ip);
            mAV.addObject("navegador", navegador);
            try {
                mAV.addObject("paises", servicio.listarTodo());
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validPais.lista.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Enviamos un objeto pais vacio a la vista para hacer de formulario
     * @return
     */
    @GetMapping("nuevoPais")
    public ModelAndView agregarPais(RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("nuevoPais");
            try {
                mAV.addObject("fronteras", servicio.listarTodo());
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validPais.lista.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
            mAV.addObject("pais", new Pais());
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Comprobamos si el pais introducido tiene errores los devolvera
     * a la vista con los errores correspondientes
     * Comprobamos si el cliente esta creando o modificando un pais para
     * enviarle correctamente su respectivo mensaje, redirigimos al listado
     * @param pais
     * @param br
     * @param atributosRedirigidos
     * @return
     */
    @PostMapping("nuevoPais")
    public ModelAndView guardarPais(@Valid @ModelAttribute Pais pais,
                                    BindingResult br,
                                    RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            if (br.hasErrors()) {
                mAV.addObject("pais", pais);
                mAV.setViewName("nuevoPais");
            } else {
                try {
                    servicio.guardarPais(pais);
                    if (Colecciones.modifica) {
                        atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                                messageSource.getMessage("validPais.cambiar.ok", null,
                                        "ERROR: Label not found", LocaleContextHolder.getLocale()));
                        Colecciones.modifica = false;
                    } else {
                        atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                                messageSource.getMessage("validPais.alta.ok", null,
                                        "ERROR: Label not found", LocaleContextHolder.getLocale()));
                    }
                } catch (Exception e) {
                    atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                            messageSource.getMessage("validPais.alta.nok", null,
                                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
                }
                mAV.setViewName("redirect:/pais/listarPais");
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Recuperamos el id del objeto que vayamos a editar indicando en la ruta
     * editarPais/ seguido del id recogido
     * Indicamos al metodo de guardarPais de nuevo que estamos modificando
     * @param id
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("editarPais/{id}")
    public ModelAndView editarDepartamento(@PathVariable long id,
                                           RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("nuevoPais");
            try {
                try {
                    mAV.addObject("fronteras", servicio.listarTodo());
                } catch (Exception e) {
                    atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                            messageSource.getMessage("validPais.lista.nok", null,
                                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
                }
                mAV.addObject("pais", servicio.listarPaisPorId(id));
                Colecciones.modifica = true;
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validPais.cambiar.nok", null,
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
     * eliminarPais/ seguido del id recogido
     * @param id
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("eliminarPais/{id}")
    public ModelAndView eliminarDepartamento(@PathVariable long id,
                                             RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("redirect:/pais/listarPais");
            try {
                servicio.borrarPorId(id);
                atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                        messageSource.getMessage("validPais.borrar.ok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validPais.borrar.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Le decimos al servicio que vamos a eliminar todos los paises
     * IMPORTANTE si hay un usuario creado con ese pais saltara excepcion,
     * ya que no se puede borrar un pais que tiene referenciado a un usuario
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("eliminarPaises")
    public ModelAndView eliminarDepartamentos(RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("redirect:/pais/listarPais");
            try {
                servicio.borrarTodo();
                atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                        messageSource.getMessage("validPais.borrarTodo.ok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validPais.borrarTodo.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }
}