package br.jus.trf5.repositorio;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import br.jus.trf5.configuracao.ConfigurationValues;
import br.jus.trf5.utilitarios.Util;

@Repository
public class RepositorioBase {

	// ----------------------------------------------------------------------------------------------------------------
	// CADA NOVO REPOSITÓRIO DEVERÁ SER INCLUIDO
	// AQUI PARA SER INSTANCIADO NA BASE CORRETA.
	public Sql2o connection;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	// ----------------------------------------------------------------------------------------------------------------

	public static final RepositorioBase INSTANCE = new RepositorioBase();

	public RepositorioBase() {
		super();
	}

	// #MÉTODOS DE ACESSO MULTIBANCO RESOLVIDO AQUI //
	private Sql2o getDadosConnection(int value) throws SQLException, UnknownHostException {

		/*
		 * CÓDIGO DA BASE DE DADOS, PODERIA DEFINIR UM ENUM, MAS DEIXEI NÚMERO MESMO
		 * 0=H2; 1=ORACLE; 2=POSTGRESQL
		 * SE NÃO FOR IP DE PRODUÇÃO, USAR RECURSOS DE DESENVOLVIMENTO/HOMOLOGAÇÃO
		 * */
		if (!ConfigurationValues.ipProducaoValue.equals(InetAddress.getLocalHost().getHostAddress())) {
			switch (value) {
			case 0: // BASE H2
				return new Sql2o(ConfigurationValues.connectionStringH2, Util.userH2, "");
			case 1: // BASE ORACLE
				return new Sql2o("jdbc:oracle:thin:@192.168.0.10:1521:BANCO1", Util.userH2, "123");
			case 2: // BASE POSTGREESQL
				return new Sql2o("jdbc:oracle:thin:@192.168.0.20:1521:BANCO2", Util.userH2, "123");
			default:
				throw new SQLException("Connection Invalid Return (Verifique o acesso ao SGBD)");
			}
		}

		// TRECHO SÓ É EXECUTA EM PRODUÇÃO
		switch (value) {
		case 0: // BASE H2 
			return new Sql2o(ConfigurationValues.connectionStringH2, Util.userH2, "");
		case 1: // BASE ORACLE 
			return new Sql2o("jdbc:oracle:thin:@192.168.0.10:1521:BANCO1", Util.userH2, "");
		case 2: // BASE POSTGREESQL 
			return new Sql2o("jdbc:postgreesql:thin:@192.168.0.20:1521:BANCO2", Util.userH2, "");
		default:
			throw new SQLException("Connection Invalid Return (Verifique o acesso ao SGBD)");
		}
	}
	// ----------------------------------------------------------------------------------------------------------------------------

	// # ÁREA DE DECLARAÇÃO DOS MÉTODOS DE INSTANCIAÇÃO DAS CLASSES DE REPOSITORIO
	// ----------------------------------------------------
	public UsuarioRepositorio getUsuarioRepositorio(int value) throws SQLException, UnknownHostException {
		usuarioRepositorio.setConnection(getDadosConnection(value));
		return usuarioRepositorio;
	}
	// #FIM
	// ----------------------------------------------------------------------------------------------------------------------------
}
