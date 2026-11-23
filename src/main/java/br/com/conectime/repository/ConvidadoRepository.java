
package br.com.conectime.repository;

import br.com.conectime.model.Convidado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConvidadoRepository extends JpaRepository<Convidado, Long> {

    Optional<Convidado> findByToken(String token);

    List<Convidado> findByReuniao_IdReuniao(Long idReuniao);

    boolean existsByReuniao_IdReuniaoAndEmail(
            Long idReuniao,
            String email
    );
}
