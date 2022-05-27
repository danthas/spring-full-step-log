package org.balicki.RegistroCompleto.controller;

import org.balicki.RegistroCompleto.entity.Usuario;
import org.balicki.RegistroCompleto.model.Colecciones;
import org.balicki.RegistroCompleto.model.UsuarioLoginDTO;
import org.balicki.RegistroCompleto.service.DepartamentoServicio;
import org.balicki.RegistroCompleto.service.PaisServicio;
import org.balicki.RegistroCompleto.service.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Anotacion Controller para configurar esta clase como controlador
 * Anotacion RequestMapping se utiliza el mapeo a nivel de clase
 */
@Controller
@RequestMapping("usuario")
public class UsuarioControlador {
    /**
     * UsuarioServicio va a ser el que nos
     * permita usar todos los metodos relacionados
     * con la bbdd de usuario
     */
    @Autowired
    private UsuarioServicio servicio;
    /**
     * DepartamentoServicio va a ser el que nos
     * permita usar todos los metodos relacionados
     * con la bbdd de departamento
     */
    @Autowired
    private DepartamentoServicio departamento;
    /**
     * PaisServicio va a ser el que nos
     * permita usar todos los metodos relacionados
     * con la bbdd de pais
     */
    @Autowired
    private PaisServicio pais;
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
     * Enviamos la lista de usuarios para mostrarlos en la vista
     * @param _id
     * @param sesionHttp
     * @param solicitudHttp
     * @param respuestaHttp
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("listarUser")
    public ModelAndView listarUsuario(@CookieValue(value = "_id", required = false) String _id,
                                      HttpSession sesionHttp,
                                      HttpServletRequest solicitudHttp,
                                      HttpServletResponse respuestaHttp,
                                      RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("listarUser");
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
                mAV.addObject("usuarios", servicio.listarTodo());
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validUser.lista.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Enviamos la lista de generos para que el cliente seleccione uno
     * Enviamos la lista de departamentos para que el cliente seleccione uno
     * Enviamos la lista de paises para que el cliente seleccione uno
     * Enviamos un objeto usuario vacio a la vista para hacer de formulario
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("nuevoUser")
    public ModelAndView agregarUsuario(RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView("nuevoUser");
        mAV.addObject("listaGenerosOrdenada", Colecciones.listaGenerosOrdenada);
        try {
            mAV.addObject("departamentos", departamento.listarTodo());
            mAV.addObject("paises", pais.listarTodo());
        } catch (Exception e) {
            atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                    messageSource.getMessage("validDepartamento.lista.nok", null,
                            "ERROR: Label not found", LocaleContextHolder.getLocale()));
            atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                    messageSource.getMessage("validPais.lista.nok", null,
                            "ERROR: Label not found", LocaleContextHolder.getLocale()));
        }
        mAV.addObject("usuario", new Usuario());
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Comprobamos si el usuario introducido tiene errores los devolvera
     * a la vista con los errores correspondientes
     * Comprobamos si el cliente esta creando o modificando un usuario
     * para enviarle correctamente su respectivo mensaje, redirigimos al listado
     * @param usuario
     * @param br
     * @param atributosRedirigidos
     * @param confirmaClave
     * @return
     */
    @PostMapping("nuevoUser")
    public ModelAndView guardarUsuario(@Valid @ModelAttribute Usuario usuario,
                                       BindingResult br,
                                       RedirectAttributes atributosRedirigidos,
                                       @RequestParam(name = "confirmaClave", required = false) String confirmaClave) {
        ModelAndView mAV = new ModelAndView();
        if (br.hasErrors()) {
            if (!usuario.getClave().equals(confirmaClave)) {
                mAV.addObject("errorClave", messageSource.getMessage("validUser.clave.nok", null,
                        "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
            mAV.addObject("listaGenerosOrdenada", Colecciones.listaGenerosOrdenada);
            try {
                mAV.addObject("paises", pais.listarTodo());
                mAV.addObject("departamentos", departamento.listarTodo());
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validPais.lista.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validDepartamento.lista.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
            mAV.addObject("usuario", usuario);
            mAV.setViewName("nuevoUser");
        } else if (!usuario.getClave().equals(confirmaClave)) {
            mAV.addObject("errorClave", messageSource.getMessage("validUser.clave.nok", null,
                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
            mAV.addObject("listaGenerosOrdenada", Colecciones.listaGenerosOrdenada);
            try {
                mAV.addObject("paises", pais.listarTodo());
                mAV.addObject("departamentos", departamento.listarTodo());
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validUser.alta.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
            mAV.addObject("usuario", usuario);
            mAV.setViewName("nuevoUser");
        } else {
            try {
                servicio.guardarUsuario(usuario);
                if (Colecciones.modifica) {
                    atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                            messageSource.getMessage("validUser.cambiar.ok", null,
                                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
                    mAV.setViewName("redirect:/usuario/listarUser");
                    Colecciones.modifica = false;
                } else {
                    atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                            messageSource.getMessage("validUser.alta.ok", null,
                                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
                    mAV.setViewName("redirect:/acceso/login");
                }
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validUser.alta.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Recuperamos el id del objeto que vayamos a editar indicando en la ruta
     * editarUser/ seguido del id recogido
     * Indicamos al metodo de guardarUser de nuevo que estamos modificando
     * @param id
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("editarUser/{id}")
    public ModelAndView editarUsuario(@PathVariable long id,
                                      RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.login) {
            mAV.setViewName("nuevoUser");
            try {
                mAV.addObject("listaGenerosOrdenada", Colecciones.listaGenerosOrdenada);
                try {
                    mAV.addObject("paises", pais.listarTodo());
                    mAV.addObject("departamentos", departamento.listarTodo());
                } catch (Exception e) {
                    atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                            messageSource.getMessage("validDepartamento.lista.nok", null,
                                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
                    atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                            messageSource.getMessage("validPais.lista.nok", null,
                                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
                }
                mAV.addObject("usuario", servicio.listarUsuarioPorId(id));
                Colecciones.modifica = true;
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validUser.cambiar.nok", null,
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
     * eliminarUser/ seguido del id recogido
     * @param id
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("eliminarUser/{id}")
    public ModelAndView eliminarUsuario(@PathVariable long id,
                                        HttpSession sesionHttp,
                                        RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        UsuarioLoginDTO usuarioLoginDTO;
        usuarioLoginDTO = (UsuarioLoginDTO) sesionHttp.getAttribute("usuarioLoginDTO");
        if (Colecciones.login) {
            mAV.setViewName("redirect:/usuario/listarUser");
            try {
                if (usuarioLoginDTO.getCorreo().equals(servicio.listarUsuarioPorId(id).getCorreo())) {
                    sesionHttp.removeAttribute("usuarioLoginDTO");
                    sesionHttp.invalidate();
                    Colecciones.login = false;
                    mAV.setViewName("redirect:/acceso/login");
                }
                servicio.borrarPorId(id);
                atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                        messageSource.getMessage("validUser.borrar.ok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validUser.borrar.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }

    /**
     * Comprobamos si el cliente ha iniciado sesion sino nos redirige al login
     * Le decimos al servicio que vamos a eliminar todos los usuarios
     * @param atributosRedirigidos
     * @return
     */
    @GetMapping("eliminarUsers")
    public ModelAndView eliminarUsuarios(HttpSession sesionHttp,
                                         RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        UsuarioLoginDTO usuarioLoginDTO;
        usuarioLoginDTO = (UsuarioLoginDTO) sesionHttp.getAttribute("usuarioLoginDTO");
        if (Colecciones.login) {
            mAV.setViewName("redirect:/usuario/listarUser");
            try {
                for (int i = 0; i < servicio.listarTodo().size(); i++) {
                    if (usuarioLoginDTO.getCorreo().equals(servicio.listarTodo().get(i).getCorreo())) {
                        sesionHttp.removeAttribute("usuarioLoginDTO");
                        sesionHttp.invalidate();
                        Colecciones.login = false;
                        mAV.setViewName("redirect:/acceso/login");
                    }
                }
                servicio.borrarTodo();
                atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                        messageSource.getMessage("validUser.borrarTodo.ok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validUser.borrarTodo.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
        } else {
            mAV.setViewName("redirect:/acceso/login");
        }
        return mAV;
    }
}
