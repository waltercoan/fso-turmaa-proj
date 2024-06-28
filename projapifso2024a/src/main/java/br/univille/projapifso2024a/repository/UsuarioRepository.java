package br.univille.projapifso2024a.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.univille.projapifso2024a.entity.Usuario;

@Repository
public interface UsuarioRepository 
    extends JpaRepository<Usuario,Long>{
        /*select * from usuario wher usuario.usuario = 'zezinho' */
        Optional<Usuario> findByUsuario(String usuario);
    
}
