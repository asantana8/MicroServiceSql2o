package br.jus.trf5.seguranca;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.jus.trf5.servico.UsuarioServico;
import br.jus.trf5.servico.UsuarioServicoDetalhe;

public class JwtAutorizacaoFiltro extends OncePerRequestFilter {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.token-prefix}")
	private String tokenPrefix;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UsuarioServicoDetalhe usuarioServicoDetalhe;

	@Autowired
	private UsuarioServico usuarioService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String requestHeader = request.getHeader(this.tokenHeader);

		String login = null;
		String authToken = null;
		if (requestHeader != null && requestHeader.startsWith(tokenPrefix + " ")) {
			authToken = requestHeader.substring(7);
			login = jwtUtil.retLoginFromToken(authToken);
		} else {
			logger.warn("Não encontrou cadeia de suporte, ignorará o cabeçalho");
		}

		try {
			if (login != null && SecurityContextHolder.getContext().getAuthentication() == null
					&& usuarioService.estaLogado(authToken)) {

				UserDetails userDetails = usuarioServicoDetalhe.loadUserByUsername(login);

				if (jwtUtil.validarToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (NumberFormatException | UsernameNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}

		chain.doFilter(request, response);
	}

}
