package br.jus.trf5.recurso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.jus.trf5.dominio.Usuario;
import br.jus.trf5.dominio.dto.LoginDTO;
import br.jus.trf5.servico.UsuarioServico;
import io.swagger.annotations.ApiModelProperty;

@RestController
@ResponseBody
@RequestMapping(value = "/usuarios")
public class UsuarioRecurso {

	@Autowired
	UsuarioServico servico;
	
	@PostMapping(value = "/login") //Devolve um token
	@ApiModelProperty(notes = "Informe login, seção e senha e recebe de volta um token")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) throws Exception {
		Usuario Usuariodto = servico.loginComTokenPorDTO(loginDto);
		return ResponseEntity.ok().body(Usuariodto.getToken());
	}

	@GetMapping(value = "/usuarioPorToken")//Valida um token
	public ResponseEntity<Usuario> buscaPorToken(@RequestHeader("Authorization") String token) throws Exception {
		return ResponseEntity.ok().body(servico.buscaPorToken(token));
	}
	
}
