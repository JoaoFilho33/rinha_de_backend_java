package com.joao.rinhabackend.dto;

import com.joao.rinhabackend.models.Pessoa;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;


import java.util.List;
import java.util.UUID;

@Builder
public record PessoaRequestDto(
        UUID id,
        @NotNull
        @NotEmpty
        @Size(min = 1, max = 100)
        String nome,
        @NotNull
        @NotEmpty
        @Size(min = 1, max=32)
        String apelido,
        @NotNull
        @NotEmpty
        @Pattern(regexp = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Date is invalid. Valid format: 'YYYY-MM-DD'")
        String nascimento,
        List<@Size(min = 1, max = 32) String> stack
) {
        public Pessoa toEntity() {
                return Pessoa.builder()
                        .id(UUID.randomUUID())
                        .apelido(apelido)
                        .nome(nome)
                        .nascimento(nascimento)
                        .stack(stack)
                        .build();
        }
}
