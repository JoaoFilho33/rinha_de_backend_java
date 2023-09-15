package com.joao.rinhabackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record PessoaResponseDto(
        UUID id,
        String nome,

        String apelido,
        String nascimento,
        List<String> stack
) {
}
