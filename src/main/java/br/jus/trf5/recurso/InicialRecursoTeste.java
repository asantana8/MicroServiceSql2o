package br.jus.trf5.recurso;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class InicialRecursoTeste {

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String welcome() {
		return "Serviço no ar!";
	}

}
