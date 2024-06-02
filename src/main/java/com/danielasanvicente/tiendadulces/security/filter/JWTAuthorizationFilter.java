package com.danielasanvicente.tiendadulces.security.filter;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.danielasanvicente.tiendadulces.security.SecurityConstants;

//Filtro responsable por verificar si el usuario tiene permiso
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtiene el valor del token enviado en el encabezado authorization dentro de la petición
        String header = request.getHeader("Authorization");

        //Si no fue enviado el token en la petición termina este filtro aquí
        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        //Separa el valor del token (Bearer JWT)
        String token = header.replace(SecurityConstants.BEARER, "");
        //Compara el token enviado en el encabezado con el token que sería válido para el usuario
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
            .build()
            .verify(token)
            .getSubject();

        //Si el token es válido se añade el objeto de autenticación al contexto de seguridad de Spring
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
