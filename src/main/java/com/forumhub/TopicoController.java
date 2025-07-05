package com.forumhub;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody  @Valid DadosCadastroTopico dados) {
        if (repository.findByTituloAndMensagem(dados.titulo(), dados.mensagem()).isPresent()) {
            throw new DataIntegrityViolationException("Tópico duplicado! Já existe um tópico com este mesmo título e mensagem.");
        }

        var topico = new Topico();
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setAutor(dados.autor());
        topico.setCurso(dados.curso());
        topico.setDataCriacao(LocalDateTime.now()); // Definir a data no momento do cadastro
        topico.setStatus("NAO_RESPONDIDO"); // Definir um status inicial
        topico.setAtivo(true);

        repository.save(topico);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = repository.findById(id);

        if (topicoOptional.isEmpty() || !topicoOptional.get().isAtivo()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topicoOptional.get()));
    }

    // No TopicoController.java

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dados) {
        var topicoOptional = repository.findById(id);
        if (topicoOptional.isEmpty() || !topicoOptional.get().isAtivo()) {
            return ResponseEntity.notFound().build();
        }

        var topicoExistente = repository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        if (topicoExistente.isPresent() && !topicoExistente.get().getId().equals(id)) {
            throw new DataIntegrityViolationException("Tópico duplicado! Já existe um tópico com este mesmo título e mensagem.");
        }
        var topico = topicoOptional.get();
        topico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }


//    @PutMapping("/{id}")
//    @Transactional
//    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados) {
//        var topico = repository.getReferenceById(id);
//        topico.atualizarInformacoes(dados);
//
//        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        topico.excluir();

        return ResponseEntity.noContent().build();
    }

}