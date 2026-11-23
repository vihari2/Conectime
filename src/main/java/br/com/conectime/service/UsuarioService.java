
package br.com.conectime.service;

import br.com.conectime.model.Usuario;
import br.com.conectime.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario cadastrar(Usuario usuario) {

        if (usuario.getEmail() == null ||
                usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail é obrigatório."
            );
        }

        usuario.setEmail(
                usuario.getEmail().trim().toLowerCase(Locale.ROOT)
        );

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado."
            );
        }

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(
                email.trim().toLowerCase(Locale.ROOT)
        );
    }

        // Autentica o usuário
        @Transactional(readOnly = true)
        public Optional<Usuario> autenticar(String email, String senha) {

        if (email == null || email.isBlank()
                || senha == null || senha.isBlank()) {
                return Optional.empty();
        }

        return buscarPorEmail(email)
                .filter(usuario ->
                        passwordEncoder.matches(senha, usuario.getSenha())
                );
        }
}