
package br.com.conectime.service;

import br.com.conectime.model.Convidado;
import br.com.conectime.repository.ConvidadoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConvidadoService {

    private final ConvidadoRepository convidadoRepository;

    public ConvidadoService(ConvidadoRepository convidadoRepository) {
        this.convidadoRepository = convidadoRepository;
    }

    // Busca um convidado pelo token exclusivo
    @Transactional(readOnly = true)
    public Convidado buscarPorToken(String token) {

        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException(
                    "O token é obrigatório."
            );
        }

        return convidadoRepository.findByToken(token.trim())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Convite não encontrado ou inválido."
                        ));
    }

    // Lista os convidados de uma reunião
    @Transactional(readOnly = true)
    public List<Convidado> listarPorReuniao(Long idReuniao) {

        return convidadoRepository
                .findByReuniao_IdReuniao(idReuniao);
    }

    // Atualiza o status quando o convidado envia sua resposta
    @Transactional
    public Convidado marcarComoRespondido(Long idConvidado) {

        Convidado convidado = convidadoRepository.findById(idConvidado)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Convidado não encontrado."
                        ));

        convidado.setStatusResposta("respondido");
        convidado.setRespondidoEm(LocalDateTime.now());

        return convidadoRepository.save(convidado);
    }
}
