
package br.com.conectime.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "convidado",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_convidado_reuniao_email",
            columnNames = {"id_reuniao", "email"}
        )
    }
)
public class Convidado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convidado")
    private Long idConvidado;

    // Reunião à qual o convidado pertence
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reuniao", nullable = false)
    private Reuniao reuniao;

    @Column(length = 100)
    private String nome;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, unique = true, length = 36)
    private String token;

    @Column(
        name = "status_resposta",
        nullable = false,
        length = 20
    )
    private String statusResposta = "pendente";

    @Column(name = "respondido_em")
    private LocalDateTime respondidoEm;

    @Column(
        name = "convidado_em",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime convidadoEm;

    // Horários que o convidado marcou como disponíveis
    @OneToMany(
        mappedBy = "convidado",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Disponibilidade> disponibilidades = new ArrayList<>();

    public Convidado() {
    }

    public Long getIdConvidado() {
        return idConvidado;
    }

    public Reuniao getReuniao() {
        return reuniao;
    }

    public void setReuniao(Reuniao reuniao) {
        this.reuniao = reuniao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatusResposta() {
        return statusResposta;
    }

    public void setStatusResposta(String statusResposta) {
        this.statusResposta = statusResposta;
    }

    public LocalDateTime getRespondidoEm() {
        return respondidoEm;
    }

    public void setRespondidoEm(LocalDateTime respondidoEm) {
        this.respondidoEm = respondidoEm;
    }

    public LocalDateTime getConvidadoEm() {
        return convidadoEm;
    }

    public List<Disponibilidade> getDisponibilidades() {
        return disponibilidades;
    }

    // Mantém o relacionamento entre convidado e disponibilidade
    public void adicionarDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidades.add(disponibilidade);
        disponibilidade.setConvidado(this);
    }
}
