package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();
        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", ""));
    }

    @Test
    @DisplayName("Cadastrar Um Usuário")
    public void deveCriarUmUsuario() {
        Usuario corpoRequisicao = new Usuario(0L, "Paulo Antunes", "paulo_antunes@email.com.br", "13465278", "");
        HttpEntity<Usuario> request = new HttpEntity<>(corpoRequisicao);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
                "/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {
        usuarioService.cadastrarUsuario(new Usuario(0L, "Maria da Silva", "maria@email.com", "12345678", ""));

        Usuario corpoRequisicao = new Usuario(0L, "Maria da Silva", "maria@email.com", "12345678", "");
        HttpEntity<Usuario> request = new HttpEntity<>(corpoRequisicao);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
                "/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario() {
        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(
                new Usuario(0L, "Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", ""));

        assertTrue(usuarioCadastrado.isPresent(), "Usuário não foi cadastrado corretamente!");
        
        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Juliana Andrews Ramos",
                "juliana_ramos@email.com.br", "juliana123", "");
        HttpEntity<Usuario> request = new HttpEntity<>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
                "/usuarios/atualizar", HttpMethod.PUT, request, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosUsuarios() {
        usuarioService.cadastrarUsuario(new Usuario(0L, "Sabrina Sanches", "sabrina_sanches@email.com", "sabrina123", ""));
        usuarioService.cadastrarUsuario(new Usuario(0L, "Ricardo Marques", "ricardo@email.com", "ricardo123", ""));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}
