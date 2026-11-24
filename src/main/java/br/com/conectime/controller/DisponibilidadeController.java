package br.com.conectime.controller;

import br.com.conectime.model.Disponibilidade;
import br.com.conectime.model.OpcaoHorario;
import br.com.conectime.model.Reuniao;
import br.com.conectime.service.DisponibilidadeService;
import br.com.conectime.service.OpcaoHorarioService;
import br.com.conectime.service.ReuniaoService;
import jakarta.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reunioes/{idReuniao}/disponibilidades")
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;
    private final OpcaoHorarioService opcaoHorarioService;
    private final ReuniaoService reuniaoService;

    public DisponibilidadeController(
        DisponibilidadeService disponibilidadeService,
        OpcaoHorarioService opcaoHorarioService,
        ReuniaoService reuniaoService
    ) {
        this.disponibilidadeService = disponibilidadeService;
        this.opcaoHorarioService = opcaoHorarioService;
        this.reuniaoService = reuniaoService;
    }

    // Exibe as disponibilidades dos convidados
    @GetMapping
    public String listar(
        @PathVariable Long idReuniao,
        HttpSession session,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        try {
            // Verifica se a reunião pertence ao organizador
            Reuniao reuniao = reuniaoService.buscarPorIdEUsuario(idReuniao, idUsuario);

            // Busca os horários da reunião
            List<OpcaoHorario> horarios = opcaoHorarioService.listarPorReuniao(idReuniao);

            // Organiza as disponibilidades por horário
            Map<Long, List<Disponibilidade>> disponibilidadesPorHorario = new LinkedHashMap<>();

            Map<Long, Long> totalPorHorario = new LinkedHashMap<>();

            for (OpcaoHorario horario : horarios) {
                List<Disponibilidade> disponibilidades = disponibilidadeService.listarPorHorario(
                    horario.getIdHorario()
                );

                disponibilidadesPorHorario.put(horario.getIdHorario(), disponibilidades);

                totalPorHorario.put(horario.getIdHorario(), (long) disponibilidades.size());
            }

            long respondidos = reuniao
                .getConvidados()
                .stream()
                .filter(c -> "respondido".equals(c.getStatusResposta()))
                .count();

            long pendentes = reuniao.getConvidados().size() - respondidos;

            model.addAttribute("respondidos", respondidos);
            model.addAttribute("pendentes", pendentes);

            model.addAttribute("reuniao", reuniao);
            model.addAttribute("horarios", horarios);

            model.addAttribute("disponibilidadesPorHorario", disponibilidadesPorHorario);

            model.addAttribute("totalPorHorario", totalPorHorario);

            return "reuniao/disponibilidades";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());

            return "redirect:/reunioes";
        }
    }

    // Recebe a opção escolhida pelo organizador
    @PostMapping("/confirmar")
    public String confirmar(
        @PathVariable Long idReuniao,
        @RequestParam Long idHorario,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        try {
            reuniaoService.confirmarHorario(idReuniao, idUsuario, idHorario);

            redirectAttributes.addFlashAttribute("sucesso", "Horário confirmado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/reunioes/" + idReuniao + "/disponibilidades";
    }
}
