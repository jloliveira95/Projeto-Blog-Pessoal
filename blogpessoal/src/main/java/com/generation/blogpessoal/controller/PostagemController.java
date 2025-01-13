package com.generation.blogpessoal.controller;

import java.util.List;

import org. springframework. beans. factory. annotation. Autowired;
import org.springframework.http.HttpStatus;
import org. springframework.http. ResponseEntity;
import org.springframework.web.bind. annotation. CrossOrigin;
import org. springframework.web. bind. annotation. GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org. springframework.web. bind. annotation. RequestMapping;
import org.springframework.web.bind. annotation. RestController;
import com.generation.blogpessoal.model. Postagem;
import com. generation. blogpessoal. repository . PostagemRepository;

@RestController											// Define ao Spring essa Classe é uma Controller
@RequestMapping("/postagens")							// Define vai ser tratado pgt essa Classe
@CrossOrigin(origins = "*", allowedHeaders = "*")		// Libera o acesso a qualquer front
public class PostagemController {


	@Autowired											// O Spring dá autonomia para a interface poder invocar os métodos
	private PostagemRepository postagemRepository;
		
	@GetMapping 										// indica que este método é chamado em Verbos/Métodos HTTP do tipo Get
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll()); // SELECT * FROM tb_postagens
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById (@PathVariable Long id) {
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); 
		
	}
	
	
	
	
}