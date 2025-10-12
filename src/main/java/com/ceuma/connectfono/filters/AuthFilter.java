package com.ceuma.connectfono.filters;

import com.ceuma.connectfono.services.JsonWebTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter extends OncePerRequestFilter {
    private final JsonWebTokenService jsonWebTokenService;
    private final ObjectMapper objectMapper;

    public AuthFilter(JsonWebTokenService jsonWebTokenService) {
        this.jsonWebTokenService = jsonWebTokenService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (isPublicPath(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        if (token == null) {
            sendErrorResponse(response, "Token ausente");
            return;
        }
        try {
            String username = jsonWebTokenService.validateToken(token);

            var authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("authenticatedUser", username);

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, e.getMessage());
            return;
        }


        chain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Acesso negado");
        errorResponse.put("message", customizeErrorMessage(errorMessage));

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private String customizeErrorMessage(String originalMessage) {
        if (originalMessage.contains("expirado")) {
            return "Sua sessão expirou. Faça login novamente.";
        } else if (originalMessage.contains("inválida") || originalMessage.contains("inválido")) {
            return "Token de autenticação inválido. Verifique suas credenciais.";
        } else if (originalMessage.contains("ausente")) {
            return "Token de autenticação é obrigatório para acessar este recurso.";
        } else {
            return "Falha na autenticação. Faça login novamente.";
        }
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/auth") || path.startsWith("/login");
    }

}
