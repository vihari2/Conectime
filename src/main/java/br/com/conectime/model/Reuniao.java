
package br.com.conectime.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reuniao")
public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reuniao")
    private Long idReuniao;

    // Organizador responsável pela reunião
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false, length = 20)
    private String formato;

    @Column(length = 150)
    private String local;

    @Column(length = 255)
    private String link;

    @Column(nullable = false)
    private String status = "aguardando";

    // Horário escolhido pelo organizador após as respostas
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario_escolhido")
    private OpcaoHorario horarioEscolhido;

    @Column(
        name = "criado_em",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private LocalDateTime criadoEm;

    // Convidados relacionados à reunião
    @OneToMany(
        mappedBy = "reuniao",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Convidado> convidados = new ArrayList<>();

    // Opções de horários da reunião
    @OneToMany(
        mappedBy = "reuniao",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<OpcaoHorario> opcoesHorarios = new ArrayList<>();

    public Reuniao() {
    }

    public Long getIdReuniao() {
        return idReuniao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OpcaoHorario getHorarioEscolhido() {
        return horarioEscolhido;
    }

    public void setHorarioEscolhido(OpcaoHorario horarioEscolhido) {
        this.horarioEscolhido = horarioEscolhido;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public List<Convidado> getConvidados() {
        return convidados;
    }

    public List<OpcaoHorario> getOpcoesHorarios() {
        return opcoesHorarios;
    }

    // Mantém o relacionamento entre reunião e convidados
    public void adicionarConvidado(Convidado convidado) {
        convidados.add(convidado);
        convidado.setReuniao(this);
    }

    // Mantém o relacionamento entre reunião e opções de horário
    public void adicionarOpcaoHorario(OpcaoHorario opcaoHorario) {
        opcoesHorarios.add(opcaoHorario);
        opcaoHorario.setReuniao(this);
    }
}
