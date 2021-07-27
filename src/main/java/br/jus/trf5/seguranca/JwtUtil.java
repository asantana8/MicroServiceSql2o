package br.jus.trf5.seguranca;

import java.util.Date;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.jus.trf5.dominio.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${jwt.secret}")
	private String segredo;

	@Value("${jwt.expiration}")
	private Long expiracao;

	// @Autowired
	// private UsuarioRepositorio usuarioRepositorio;

	public String retLoginFromToken(String token) {
		return retClaimDoToken(token, Claims::getSubject);
	}

	public <T> T retClaimDoToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = retTodosClaimsDoToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims retTodosClaimsDoToken(String token) {
		String token_ = token.contains("Bearer") ? token.substring(7, token.length()) : token;
		return Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token_).getBody();
	}

	public Date retDataEmissaoDoToken(String token) {
		return retClaimDoToken(token, Claims::getIssuedAt);
	}

	public Date retDataExpiracaoDoToken(String token) {
		return retClaimDoToken(token, Claims::getExpiration);
	}

	private Boolean ehTokenExpirado(String token) {
		final Date expiration = retDataExpiracaoDoToken(token);
		return expiration.before(new Date());
	}

	private Boolean foiCriadoAntesUltimaSenhaSerResetada(final Date created, final Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public String gerarToken(String login) {
		return Jwts.builder().setSubject(login).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiracao))
				// .setNotBefore(getHojeMenosUmDia())
				.signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();
	}

//	private Date getHojeMenosUmDia() {
//		Date dataHojeMenosUmDia = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(dataHojeMenosUmDia);
//		c.add(Calendar.DATE, -1);
//		return c.getTime();
//	}

	public Boolean validarToken(String token, UserDetails userDetails) {

		Usuario user = (Usuario) userDetails;
		final String login = retLoginFromToken(token);
		final String userlogin = login.substring(0, login.indexOf("_"));
		final String userSecao = login.substring(login.indexOf("_") + 1, login.length());
		final Date criadoem = retDataEmissaoDoToken(token);

		return (userlogin.equals(user.getLogin()) && Integer.parseInt(userSecao) == user.getcodbase()
				&& !ehTokenExpirado(token)
				&& !foiCriadoAntesUltimaSenhaSerResetada(criadoem, user.getUltima_atualizacao_token()));
	}

	public boolean tokenValido(String token) {
		Claims claims = retTodosClaimsDoToken(token);
		if (claims != null) {
			String login = claims.getSubject();
			Date dataExpiracao = claims.getExpiration();
			Date dataAtual = new Date(System.currentTimeMillis());
			if (login != null && dataExpiracao != null && dataAtual.before(dataExpiracao)) {
				return true;
			}
		}
		return false;
	}

	public boolean tokenValidoParaMetodo(String token) {
		boolean valido = false;
		try {
			Claims claims = retTodosClaimsDoToken(token);
			if (claims != null) {
				String login = claims.getSubject();
				Date dataExpiracao = claims.getExpiration();
				Date dataAtual = new Date(System.currentTimeMillis());
				if (login != null && dataExpiracao != null && dataAtual.before(dataExpiracao)) {
					valido = true;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return valido;
	}

	public String getLogin(String token) {
		Claims claims = retTodosClaimsDoToken(token);
		if (claims != null) {
			return claims.getSubject().substring(0, claims.getSubject().indexOf("_"));
		}
		return null;
	}

	public int getSecaoToken(String token) {
		String login = retLoginFromToken(token);
		String userSecao = login.substring(login.indexOf("_") + 1, login.length());
		return Integer.parseInt(userSecao);
	}

//	public void atualizarToken(String login, String token) {
//		final String loginTok = retLoginFromToken(token);
//		final String userSecao = loginTok.substring(loginTok.indexOf("_") + 1, loginTok.length());
//		usuarioRepositorio.atualizarToken(login, Integer.parseInt(userSecao), token);
//	}

}
