
package br.com.conectime.controller;

import br.com.conectime.model.Reuniao;
import br.com.conectime.model.Convidado;
import br.com.conectime.model.OpcaoHorario;

import br.com.conectime.service.ReuniaoService;

import jakarta.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reunioes")
public class ReuniaoController {

    private final ReuniaoService reuniaoService;

    public ReuniaoController(ReuniaoService reuniaoService) {
        this.reuniaoService = reuniaoService;
    }

    // Exibe o formulário de criação
    @GetMapping("/nova")
    public String exibirFormulario(HttpSession session) {

        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        return "reuniao/formulario";
    }

    // Cria uma nova reunião
    @PostMapping
    public String criar(
            @RequestParam String titulo,
            @RequestParam(required = false) String descricao,
            @RequestParam String formato,
            @RequestParam(required = false) String local,
            @RequestParam(required = false) String link,
            @RequestParam List<String> emails,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            List<LocalDateTime> dataHoraInicio,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            List<LocalDateTime> dataHoraFim,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        try {
            if (dataHoraInicio.size() != dataHoraFim.size()
                    || dataHoraInicio.isEmpty()) {
                throw new IllegalArgumentException(
                        "Informe pelo menos uma opção de horário completa."
                );
            }

            Reuniao reuniao = new Reuniao();

            reuniao.setTitulo(titulo);
            reuniao.setDescricao(descricao);
            reuniao.setFormato(formato);
            reuniao.setLocal(local);
            reuniao.setLink(link);

            // Adiciona os convidados
            for (String email : emails) {
                Convidado convidado = new Convidado();
                convidado.setEmail(email);

                reuniao.adicionarConvidado(convidado);
            }

            // Adiciona as opções de horário
            for (int i = 0; i < dataHoraInicio.size(); i++) {

                OpcaoHorario horario = new OpcaoHorario();

                horario.setDataHoraInicio(dataHoraInicio.get(i));
                horario.setDataHoraFim(dataHoraFim.get(i));

                reuniao.adicionarOpcaoHorario(horario);
            }

            reuniaoService.criar(reuniao, idUsuario);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Reunião criada com sucesso!"
            );

            return "redirect:/reunioes";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());

            return "reuniao/formulario";
        }
    }

    // Lista as reuniões do organizador
    @GetMapping
    public String listar(HttpSession session, Model model) {

        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        List<Reuniao> reunioes =
                reuniaoService.listarPorUsuario(idUsuario);

        model.addAttribute("reunioes", reunioes);

        return "reuniao/lista";
    }

    // Exibe os detalhes de uma reunião
    @GetMapping("/{id}")
    public String visualizar(
            @PathVariable Long id,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        try {
            Reuniao reuniao =
                    reuniaoService.buscarPorIdEUsuario(id, idUsuario);

            model.addAttribute("reuniao", reuniao);

            return "reuniao/detalhes";

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );

            return "redirect:/reunioes";
        }
    }

    // Exclui uma reunião
    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Long idUsuario = (Long) session.getAttribute("usuarioId");

        if (idUsuario == null) {
            return "redirect:/usuarios/login";
        }

        try {
            reuniaoService.excluir(id, idUsuario);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Reunião excluída com sucesso!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/reunioes";
    }
}
