package com.danielasanvicente.tiendadulces.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.danielasanvicente.tiendadulces.security.filter.AuthenticationFilter;
import com.danielasanvicente.tiendadulces.security.filter.ExceptionHandlerFilter;
import com.danielasanvicente.tiendadulces.security.filter.JWTAuthorizationFilter;
import com.danielasanvicente.tiendadulces.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Crea el filtro de autenticación personalizado
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        
        // define la url para autenticación
        authenticationFilter.setFilterProcessesUrl("/autenticar");
        
        http
            // deshabilita la protección CSRF
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> 
                authorize 
                    // solo permite la url /cliente/registrar sin autenticación o permisos
                    .requestMatchers(SecurityConstants.REGISTER_PATH).permitAll()
                    //permite todos los metodos http en la ruta "/login"
                    .requestMatchers(HttpMethod.GET,"/login").permitAll()
                    //permite todos los metodos http en la ruta "/"
                    .requestMatchers(HttpMethod.GET,"/").permitAll()
                    // permite todos los GET requests en la ruta "/dulce/*"
                    .requestMatchers(HttpMethod.GET, "/dulce/**").permitAll()
                    // permite todos los GET requests en la ruta "/arreglo/*"
                    .requestMatchers(HttpMethod.GET, "/arreglo/**").permitAll()
                    // permite todos los GET requests en la ruta "/mesa/*"
                    .requestMatchers(HttpMethod.GET, "/mesa/**").permitAll()
                    // permite todos los GET requests en la ruta "/cliente/*"
                    .requestMatchers(HttpMethod.GET, "/cliente/**").permitAll()
                    // permite todos los GET requests en la ruta "/orden/*"
                    .requestMatchers(HttpMethod.GET, "/orden/**").permitAll()
                    // permite todos los GET requests en la ruta "/pago/*"
                    .requestMatchers(HttpMethod.GET, "/pago/**").permitAll()
                    // permite todos los GET requests en la ruta "/metodoPago/*"
                    .requestMatchers(HttpMethod.GET, "/metodoPago/**").permitAll()
                    // permite todos los GET requests en la ruta "/carrito/*"
                    .requestMatchers(HttpMethod.GET, "/carrito/**").permitAll()
                    // permite todos los GET requests en la ruta "/itemCarrito/*"
                    .requestMatchers(HttpMethod.GET, "/itemCarrito/**").permitAll()
                    // permite todos los GET requests en la ruta "/lineaOrden/*"
                    .requestMatchers(HttpMethod.GET, "/lineaOrden/**").permitAll()
                    //permite acceso al contenido estático
                    .requestMatchers("/static/**").permitAll() 
                    .requestMatchers("/imagen/**").permitAll() 
                    .requestMatchers("/tema/**").permitAll()
                    .requestMatchers("/fonts/**").permitAll() 
                    .requestMatchers("/iconos/**").permitAll()
                    .requestMatchers("/bootstrap/**").permitAll()
                    // todas las demás rutas necesitan autenticación
                    .anyRequest().authenticated()
            )
            // define que el filtro de Excepciones debe actuar antes del filtro de autenticación
            .addFilterBefore(
                new ExceptionHandlerFilter(), 
                AuthenticationFilter.class
            )
            // el filtro de autenticación de la aplicación
            .addFilter(authenticationFilter)
            // define que el filtro de JWT debe actuar después del filtro de autenticación
            .addFilterAfter(
                new JWTAuthorizationFilter(), 
                AuthenticationFilter.class
            )
            // define que la aplicación no guardará datos de sesión en el servidor 
            // arquitectura sin estado de las APIs REST
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // define las reglas de CORS de la aplicación de acuerdo con el bean abajo
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            );



        return http.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:8080",
            "http://localhost:5173"
        ));
        
        configuration.setAllowedMethods(Arrays.asList(
            "GET", 
            "POST", 
            "PUT", 
            "DELETE", 
            "PATCH", 
            "HEAD"
        ));
        
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "X-Requested-With",
            "Cache-Control",
            "If-Modified-Since",
            "If-None-Match",
            "ETag"
        ));
        
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
