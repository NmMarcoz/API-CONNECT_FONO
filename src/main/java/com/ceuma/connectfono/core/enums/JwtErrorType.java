package com.ceuma.connectfono.core.enums;

public enum JwtErrorType {
    TOKEN_EXPIRED("Token expirado", "Sua sessão expirou. Faça login novamente."),
    INVALID_SIGNATURE("Assinatura inválida", "Token de autenticação inválido. Verifique suas credenciais."),
    TOKEN_MISSING("Token ausente", "Token de autenticação é obrigatório para acessar este recurso."),
    MALFORMED_TOKEN("Token malformado", "Token de autenticação está em formato inválido."),
    UNSUPPORTED_TOKEN("Token não suportado", "Tipo de token não suportado pelo sistema."),
    GENERIC_ERROR("Erro genérico", "Falha na autenticação. Faça login novamente.");

    private final String code;
    private final String userMessage;

    JwtErrorType(String code, String userMessage) {
        this.code = code;
        this.userMessage = userMessage;
    }

    public String getCode() {
        return code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public static JwtErrorType fromMessage(String errorMessage) {
        if (errorMessage == null) return GENERIC_ERROR;

        String lowerMessage = errorMessage.toLowerCase();

        if (lowerMessage.contains("expirado") || lowerMessage.contains("expired")) {
            return TOKEN_EXPIRED;
        } else if (lowerMessage.contains("assinatura") || lowerMessage.contains("signature")) {
            return INVALID_SIGNATURE;
        } else if (lowerMessage.contains("ausente") || lowerMessage.contains("missing")) {
            return TOKEN_MISSING;
        } else if (lowerMessage.contains("malformed")) {
            return MALFORMED_TOKEN;
        } else if (lowerMessage.contains("unsupported")) {
            return UNSUPPORTED_TOKEN;
        } else {
            return GENERIC_ERROR;
        }
    }
}
