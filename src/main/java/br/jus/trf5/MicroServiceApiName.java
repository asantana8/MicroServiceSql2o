package br.jus.trf5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.jus.trf5.dominio.Usuario;
import br.jus.trf5.repositorio.RepositorioBase;
import br.jus.trf5.repositorio.UsuarioRepositorio;

//#RENOMEAR ESTA CLASSE CONFORME A FUNCIONALIDADE DO MICRO SERVIÇO

@SpringBootApplication
public class MicroServiceApiName implements CommandLineRunner {

	@Autowired
	RepositorioBase repositorio;

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceApiName.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UsuarioRepositorio usuRepositorio = repositorio.getUsuarioRepositorio(0);
		Usuario bean1 = new Usuario(0, "ACS1", "$2a$10$ao9wHC0jsLN0oetx11SlP.X2gnxzry3oUK7nq8s1jgRoIXmNI8i6y");
		usuRepositorio.salvarOrAtualizar(bean1);
		Usuario bean2 = new Usuario(0, "ACS2", "$2a$10$ao9wHC0jsLN0oetx11SlP.X2gnxzry3oUK7nq8s1jgRoIXmNI8i6y");
		usuRepositorio.salvarOrAtualizar(bean2);
		Usuario bean3 = new Usuario(0, "ACS3", "$2a$10$ao9wHC0jsLN0oetx11SlP.X2gnxzry3oUK7nq8s1jgRoIXmNI8i6y");
		usuRepositorio.salvarOrAtualizar(bean3);
	}

}
