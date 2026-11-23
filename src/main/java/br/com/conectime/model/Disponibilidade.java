
package br.com.conectime.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "disponibilidade",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_disponibilidade",
            columnNames = {"id_convidado", "id_horario"}
        )
    }
)
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidade")
    private Long idDisponibilidade;

    // Convidado que marcou o horário como disponível
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_convidado", nullable = false)
    private Convidado convidado;

    // Opção de horário selecionada
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_horario", nullable = false)
    private OpcaoHorario opcaoHorario;

    public Disponibilidade() {
    }

    public Long getIdDisponibilidade() {
        return idDisponibilidade;
    }

    public Convidado getConvidado() {
        return convidado;
    }

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
    }

    public OpcaoHorario getOpcaoHorario() {
        return opcaoHorario;
    }

    public void setOpcaoHorario(OpcaoHorario opcaoHorario) {
        this.opcaoHorario = opcaoHorario;
    }
}
