package org.balicki.RegistroCompleto.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Anotacion Configuration para definir que es nuestra clase de configuracion
 * Anotacion ComponentScan que se encarga de instanciar todos nuestros objetos
 * E implementa metodos de la clase WebMvcConfigurer que es el encargado
 * de poder permitirnos usar varios messages.propierties de distintos idiomas
 */
@Configuration
@ComponentScan(basePackages = "org.balicki.RegistroCompleto.config")
public class Configuracion implements WebMvcConfigurer {
    /**
     * Bean para definir el messageSource y su encoding
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Bean para la validacion de mensajes
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    /**
     * Bean para asignar el lenguaje por defecto
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        Locale castellano = new Locale("ES");
        slr.setDefaultLocale(castellano);
        return slr;
    }

    /**
     * Bean para asignar un parametro para el cambio de idioma
     * con un objeto de la clase LocaleChangeInterceptor
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Metodo para recuperar el LocaleChangeInterceptor
     * que estamos usando
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
