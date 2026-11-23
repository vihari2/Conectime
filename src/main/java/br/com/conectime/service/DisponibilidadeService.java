
package br.com.conectime.service;

import br.com.conectime.model.Convidado;
import br.com.conectime.model.Disponibilidade;
import br.com.conectime.model.OpcaoHorario;

import br.com.conectime.repository.ConvidadoRepository;
import br.com.conectime.repository.DisponibilidadeRepository;
import br.com.conectime.repository.OpcaoHorarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class DisponibilidadeService {

    private final DisponibilidadeRepository disponibilidadeRepository;
    private final ConvidadoRepository convidadoRepository;
    private final OpcaoHorarioRepository opcaoHorarioRepository;

    public DisponibilidadeService(
            DisponibilidadeRepository disponibilidadeRepository,
            ConvidadoRepository convidadoRepository,
            OpcaoHorarioRepository opcaoHorarioRepository) {

        this.disponibilidadeRepository = disponibilidadeRepository;
        this.convidadoRepository = convidadoRepository;
        this.opcaoHorarioRepository = opcaoHorarioRepository;
    }

    // Registra ou atualiza os horários disponíveis do convidado
    @Transactional
    public List<Disponibilidade> registrarDisponibilidades(
            String token,
            List<Long> idsHorarios) {

        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException(
                    "Token inválido."
            );
        }

        if (idsHorarios == null) {
            throw new IllegalArgumentException(
                    "A lista de horários é obrigatória."
            );
        }

        // Identifica o convidado pelo token exclusivo (RN04)
        Convidado convidado = convidadoRepository.findByToken(token.trim())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Convite não encontrado ou inválido."
                        ));

        // Verifica se há IDs repetidos na seleção
        Set<Long> idsUnicos = new LinkedHashSet<>(idsHorarios);

        if (idsUnicos.size() != idsHorarios.size()) {
            throw new IllegalArgumentException(
                    "Não é permitido selecionar o mesmo horário duas vezes."
            );
        }

        List<OpcaoHorario> horariosSelecionados = new ArrayList<>();

        // Valida todos os horários antes de modificar a resposta
        for (Long idHorario : idsUnicos) {

            if (idHorario == null) {
                throw new IllegalArgumentException(
                        "Identificador de horário inválido."
                );
            }

            OpcaoHorario horario = opcaoHorarioRepository.findById(idHorario)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Opção de horário não encontrada."
                            ));

            // RN05: o horário precisa pertencer à reunião do convidado
            if (!horario.getReuniao().getIdReuniao()
                    .equals(convidado.getReuniao().getIdReuniao())) {

                throw new IllegalArgumentException(
                        "Este horário não pertence à sua reunião."
                );
            }

            horariosSelecionados.add(horario);
        }

        // Remove a resposta anterior, caso exista
        disponibilidadeRepository.deleteByConvidado_IdConvidado(
                convidado.getIdConvidado()
        );

        List<Disponibilidade> disponibilidades = new ArrayList<>();

        // Registra somente os horários selecionados
        for (OpcaoHorario horario : horariosSelecionados) {

            Disponibilidade disponibilidade = new Disponibilidade();

            disponibilidade.setConvidado(convidado);
            disponibilidade.setOpcaoHorario(horario);

            disponibilidades.add(disponibilidade);
        }

        // Salva as novas disponibilidades
        List<Disponibilidade> salvas =
                disponibilidadeRepository.saveAll(disponibilidades);

        // Marca o convidado como respondido
        // Mesmo que não tenha selecionado nenhum horário
        convidado.setStatusResposta("respondido");
        convidado.setRespondidoEm(LocalDateTime.now());

        convidadoRepository.save(convidado);

        return salvas;
    }

    // Consulta os horários marcados por um convidado
    @Transactional(readOnly = true)
    public List<Disponibilidade> listarPorConvidado(Long idConvidado) {

        return disponibilidadeRepository
                .findByConvidado_IdConvidado(idConvidado);
    }

    // Lista os convidados disponíveis em determinado horário
    @Transactional(readOnly = true)
    public List<Disponibilidade> listarPorHorario(Long idHorario) {

        return disponibilidadeRepository
                .findByOpcaoHorario_IdHorario(idHorario);
    }

    // Conta quantos convidados podem participar de um horário
    @Transactional(readOnly = true)
    public long contarDisponibilidades(Long idHorario) {

        return disponibilidadeRepository
                .countByOpcaoHorario_IdHorario(idHorario);
    }
}
