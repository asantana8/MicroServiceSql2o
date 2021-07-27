package br.jus.trf5.seguranca.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.jus.trf5.seguranca.Constantes;
import br.jus.trf5.seguranca.JwtAutorizacaoFiltro;
import br.jus.trf5.servico.UsuarioServicoDetalhe;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioServicoDetalhe usuarioServicoDetalhe;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedOrigins(Constantes.domainAtual)
						.allowedHeaders("Authorization", "Cache-Control", "Content-Type").allowCredentials(true)
						.exposedHeaders("Authorization", "Cache-Control");
			}
		};
	}

	private static final String[] PUBLIC_MATCHERS_POST = {
			"/usuarios",
			"/usuarios/login/**"
			//"/usuarios/usuarioPorToken/**"
	};

	private static final String[] PUBLIC_MATCHERS_GET = {
			"/help",
			"/usuarios",
			"/usuarios/login/**"
			//"/usuarios/usuarioPorToken/**"
	};
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/v2/api-docs", 
				"/configuration/ui", 
				"/swagger-resources/**",
				"/configuration/security", 
				"/configuration/**", 
				"/swagger-ui.html", 
				"/webjars/**",
				"/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(authenticationTokenFilterBean(),
				org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioServicoDetalhe).passwordEncoder(encodificadorDeSenha());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuracao = new CorsConfiguration().applyPermitDefaultValues();
		configuracao.setAllowedOrigins(Arrays.asList(Constantes.domainAtual));
		configuracao.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuracao);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder encodificadorDeSenha() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAutorizacaoFiltro authenticationTokenFilterBean() throws Exception {
		return new JwtAutorizacaoFiltro();
	}

}
