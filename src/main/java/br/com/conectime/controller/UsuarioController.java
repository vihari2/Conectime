
package br.com.conectime.controller;

import br.com.conectime.model.Usuario;
import br.com.conectime.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Exibe a página de cadastro
    @GetMapping("/cadastro")
    public String exibirCadastro() {
        return "usuario/cadastro";
    }

    // Processa o cadastro do organizador
    @PostMapping("/cadastro")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            Model model,
            RedirectAttributes redirectAttributes) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        try {
            usuarioService.cadastrar(usuario);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Cadastro realizado com sucesso!"
            );

            return "redirect:/usuarios/login";

        } catch (IllegalArgumentException e) {

            model.addAttribute("erro", e.getMessage());
            return "usuario/cadastro";
        }
    }

    // Exibe a página de login
    @GetMapping("/login")
    public String exibirLogin() {
        return "usuario/login";
    }

    // Processa o login
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String senha,
            HttpSession session,
            Model model) {

        Optional<Usuario> usuario =
                usuarioService.autenticar(email, senha);

        if (usuario.isEmpty()) {
            model.addAttribute(
                    "erro",
                    "E-mail ou senha inválidos."
            );

            return "usuario/login";
        }

        session.setAttribute(
                "usuarioId",
                usuario.get().getIdUsuario()
        );

        session.setAttribute(
                "usuarioNome",
                usuario.get().getNome()
        );

        return "redirect:/reunioes";
    }

    // Encerra a sessão do organizador
    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/usuarios/login";
    }
}
