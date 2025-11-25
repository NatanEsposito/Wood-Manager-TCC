package br.edu.fatec.zl.WoodManager.util;

import java.util.regex.Pattern;

public class Util {
	public static String removerMascara(String valor) {
		if (valor != null) {
			return valor.replaceAll("\\D", ""); // Remove todos os caracteres não numéricos
		}
		return null;
	}

	// Regex simples para validação de e-mail
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

	public static boolean isEmail(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}
}