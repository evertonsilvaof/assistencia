package com.assistencia;

import com.assistencia.entity.*;
import com.assistencia.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrdemServicoControllerTest {
    @Autowired MockMvc mvc;
    @Autowired ClienteRepository clientes;
    @Autowired EquipamentoRepository equipamentos;
    @Autowired OrdemServicoRepository ordens;
    @Autowired HistoricoStatusRepository historicos;
    ClienteEntity cliente;
    EquipamentoEntity equipamento;

    @BeforeEach void preparar() {
        historicos.deleteAll(); ordens.deleteAll(); equipamentos.deleteAll(); clientes.deleteAll();
        cliente = new ClienteEntity(); cliente.setNome("Cliente Teste"); cliente.setAtivo(true);
        cliente = clientes.save(cliente);
        equipamento = new EquipamentoEntity(); equipamento.setDescricao("Notebook"); equipamento.setCliente(cliente);
        equipamento = equipamentos.save(equipamento);
    }

    @Test void deveAbrirOrdemComStatusRecebidaEHistorico() throws Exception {
        mvc.perform(post("/ordens").contentType(MediaType.APPLICATION_JSON)
            .content("{\"clienteId\":" + cliente.getId() + ",\"equipamentoId\":" + equipamento.getId() + ",\"descricaoProblema\":\"Notebook não liga\"}"))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.numeroProtocolo").value(org.hamcrest.Matchers.matchesRegex("OS-\\d{8}-[A-F0-9]{4}")))
            .andExpect(jsonPath("$.status").value("RECEBIDA")).andExpect(jsonPath("$.dataAbertura").exists());
        org.junit.jupiter.api.Assertions.assertEquals(1, historicos.count());
    }

    @Test void deveRetornar400ParaCamposInvalidos() throws Exception {
        mvc.perform(post("/ordens").contentType(MediaType.APPLICATION_JSON)
            .content("{\"clienteId\":null,\"equipamentoId\":null,\"descricaoProblema\":\" \"}"))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos").exists());
    }

    @Test void deveRetornar404ParaClienteInexistente() throws Exception {
        mvc.perform(post("/ordens").contentType(MediaType.APPLICATION_JSON)
            .content("{\"clienteId\":99999,\"equipamentoId\":" + equipamento.getId() + ",\"descricaoProblema\":\"Falha\"}"))
            .andExpect(status().isNotFound()).andExpect(jsonPath("$.detail").value("Cliente não encontrado."));
    }

    @Test void deveRetornar422ParaClienteInativo() throws Exception {
        cliente.setAtivo(false); clientes.save(cliente);
        mvc.perform(post("/ordens").contentType(MediaType.APPLICATION_JSON)
            .content("{\"clienteId\":" + cliente.getId() + ",\"equipamentoId\":" + equipamento.getId() + ",\"descricaoProblema\":\"Falha\"}"))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test void deveRetornar422ParaEquipamentoDeOutroCliente() throws Exception {
        ClienteEntity outro = new ClienteEntity(); outro.setNome("Outro"); outro.setAtivo(true); outro = clientes.save(outro);
        EquipamentoEntity eqOutro = new EquipamentoEntity(); eqOutro.setDescricao("Celular"); eqOutro.setCliente(outro); eqOutro = equipamentos.save(eqOutro);
        mvc.perform(post("/ordens").contentType(MediaType.APPLICATION_JSON)
            .content("{\"clienteId\":" + cliente.getId() + ",\"equipamentoId\":" + eqOutro.getId() + ",\"descricaoProblema\":\"Falha\"}"))
            .andExpect(status().isUnprocessableEntity());
    }
}
