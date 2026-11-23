
package br.com.conectime.repository;

import br.com.conectime.model.OpcaoHorario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcaoHorarioRepository
        extends JpaRepository<OpcaoHorario, Long> {

    List<OpcaoHorario> findByReuniao_IdReuniaoOrderByDataHoraInicioAsc(
            Long idReuniao
    );
}
