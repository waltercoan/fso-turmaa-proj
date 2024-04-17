package br.univille.projapifso2024a;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import br.univille.projapifso2024a.service.ClienteService;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteServiceTests {

    @Autowired
    private ClienteService service;

    @Test
    void getAllClientesEmptyList(){
        var listaClientes = service.getAll();
        assertEquals(listaClientes.size(), 0);
    }

}
