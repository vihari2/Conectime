
package br.com.conectime.service;

import br.com.conectime.model.OpcaoHorario;
import br.com.conectime.model.Reuniao;

import br.com.conectime.repository.OpcaoHorarioRepository;
import br.com.conectime.repository.ReuniaoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OpcaoHorarioService {

    private final OpcaoHorarioRepository opcaoHorarioRepository;
    private final ReuniaoRepository reuniaoRepository;

    public OpcaoHorarioService(
            OpcaoHorarioRepository opcaoHorarioRepository,
            ReuniaoRepository reuniaoRepository) {

        this.opcaoHorarioRepository = opcaoHorarioRepository;
        this.reuniaoRepository = reuniaoRepository;
    }

    // Cadastra uma opção de horário para uma reunião
    @Transactional
    public OpcaoHorario criar(
            Long idReuniao,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim) {

        if (dataHoraInicio == null || dataHoraFim == null) {
            throw new IllegalArgumentException(
                    "As datas e horários são obrigatórios."
            );
        }

        // RN02: o horário final deve ser posterior ao inicial
        if (!dataHoraFim.isAfter(dataHoraInicio)) {
            throw new IllegalArgumentException(
                    "O horário final deve ser posterior ao horário inicial."
            );
        }

        // Verifica se a reunião existe
        Reuniao reuniao = reuniaoRepository.findById(idReuniao)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Reunião não encontrada."
                        ));

        // Cria a opção de horário
        OpcaoHorario opcaoHorario = new OpcaoHorario();

        opcaoHorario.setReuniao(reuniao);
        opcaoHorario.setDataHoraInicio(dataHoraInicio);
        opcaoHorario.setDataHoraFim(dataHoraFim);

        return opcaoHorarioRepository.save(opcaoHorario);
    }

    // Lista as opções de horário de uma reunião
    @Transactional(readOnly = true)
    public List<OpcaoHorario> listarPorReuniao(Long idReuniao) {

        if (!reuniaoRepository.existsById(idReuniao)) {
            throw new IllegalArgumentException(
                    "Reunião não encontrada."
            );
        }

        return opcaoHorarioRepository
                .findByReuniao_IdReuniaoOrderByDataHoraInicioAsc(idReuniao);
    }

    // Busca uma opção de horário pelo ID
    @Transactional(readOnly = true)
    public OpcaoHorario buscarPorId(Long idHorario) {

        return opcaoHorarioRepository.findById(idHorario)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Opção de horário não encontrada."
                        ));
    }
}
