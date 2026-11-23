
package br.com.conectime.service;

import br.com.conectime.model.Reuniao;
import br.com.conectime.model.Usuario;
import br.com.conectime.model.Convidado;
import br.com.conectime.model.OpcaoHorario;

import br.com.conectime.repository.ReuniaoRepository;
import br.com.conectime.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
public class ReuniaoService {

    private final ReuniaoRepository reuniaoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReuniaoService(
            ReuniaoRepository reuniaoRepository,
            UsuarioRepository usuarioRepository) {

        this.reuniaoRepository = reuniaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Cria uma reunião
    @Transactional
    public Reuniao criar(Reuniao reuniao, Long idUsuario) {

        // RN01: a reunião precisa ter um título
        if (reuniao.getTitulo() == null ||
                reuniao.getTitulo().isBlank()) {
            throw new IllegalArgumentException(
                    "A reunião deve possuir um título."
            );
        }

        // RN03: a reunião precisa ter pelo menos um convidado
        if (reuniao.getConvidados() == null ||
                reuniao.getConvidados().isEmpty()) {
            throw new IllegalArgumentException(
                    "A reunião deve possuir pelo menos um convidado."
            );
        }

        // Busca o organizador responsável
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Usuário não encontrado."
                        ));

        reuniao.setUsuario(usuario);

        // Valida o formato da reunião
        validarFormato(reuniao);

        // Valida os convidados
        Set<String> emails = new HashSet<>();

        for (Convidado convidado : reuniao.getConvidados()) {

            if (convidado.getEmail() == null ||
                    convidado.getEmail().isBlank()) {
                throw new IllegalArgumentException(
                        "O e-mail do convidado é obrigatório."
                );
            }

            String email = convidado.getEmail()
                    .trim()
                    .toLowerCase(Locale.ROOT);

            if (!emails.add(email)) {
                throw new IllegalArgumentException(
                        "O mesmo e-mail não pode ser convidado duas vezes."
                );
            }

            convidado.setEmail(email);
            convidado.setToken(UUID.randomUUID().toString());
            convidado.setStatusResposta("pendente");
            convidado.setReuniao(reuniao);
        }

        // Valida as opções de horário
        for (OpcaoHorario horario : reuniao.getOpcoesHorarios()) {

            if (horario.getDataHoraInicio() == null ||
                    horario.getDataHoraFim() == null) {
                throw new IllegalArgumentException(
                        "O início e o fim do horário são obrigatórios."
                );
            }

            // RN02: o horário final deve ser posterior ao inicial
            if (!horario.getDataHoraFim()
                    .isAfter(horario.getDataHoraInicio())) {
                throw new IllegalArgumentException(
                        "O horário final deve ser posterior ao horário inicial."
                );
            }

            horario.setReuniao(reuniao);
        }

        reuniao.setStatus("aguardando");
        reuniao.setHorarioEscolhido(null);

        return reuniaoRepository.save(reuniao);
    }

    // Lista as reuniões pertencentes ao organizador
    @Transactional(readOnly = true)
    public List<Reuniao> listarPorUsuario(Long idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new IllegalArgumentException(
                    "Usuário não encontrado."
            );
        }

        return reuniaoRepository
                .findByUsuario_IdUsuarioOrderByCriadoEmDesc(idUsuario);
    }

    // Busca uma reunião garantindo que pertence ao organizador
    @Transactional(readOnly = true)
    public Reuniao buscarPorIdEUsuario(
            Long idReuniao,
            Long idUsuario) {

        return reuniaoRepository
                .findByIdReuniaoAndUsuario_IdUsuario(
                        idReuniao,
                        idUsuario
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Reunião não encontrada."
                        ));
    }

    // Exclui uma reunião
    @Transactional
    public void excluir(Long idReuniao, Long idUsuario) {

        Reuniao reuniao = buscarPorIdEUsuario(
                idReuniao,
                idUsuario
        );

        reuniaoRepository.delete(reuniao);
    }

    // Valida o formato da reunião
    private void validarFormato(Reuniao reuniao) {

        String formato = reuniao.getFormato();

        if (formato == null || formato.isBlank()) {
            throw new IllegalArgumentException(
                    "O formato da reunião é obrigatório."
            );
        }

        formato = formato.trim().toLowerCase(Locale.ROOT);
        reuniao.setFormato(formato);

        switch (formato) {

            case "presencial":
                if (reuniao.getLocal() == null ||
                        reuniao.getLocal().isBlank()) {
                    throw new IllegalArgumentException(
                            "Reuniões presenciais precisam de um local."
                    );
                }
                break;

            case "online":
                if (reuniao.getLink() == null ||
                        reuniao.getLink().isBlank()) {
                    throw new IllegalArgumentException(
                            "Reuniões online precisam de um link."
                    );
                }
                break;

            case "hibrido":
                if (reuniao.getLocal() == null ||
                        reuniao.getLocal().isBlank() ||
                        reuniao.getLink() == null ||
                        reuniao.getLink().isBlank()) {
                    throw new IllegalArgumentException(
                            "Reuniões híbridas precisam de local e link."
                    );
                }
                break;

            default:
                throw new IllegalArgumentException(
                        "Formato de reunião inválido."
                );
        }
    }
}
