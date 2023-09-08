package com.formacionbdi.springboot.app.gateway.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

    public EjemploGatewayFilterFactory() {
        super(Configuracion.class);
    }

    @Override
    public GatewayFilter apply(Configuracion config) {
        return (exchange, chain) -> {
            LOGGER.info("Ejecutando PRE gateway filter factory, {}", config.mensaje);

            Optional.ofNullable(config.cookieValor)
                    .ifPresent( cookie -> exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build()));

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                LOGGER.info("Ejecutando POST gateway filter factory");

            }));

        };
    }

    /*
    Metodo que nos permite organizar por medio de una lista
    el orden como deben ir los campos de la clase configuracion
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("mensaje", "cookieValor", "cookieNombre");
    }

    @Override
    public String name() {
        return "EjemploCookie";
    }

    /*
            Clase que configura la cookie, esta es la base para
            construir la cookie con sus valores
             */
    public static class Configuracion {

        private String mensaje;
        private String cookieValor;
        private String cookieNombre;

        public String getMensaje(){
            return mensaje;
        }
        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
        public String getCookieValor() {
            return cookieValor;
        }
        public void setCookieValor(String cookieValor) {
            this.cookieValor = cookieValor;
        }
        public String getCookieNombre() {
            return cookieNombre;
        }
        public void setCookieNombre(String cookieNombre) {
            this.cookieNombre = cookieNombre;
        }
    }
}
