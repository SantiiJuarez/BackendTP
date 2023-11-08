package com.example.TPGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
//@EnableWebFluxSecurity
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas (RouteLocatorBuilder builder){
        return builder.routes()
                //Test para ver si funciona
                .route(p -> p.path("/get/**")
                        .uri("https://google.com"))
                .build();

    }

//    @Bean
//    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
//        http.authorizeExchange(exchanges -> exchanges
//
//                        // Esta ruta puede ser accedida por cualquiera, sin autorización
//                        .pathMatchers("/api/personas/**")
//                        .hasRole("KEMPES_ADMIN")
//
//                        .pathMatchers("/api/entradas/**")
//                        .hasRole("KEMPES_ORGANIZADOR")
//
//                        // Cualquier otra petición...
//                        .anyExchange()
//                        .authenticated()
//
//                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .csrf(csrf -> csrf.disable());
//        return http.build();
//    }
//
//    @Bean
//    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
//        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
//        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//
//        // Se especifica el nombre del claim a analizar
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//        // Se agrega este prefijo en la conversión por una convención de Spring
//        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//
//        // Se asocia el conversor de Authorities al Bean que convierte el token JWT a un objeto Authorization
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
//                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));
//        // También se puede cambiar el claim que corresponde al nombre que luego se utilizará en el objeto
//        // Authorization
//        // jwtAuthenticationConverter.setPrincipalClaimName("user_name");
//
//        return jwtAuthenticationConverter;
//    }
}
