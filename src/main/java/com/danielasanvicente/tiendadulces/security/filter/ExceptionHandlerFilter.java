package com.danielasanvicente.tiendadulces.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.danielasanvicente.tiendadulces.exception.EntityNotFoundException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        //Intenta seguir al siguiente filtro
        try {
            filterChain.doFilter(request, response);
        }
        //Se intentó encontrar el cliente por el correo enviado en el login pero no fue encontrado
        catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Correo de cliente invalido");
            response.getWriter().flush();
        }
        //Se envió un encabezado de authorization inválido (generalmente un jwt expirado)
        catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("JWT invalido");
            response.getWriter().flush();
        }
        //Petición inválida o mal formulada
        catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("BAD REQUEST");
            response.getWriter().flush();
        }  
    }
}
