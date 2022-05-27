package org.balicki.RegistroCompleto.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Anotacion Controller para configurar esta clase como controlador
 * Anotacion RequestMapping se utiliza el mapeo a nivel de clase
 */
@Controller
@RequestMapping("acceso")
public class AccesoController {
    /**
     * UsuarioServicio para las funciones de usuario
     */
    @Autowired
    private UsuarioServicio usuario;
    /**
     * DepartamentoServicio para las funciones de departamento
     */
    @Autowired
    private DepartamentoServicio departamento;
    /**
     * PaisServicio para las funciones de pais
     */
    @Autowired
    private PaisServicio pais;
    /**
     * MessageSource para llamar al messages properties para mensajes
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Metodo para mostrar la vista de login
     * Leera los csv de departamento y pais,
     * y los agregara a la bbdd si no existen
     * Tambien recogemos el UsuarioLoginDTO de la sesion
     * solo si es nulo para recuperar el login ya hecho
     * @param sesionHttp
     * @return
     */
    @GetMapping("login")
    public ModelAndView devuelveFormularioLogin(HttpSession sesionHttp) {
        ModelAndView mAV = new ModelAndView();
        if (Colecciones.leerCsv) {
            Colecciones.leerInsertarFicheroDepartamento(departamento);
            Colecciones.leerInsertarFicheroPais(pais);
            //Colecciones.agregarFronteras(pais);
        }
        Colecciones.leerCsv = false;
        UsuarioLoginDTO usuarioLoginDTO;
        if (sesionHttp.getAttribute("usuarioLoginDTO") != null) {
            usuarioLoginDTO = (UsuarioLoginDTO) sesionHttp.getAttribute("usuarioLoginDTO");
        } else {
            usuarioLoginDTO = new UsuarioLoginDTO();
        }
        mAV.addObject("usuarioLoginDTO", usuarioLoginDTO);
        mAV.setViewName("login");
        return mAV;
    }

    /**
     * Comprobamos si UsuarioLoginDTO tiene algun error
     * sino comparamos el usuario introducido con uno
     * de la bbdd y si coincide quitaremos el usuario
     * anterior de la sesion e introduciremos este nuevo
     * por ultimo se nos redirigira al listado de Usuarios
     * @param usuarioLoginDTO
     * @param br
     * @param sesionHttp
     * @param atributosRedirigidos
     * @return
     */
    @PostMapping("login")
    public ModelAndView recibeCredencialesLogin(@Valid UsuarioLoginDTO usuarioLoginDTO,
                                                BindingResult br,
                                                HttpSession sesionHttp,
                                                RedirectAttributes atributosRedirigidos) {
        ModelAndView mAV = new ModelAndView();
        if (br.hasErrors()) {
            mAV.addObject("otroError", messageSource.getMessage("validLogin.usuario.nok", null,
                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
            mAV.addObject("usuarioLoginDTO", usuarioLoginDTO);
            mAV.setViewName("login");
        } else {
            try {
                for (int i = 0; i < usuario.listarTodo().size(); i++) {
                    if (usuarioLoginDTO.getCorreo().equals(usuario.listarTodo().get(i).getCorreo()) &&
                            usuarioLoginDTO.getClave().equals(usuario.listarTodo().get(i).getClave())) {
                        Colecciones.login = true;
                        atributosRedirigidos.addFlashAttribute("resultadoAccionOK",
                                messageSource.getMessage("validLogin.login.ok", null,
                                        "ERROR: Label not found", LocaleContextHolder.getLocale()));
                    }
                }
            } catch (Exception e) {
                atributosRedirigidos.addFlashAttribute("resultadoAccionNOK",
                        messageSource.getMessage("validLogin.login.nok", null,
                                "ERROR: Label not found", LocaleContextHolder.getLocale()));
            }
            if (Colecciones.login) {
                if (sesionHttp.getAttribute("usuarioLoginDTO") != null) {
                    sesionHttp.removeAttribute("usuarioLoginDTO");
                    sesionHttp.setAttribute("usuarioLoginDTO", usuarioLoginDTO);
                } else {
                    sesionHttp.setAttribute("usuarioLoginDTO", usuarioLoginDTO);
                }
                mAV.setViewName("redirect:/usuario/listarUser");
            } else {
                mAV.addObject("otroError", messageSource.getMessage("validLogin.usuario.nok", null,
                        "ERROR: Label not found", LocaleContextHolder.getLocale()));
                mAV.addObject("usuarioLoginDTO", usuarioLoginDTO);
                mAV.setViewName("login");
            }
        }
        return mAV;
    }

    /**
     * Destruimos la sesion y redirigimos al login
     * @param sesionHttp
     * @return
     */
    @GetMapping("logout")
    public ModelAndView desconexion(HttpSession sesionHttp) {
        ModelAndView mAV = new ModelAndView();
        sesionHttp.invalidate();
        Colecciones.login = false;
        mAV.setViewName("redirect:login");
        return mAV;
    }
}
