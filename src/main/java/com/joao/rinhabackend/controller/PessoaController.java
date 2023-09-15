package com.joao.rinhabackend.controller;

import com.joao.rinhabackend.dto.PessoaRequestDto;
import com.joao.rinhabackend.dto.PessoaResponseDto;
import com.joao.rinhabackend.excetions.NotFoundException;
import com.joao.rinhabackend.models.Pessoa;
import com.joao.rinhabackend.repository.PessoaRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
@Validated
public class PessoaController {
    @Autowired
    PessoaRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<Pessoa> savePessoa(@Valid @RequestBody PessoaRequestDto pessoareq) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(UUID.randomUUID());
        pessoa.setNome(pessoareq.nome());
        pessoa.setApelido(pessoareq.apelido());
        pessoa.setNascimento(pessoareq.nascimento());
        pessoa.setStack(pessoareq.stack());

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> getAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findAll());
    }

    @GetMapping("/{id}")
    public PessoaResponseDto getById(@PathVariable UUID id){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Pessoa com o id %s não foi encontrada", id)));

        return PessoaResponseDto.builder()
                .id(pessoa.getId())
                .apelido(pessoa.getApelido())
                .nome(pessoa.getNome())
                .nascimento(pessoa.getNascimento())
                .stack(pessoa.getStack())
                .build();
    }

}
