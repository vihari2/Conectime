package br.com.conectime.repository;

import br.com.conectime.model.Disponibilidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface DisponibilidadeRepository
        extends JpaRepository<Disponibilidade, Long> {

    List<Disponibilidade> findByConvidado_IdConvidado(Long idConvidado);

    @EntityGraph(attributePaths = "convidado")
    List<Disponibilidade> findByOpcaoHorario_IdHorario(Long idHorario);

    long countByOpcaoHorario_IdHorario(Long idHorario);

    void deleteByConvidado_IdConvidado(Long idConvidado);
}