package br.jus.trf5.configuracao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationValues {

	public static String connectionString1;
	public static String connectionString2;
	public static String connectionStringH2;

	public static String urlServiceAbreu;
	public static String prtUrlServiceAbreu;

	public static Boolean ipProducao;
	public static String ipProducaoValue;

	public static final String off = "SERVIÇO OFF-LINE";
	public static final String on = "SERVIÇO  ON-LINE";

	public static final String plogin = "99999999131";
	public static final String psenha = "$STI#Trf#Sist3ma$";
	public static final String hlogin = "02230612395";
	public static final String hsenha = "$TRF5sistemas";

	public static final String userDelphos = "delphos";
	public static final String userEsparta2 = "esparta2";
	public static final String Sucess = "success";

	public static final long SEGUNDO = 1000;
	public static final long MINUTO = SEGUNDO * 60;
	public static final String mensagemLogger = "PROCESSAMENTO DE ENVIO INICIADO ";
	
	@Value("${svc.ipProducao}")
	public void setIpProducao(String value) {
		ConfigurationValues.ipProducaoValue = value;
	}

	@Value("${spring.datasource.url}")
	public void setconnectionStringH2(String value) {
		ConfigurationValues.connectionStringH2 = value;
	}	
	
	@Value("${url.connectionString1}")
	public void setConnectionString1(String value) {
		ConfigurationValues.connectionString1 = value;
	}

	@Value("${url.connectionString2}")
	public void setConnectionString2(String value) {
		ConfigurationValues.connectionString2 = value;
	}

}
