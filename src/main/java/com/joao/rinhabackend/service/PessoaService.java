package com.joao.rinhabackend.service;

import com.joao.rinhabackend.dto.PessoaRequestDto;
import com.joao.rinhabackend.dto.PessoaResponseDto;
import com.joao.rinhabackend.excetions.BadRequestException;
import com.joao.rinhabackend.excetions.CustomValidationException;
import com.joao.rinhabackend.excetions.NotFoundException;
import com.joao.rinhabackend.models.Pessoa;
import com.joao.rinhabackend.repository.PessoaRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    public PessoaResponseDto savePessoa(PessoaRequestDto pessoareq) {
        Pessoa pessoa;

        try {
            pessoa = pessoaRepository.save(pessoareq.toEntity());
        } catch(DataIntegrityViolationException e) {
            throw new CustomValidationException(String.format("Entidade não processad: %s", e.getMessage()));
        }

        return PessoaResponseDto.builder()
                .id(pessoa.getId())
                .apelido(pessoa.getApelido())
                .nome(pessoa.getNome())
                .nascimento(pessoa.getNascimento())
                .stack(pessoa.getStack())
                .build();
    }

    public PessoaResponseDto getPessoaById(UUID id){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Pessoa com o id %s não foi encontrada", id));

        return PessoaResponseDto.builder()
                .id(pessoa.getId())
                .apelido(pessoa.getApelido())
                .nome(pessoa.getNome())
                .nascimento(pessoa.getNascimento())
                .stack(pessoa.getStack())
                .build();
    }

    public List<PessoaResponseDto> findAllByTerm(String term) {
        if(term.isBlank() || term.isEmpty()) {
            throw new BadRequestException("Termo de busca null ou em branco!");
        }

        return pessoaRepository.findAllByTerm(term).stream()
                .map(pessoa -> PessoaResponseDto.builder()
                        .id(pessoa.getId())
                        .apelido(pessoa.getApelido())
                        .nome(pessoa.getNome())
                        .nascimento(pessoa.getNascimento())
                        .stack(pessoa.getStack())
                        .build())
                .toList();
    }

    public Long countPeople() {
        return pessoaRepository.count();
    }
}
