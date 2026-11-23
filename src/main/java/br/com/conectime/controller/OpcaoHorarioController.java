package br.com.conectime.controller;

import br.com.conectime.model.Reuniao;
import br.com.conectime.model.OpcaoHorario;

import br.com.conectime.service.ReuniaoService;
import br.com.conectime.service.OpcaoHorarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reunioes/{idReuniao}/horarios")
public class OpcaoHorarioController {

    private final OpcaoHorarioService opcaoHorarioService;
    private final ReuniaoService reuniaoService;

    public OpcaoHorarioController(
            OpcaoHorarioService opcaoHorarioService,
            ReuniaoService reuniaoService) {

        this.opcaoHorarioService = opcaoHorarioService;
        this.reuniaoService = reuniaoService;
    }

    // Lista os horários de uma reunião
    @GetMapping
    public String listar(
            @PathVariable Long idReuniao,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        try {
            Reuniao reuniao =
                    reuniaoService.buscarPorIdEUsuario(
                            idReuniao,
                            idUsuario
                    );

            List<OpcaoHorario> horarios =
                    opcaoHorarioService.listarPorReuniao(idReuniao);

            model.addAttribute("reuniao", reuniao);
            model.addAttribute("horarios", horarios);

            return "reuniao/horarios";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/reunioes";
        }
    }

    // Cadastra uma nova opção de horário
    @PostMapping
    public String criar(
            @PathVariable Long idReuniao,

            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime dataHoraInicio,

            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
            LocalDateTime dataHoraFim,

            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        try {
            // Confirma que a reunião pertence ao organizador
            reuniaoService.buscarPorIdEUsuario(
                    idReuniao,
                    idUsuario
            );

            opcaoHorarioService.criar(
                    idReuniao,
                    dataHoraInicio,
                    dataHoraFim
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Horário cadastrado com sucesso!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/reunioes/" + idReuniao + "/horarios";
    }
}
