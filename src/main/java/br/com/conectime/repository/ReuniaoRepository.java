
package br.com.conectime.repository;

import br.com.conectime.model.Reuniao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReuniaoRepository extends JpaRepository<Reuniao, Long> {

    List<Reuniao> findByUsuario_IdUsuarioOrderByCriadoEmDesc(Long idUsuario);

    Optional<Reuniao> findByIdReuniaoAndUsuario_IdUsuario(
            Long idReuniao,
            Long idUsuario
    );
}
