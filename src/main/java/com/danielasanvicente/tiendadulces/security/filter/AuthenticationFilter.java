package com.danielasanvicente.tiendadulces.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.danielasanvicente.tiendadulces.entity.Cliente;
import com.danielasanvicente.tiendadulces.security.SecurityConstants;
import com.danielasanvicente.tiendadulces.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private CustomAuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, 
        HttpServletResponse response
    ) throws AuthenticationException {
        try {
            //Obtiene los valores de correo y contraseña.
            Cliente cliente = new ObjectMapper().readValue(request.getInputStream(), Cliente.class);
            //Crea un objeto de autenticación basado en el modelo de nombre de usuario y contraseña
            Authentication authentication = new UsernamePasswordAuthenticationToken(cliente.getCorreo(), cliente.getContrasena());

            //Llama el método authenticate dentro de la clase CustomAuthenticationManager que está dentro de la carpeta manager
            //El método authenticate llama el método successfulAuthentication o unsuccessfulAuthentication a depender si fue exitosa la autenticación
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        } 
    }

    // Si no funciona authenticate con el objeto de autenticación enviado, se llama este método.
    // Envía una respuesta de estatus 401 unauthorized
    @Override
    protected void unsuccessfulAuthentication(
        HttpServletRequest request, 
        HttpServletResponse response, 
        AuthenticationException failed
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    // Si funciona authenticate con el objeto de autenticación enviado, se llama este método
    @Override
    protected void successfulAuthentication(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain chain,
        Authentication authResult
    ) throws IOException, ServletException {
        //Crea el token
        String token = JWT.create()
            //obtiene el correo enviado que estaba dentro del objeto Authentication
            .withSubject(authResult.getName())
            //define el tiempo de duración del token
            .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
            //Firma el JWT de acuerdo con el secret de la aplicación
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));


        //Crea un objeto que será la respuesta enviada por la API cuando la autenticación funciona correctamente
        Map<String, String> responseBody = new HashMap<>();
        //Crea un campo token en el Json con el valor del token
        responseBody.put("token", token);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //Añade el valor del token al encabezado authorization en la respuesta de la API
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
        //Envía la respuesta como un json
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
