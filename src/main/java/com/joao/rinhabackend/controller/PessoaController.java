package com.joao.rinhabackend.controller;

import com.joao.rinhabackend.dto.PessoaRequestDto;
import com.joao.rinhabackend.dto.PessoaResponseDto;
import com.joao.rinhabackend.repository.PessoaRepository;
import com.joao.rinhabackend.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
@Validated
public class PessoaController {
    @Autowired
    PessoaService pessoaService;

    @Autowired
    PessoaRepository pessoaRepository;

    @PostMapping
    public ResponseEntity<PessoaResponseDto> savePessoa(@Valid @RequestBody PessoaRequestDto pessoareq) {
        PessoaResponseDto pessoa = pessoaService.savePessoa(pessoareq);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> getById(@PathVariable UUID id){
        PessoaResponseDto pessoa = pessoaService.getPessoaById(id);
        return ResponseEntity.ok().body(pessoa);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDto>> getAllByTerm(@RequestParam(value = "t") String term) {
        return ResponseEntity.ok(pessoaService.findAllByTerm(term));
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<String> countPeople() {
        return ResponseEntity.ok(pessoaService.countPeople().toString());
    }

//    @GetMapping("/contagem-pessoas")
//        public Long countPeople() {
//            return pessoaRepository.count();
//        }

}
