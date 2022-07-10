package org.balicki.RegistroCompleto.model;

import org.balicki.RegistroCompleto.entity.Departamento;
import org.balicki.RegistroCompleto.entity.Pais;
import org.balicki.RegistroCompleto.exception.InformacionExcepciones;
import org.balicki.RegistroCompleto.service.DepartamentoServicio;
import org.balicki.RegistroCompleto.service.PaisServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Colecciones {
    /**
     * Constante para de la ruta para leer departamento.csv
     */
    public static final String DEPARTAMENTO = "./src/main/resources/static/csv/departamento.csv";
    /**
     * Constante para de la ruta para leer pais.csv
     */
    public static final String PAIS = "./src/main/resources/static/csv/pais.csv";
    /**
     * Constante separar los campos del csv
     */
    public static final String SEPARADOR = ",";
    /**
     * Lista de generos que almaceno en local
     */
    public static SortedMap<String, String> listaGenerosOrdenada = new TreeMap<>();
    /**
     * Booleana para confirmar si has iniciado sesion
     */
    public static boolean login = false;
    /**
     * Booleana para confirmar si estas modificando un campo
     */
    public static boolean modifica = false;
    /**
     * Booleana para confirmar si has el servidor ha leido los csv
     */
    public static boolean leerCsv = true;
    /**
     * String para mostrar el ultimo usuario que ha iniciado sesion
     */
    public static String ultimoUser = "";
    /**
     * Contador de visitas
     */
    public static int cont = 1;
    /**
     * Cookie para contar las visitas
     */
    private static Cookie contador;
    /**
     * Cookie para asignar un id a cada usuario por sesion
     */
    private static Cookie id;

    /**
     * Introducimos tres generos a la lista
     */
    static {
        // Lista de generos
        listaGenerosOrdenada.put("H", "Hombre");
        listaGenerosOrdenada.put("M", "Mujer");
        listaGenerosOrdenada.put("O", "Otro");
    }

    /**
     * Recogemos el usuario de la sesion
     * Comprobamos si existe una cookie con el id
     * sino la creara junto con la cookie contador
     * Mostramos un mensaje de bienvenida al cliente
     * @param _id
     * @param sesionHttp
     * @param solicitudHttp
     * @param respuestaHttp
     * @param mAV
     */
    public static void generaCookies(String _id,
                                     HttpSession sesionHttp,
                                     HttpServletRequest solicitudHttp,
                                     HttpServletResponse respuestaHttp,
                                     ModelAndView mAV,
                                     MessageSource messageSource) {
        UsuarioLoginDTO usuarioLoginDTO;
        usuarioLoginDTO = (UsuarioLoginDTO) sesionHttp.getAttribute("usuarioLoginDTO");
        if (_id != null && ultimoUser.equals(usuarioLoginDTO.getCorreo())) {
            id = new Cookie("_id", WebUtils.getCookie(solicitudHttp, "_id").getValue());
            cont = Integer.parseInt(WebUtils.getCookie(solicitudHttp, "_contador").getValue());
            cont++;
            contador = new Cookie("_contador", String.valueOf(cont));
            id.setPath("/");
            contador.setPath("/");
            respuestaHttp.addCookie(id);
            respuestaHttp.addCookie(contador);
            mAV.addObject("bienvenida", messageSource.getMessage("formGlobal.cabecera.bienvenidaNuevo", null,
                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
        } else {
            cont = 1;
            id = new Cookie("_id", generaCadenaAlfanumericaAleatoria(64));
            contador = new Cookie("_contador", String.valueOf(cont));
            id.setPath("/");
            contador.setPath("/");
            respuestaHttp.addCookie(id);
            respuestaHttp.addCookie(contador);
            mAV.addObject("bienvenida", messageSource.getMessage("formGlobal.cabecera.bienvenidaPrimera", null,
                    "ERROR: Label not found", LocaleContextHolder.getLocale()));
        }
        ultimoUser = usuarioLoginDTO.getCorreo();
    }

    /**
     * Genreamos una cadenaAlfanumerica de la longitud deseada
     * para asignarlo a la cookie de id
     * @param longitudCadena
     * @return
     */
    public static String generaCadenaAlfanumericaAleatoria(int longitudCadena) {
        int leftLimit = 48; // Numero 0
        int rightLimit = 122; // Letra z
        Random random = new Random();

        String cadenaAleatoriaGenerada = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(longitudCadena)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return (cadenaAleatoriaGenerada);
    }

    /**
     * Metodo para agregar las fronteras del csv a los
     * paises de la bbdd
     * @param servicio
     */
    public static void agregarFronteras(PaisServicio servicio) {
        try {
            BufferedReader br = null;
            int contador = 0;
            try {
                br = new BufferedReader(new FileReader(PAIS));
                String linea = br.readLine();
                while (linea != null) {
                    String[] campos = linea.split(SEPARADOR);
                    try {
                        List<Pais> frontera = new ArrayList<>();
                        for (int i = 0; i < campos.length; i++) {
                            if (i >= 4) {
                                frontera.add(servicio.listarPaisPorNombre(campos[i]));
                                System.err.println(campos[i]);
                            }
                        }
                        servicio.listarTodo().get(contador).setFrontera(frontera);
                        servicio.guardarPais(servicio.listarTodo().get(contador));
                        contador++;
                        linea = br.readLine();
                    } catch (Exception e) {
                        InformacionExcepciones.muestraInformacionExcepcion(e);
                    }
                }
            } catch (IOException e) {
                InformacionExcepciones.muestraInformacionExcepcion(e);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        InformacionExcepciones.muestraInformacionExcepcion(e);
                    }
                }
            }
        } catch (Exception e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
        }
    }

    /**
     * Meotodo para leer paises del csv
     * de pais y agregarlos a la bbdd
     * @param servicio
     */
    public static void leerInsertarFicheroPais(PaisServicio servicio) {
        BufferedReader br = null;
        Pais pais;
        try {
            br = new BufferedReader(new FileReader(PAIS));
            String linea = br.readLine();
            while (linea != null) {
                String[] campos = linea.split(SEPARADOR);
                pais = new Pais(campos[0],
                        campos[1],
                        Integer.parseInt(campos[2]),
                        Double.parseDouble(campos[3]));
                boolean encontrado = false;
                try {
                    for (int i = 0; i < servicio.listarTodo().size(); i++) {
                        if (servicio.listarTodo().get(i).getNombre().equals(pais.getNombre())) {
                            encontrado = true;
                        }
                    }
                    if (encontrado) {
                        linea = br.readLine();
                    } else {
                        servicio.guardarPais(pais);
                    }
                } catch (Exception e) {
                    InformacionExcepciones.muestraInformacionExcepcion(e);
                }
            }
        } catch (IOException e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    InformacionExcepciones.muestraInformacionExcepcion(e);
                }
            }
        }
    }

    /**
     * Meotodo para leer departamentos del csv
     * de departamento y agregarlos a la bbdd
     * @param servicio
     */
    public static void leerInsertarFicheroDepartamento(DepartamentoServicio servicio) {
        BufferedReader br = null;
        Departamento departamento;
        try {
            br = new BufferedReader(new FileReader(DEPARTAMENTO));
            String linea = br.readLine();
            while (linea != null) {
                String[] campos = linea.split(SEPARADOR);
                departamento = new Departamento(campos[0],
                        campos[1],
                        campos[2]);
                boolean encontrado = false;
                try {
                    for (int i = 0; i < servicio.listarTodo().size(); i++) {
                        if (servicio.listarTodo().get(i).getNumero().equals(departamento.getNumero())) {
                            encontrado = true;
                        }
                    }
                    if (encontrado) {
                        linea = br.readLine();
                    } else {
                        servicio.guardarDepartamento(departamento);
                    }
                } catch (Exception e) {
                    InformacionExcepciones.muestraInformacionExcepcion(e);
                }
            }
        } catch (IOException e) {
            InformacionExcepciones.muestraInformacionExcepcion(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    InformacionExcepciones.muestraInformacionExcepcion(e);
                }
            }
        }
    }
}

