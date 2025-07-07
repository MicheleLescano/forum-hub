package com.forumhub;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoResposta(@NotBlank String mensagem) {
}
