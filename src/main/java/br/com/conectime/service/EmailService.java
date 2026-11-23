
package br.com.conectime.service;

import br.com.conectime.model.Convidado;
import br.com.conectime.model.Reuniao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Envia um convite por e-mail
    public void enviarConvite(Convidado convidado) {

        if (convidado.getEmail() == null ||
                convidado.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail do convidado é obrigatório."
            );
        }

        if (convidado.getToken() == null ||
                convidado.getToken().isBlank()) {
            throw new IllegalArgumentException(
                    "O convidado não possui um token válido."
            );
        }

        Reuniao reuniao = convidado.getReuniao();

        String linkConvite = baseUrl.replaceAll("/+$", "")
                + "/convites/"
                + convidado.getToken();

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom(remetente);
        mensagem.setTo(convidado.getEmail());

        mensagem.setSubject(
                "Você foi convidado para uma reunião: "
                        + reuniao.getTitulo()
        );

        mensagem.setText(
                "Olá!\n\n" +
                "Você recebeu um convite para participar de uma reunião " +
                "pelo Conectime.\n\n" +

                "Reunião: " + reuniao.getTitulo() + "\n\n" +

                "Para informar sua disponibilidade, acesse o link exclusivo:\n\n" +

                linkConvite + "\n\n" +

                "Você não precisa criar uma conta para responder.\n\n" +

                "Atenciosamente,\n" +
                "Equipe Conectime"
        );

        mailSender.send(mensagem);
    }
}
