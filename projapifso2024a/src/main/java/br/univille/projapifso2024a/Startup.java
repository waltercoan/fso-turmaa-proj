package br.univille.projapifso2024a;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import br.univille.projapifso2024a.entity.Cliente;
import br.univille.projapifso2024a.entity.Usuario;
import br.univille.projapifso2024a.service.ClienteService;
import br.univille.projapifso2024a.service.UsuarioService;

@Component
public class Startup {
    @Autowired
    private ClienteService service;

    @Autowired
    private UsuarioService serviceUsuario;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        var cliente1 = new Cliente();
        cliente1.setNome("Zezinho");
        cliente1.setEndereco("Rua lalalalla 100");
        cliente1.setDataNascimento(new Date(2024,04,17));
        service.save(cliente1);

        var usuario = new Usuario();
        usuario.setUsuario("admin");
        usuario.setSenha("admin");
        serviceUsuario.save(usuario);
    }
}
