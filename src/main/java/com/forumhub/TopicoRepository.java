package com.forumhub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.forumhub.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
   Page <Topico> findAllByAtivoTrue(Pageable paginacao);
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);
}
