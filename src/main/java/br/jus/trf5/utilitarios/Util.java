package br.jus.trf5.utilitarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	
	private Util() {
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String IpProducao   = "192.168.2.200";
	public static final String userH2  = "sa";		
			
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public Date converteDataHoraMni(String dateStr) {
		try {
			return dateTimeFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	public Date converteDataMni(String dateStr) {
		Date date = null;
		try {
			if (dateStr.lastIndexOf('/') > -1) {
				String[] arrayDate = dateStr.split("/");
				dateStr = arrayDate[2] + arrayDate[1] + arrayDate[0];
			} else if (dateStr.lastIndexOf('-') == -1 && dateStr.length() == 8) {
				dateStr = dateStr.substring(4, 8) + dateStr.substring(2, 4) + dateStr.substring(0, 2);
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

			date = format.parse(dateStr);
			return date;
		} catch (ParseException e) {
			return null;
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
		}
		return date;
	}

	public String formataDataHoraMni(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateTimeFormat.format(date);
		}
	}

	public String formataDataHoraMni(String date) {
		if (date == null) {
			return "";
		} else {
			return dateTimeFormat.format(date);
		}
	}

	public String formataDataMni(Date date) {
		if (date == null) {
			return "";
		} else {
			return dateFormat.format(date);
		}
	}
}
