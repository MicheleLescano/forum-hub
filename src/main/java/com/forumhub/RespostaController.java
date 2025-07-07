package com.forumhub;

import org.springframework.security.access.AccessDeniedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoResposta> cadastrar(@RequestBody @Valid DadosCadastroResposta dados, Authentication authentication, UriComponentsBuilder uriBuilder) {

        var autor = (Usuario) authentication.getPrincipal();

        var topico = topicoRepository.findById(dados.idTopico())
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado com o ID fornecido."));

        var resposta = new Resposta();
        resposta.setMensagem(dados.mensagem());
        resposta.setTopico(topico);
        resposta.setAutor(autor);
        resposta.setAtivo(true);

        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoResposta> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoResposta dados, Authentication authentication) {
        var resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada."));

        var usuarioLogado = (Usuario) authentication.getPrincipal();
        if (!resposta.getAutor().equals(usuarioLogado)) {
            throw new AccessDeniedException("Usuário não tem permissão para alterar esta resposta.");
        }

        resposta.setMensagem(dados.mensagem());
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id, Authentication authentication) {
        var resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada."));

        var usuarioLogado = (Usuario) authentication.getPrincipal();
        if (!resposta.getAutor().equals(usuarioLogado)) {
            throw new AccessDeniedException("Usuário não tem permissão para excluir esta resposta.");
        }

        resposta.excluir();
        return ResponseEntity.noContent().build();
    }


}
