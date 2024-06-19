package br.univille.projfso2024a;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.univille.projfso2024a.entity.Agendamento;
import br.univille.projfso2024a.entity.Cliente;
import br.univille.projfso2024a.entity.Produto;
import br.univille.projfso2024a.repository.AgendamentoRepository;
import br.univille.projfso2024a.service.AgendamentoService;
import br.univille.projfso2024a.service.ClienteService;
import br.univille.projfso2024a.service.ProdutoService;

@Component
public class Startup {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private AgendamentoService agendamentoService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        var cliente1 = new Cliente();
        cliente1.setNome("Zezinho");
        cliente1.setEndereco("Rua lalalalla 100");
        cliente1.setDataNascimento(new Date(2024,04,17));
        clienteService.save(cliente1);

        var prod1 = new Produto();
        prod1.setDescricao("Nintendo Switch");
        prod1.setValor(2000f);
        produtoService.save(prod1);

        var prod2 = new Produto();
        prod2.setDescricao("XBOX Series X");
        prod2.setValor(4500f);
        produtoService.save(prod2);

        var agendamento = new Agendamento();
        agendamento.setData(new Date());
        agendamento.setHora(new Date());
        agendamento.setDescricao("Zezinho");
        agendamentoService.save(agendamento);
        
        agendamento = new Agendamento();
        agendamento.setData(new Date());
        agendamento.setHora(new Date());
        agendamento.setDescricao("Mariazinha");
        agendamentoService.save(agendamento);
    }
}
