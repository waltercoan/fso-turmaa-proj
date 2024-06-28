package br.univille.projapifso2024a.service;

import java.util.Optional;
import br.univille.projapifso2024a.entity.Usuario;

public interface UsuarioService {
    void save(Usuario usuario);
    Optional<Usuario> findByUsuario(String usuario);
}
