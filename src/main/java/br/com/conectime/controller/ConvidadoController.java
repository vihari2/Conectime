
package br.com.conectime.controller;

import br.com.conectime.model.Convidado;
import br.com.conectime.model.OpcaoHorario;

import br.com.conectime.service.ConvidadoService;
import br.com.conectime.service.OpcaoHorarioService;
import br.com.conectime.service.DisponibilidadeService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/convites")
public class ConvidadoController {

    private final ConvidadoService convidadoService;
    private final OpcaoHorarioService opcaoHorarioService;
    private final DisponibilidadeService disponibilidadeService;

    public ConvidadoController(
            ConvidadoService convidadoService,
            OpcaoHorarioService opcaoHorarioService,
            DisponibilidadeService disponibilidadeService) {

        this.convidadoService = convidadoService;
        this.opcaoHorarioService = opcaoHorarioService;
        this.disponibilidadeService = disponibilidadeService;
    }

    // Exibe o convite utilizando o token exclusivo
    @GetMapping("/{token}")
    public String visualizarConvite(
            @PathVariable String token,
            Model model) {

        try {
            Convidado convidado =
                    convidadoService.buscarPorToken(token);

            Long idReuniao =
                    convidado.getReuniao().getIdReuniao();

            List<OpcaoHorario> horarios =
                    opcaoHorarioService.listarPorReuniao(idReuniao);

            model.addAttribute("convidado", convidado);
            model.addAttribute("reuniao", convidado.getReuniao());
            model.addAttribute("horarios", horarios);

            return "convidado/convite";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());
            return "convidado/convite-invalido";
        }
    }

    // Recebe os horários selecionados pelo convidado
    @PostMapping("/{token}/responder")
    public String responderConvite(
            @PathVariable String token,
            @RequestParam(name = "idsHorarios", required = false)
            List<Long> idsHorarios,
            RedirectAttributes redirectAttributes) {

        try {
            // Permite enviar uma resposta sem horários selecionados
            if (idsHorarios == null) {
                idsHorarios = List.of();
            }

            disponibilidadeService.registrarDisponibilidades(
                    token,
                    idsHorarios
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Sua disponibilidade foi registrada!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/convites/" + token;
    }
}
