package br.jus.trf5.configuracao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	// ERROS
	private final ResponseMessage m201 = simpleMessage(201, "Recurso criado");
	private final ResponseMessage m204put = simpleMessage(204, "Atualização ok");
	private final ResponseMessage m204del = simpleMessage(204, "Deleção ok");
	private final ResponseMessage m400 = simpleMessage(400, "Requisição inválida");
	private final ResponseMessage m401 = simpleMessage(401, " Não autorizado");
	private final ResponseMessage m403 = simpleMessage(403, "Acesso Negado");
	private final ResponseMessage m404 = simpleMessage(404, "Não encontrado");
	private final ResponseMessage m422 = simpleMessage(422, "Erro de validação");
	private final ResponseMessage m500 = simpleMessage(500, "Erro inesperado");

	// DOCUMENTAÇÃO
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.jus.trf5.recurso"))// Rastrear o Pacote
				//.paths(PathSelectors.any()).paths(Predicates.not(PathSelectors.regex("/certidaoweb.*?")))// Oculta				
				.build().apiInfo(metaInfo()).securitySchemes(Arrays.asList(apiKey()))
				.securityContexts(Collections.singletonList(securityContext())).groupName("All")

				// MENSAGENS
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m400, m401, m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m400, m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404, m500));
	}
	
	@Bean
	public Docket pesquisar() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("order").apiInfo(metaInfo()).select()
				.paths(PathSelectors.regex("/usuarios.*")).build().apiInfo(metaInfo())
				.securitySchemes(Arrays.asList(apiKey())).securityContexts(Collections.singletonList(securityContext()))
				.groupName("pesquisar")

				// MENSAGENS
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m400, m401, m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m400, m403, m404, m422, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404, m500));

	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().validatorUrl("").build();
	}

	// ERROS
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}

	// INFORMAÇÕES
	private ApiInfo metaInfo() {

		@SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo("Micro Serviço MPS",
				"Micro Serviço Template MPS.", "1.0", "Micro Serviço MPS",
				new Contact("Mps Informática", "https://www.mps.com.br/", "suportedelphos@mps.com.br"),
				"Apache License Version 2.0", "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>());

		return apiInfo;
	}

	// BOTÃO DA AUTENTICAÇÃO
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
	}

	private List<SecurityReference> defaultAuth() {
		final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[] { authorizationScope };
		return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
	}

	private ApiKey apiKey() {
		return new ApiKey("Bearer", "Authorization", "header");
	}

}
