package com.forumhub;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta>findByTopicoIdAndAtivoTrue(Long id);
}
