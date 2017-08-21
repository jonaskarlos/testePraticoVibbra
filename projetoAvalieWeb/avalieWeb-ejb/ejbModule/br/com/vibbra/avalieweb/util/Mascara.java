package br.com.vibbra.avalieweb.util;

/**
 * Classe que contém as regras de aplicações de máscaras.
 * 
 * 
 * 
 */
public class Mascara {

	private Mascara() {

	}

	/**
	 * MÃ©todo com a regra para aplicaÃ§Ã£o de Mascara para CPF.
	 * 
	 * @param valor
	 *            a string que contï¿½m o cpf a ser mascarado.
	 * @return java.lang.String a String contendo o cpf mascarado.
	 */
	public static String aplicarMascaraCPF(String valor) {
		StringBuilder sb = new StringBuilder();
		sb.append(valor.substring(0, 3));
		sb.append(".");
		sb.append(valor.substring(3, 6));
		sb.append(".");
		sb.append(valor.substring(6, 9));
		sb.append("-");
		sb.append(valor.substring(9, 11));
		return sb.toString();
	}

	/**
	 * MÃ©todo com a regra para aplicaÃ§Ã£oo de Mascara para CNPJ.
	 * 
	 * @param valor
	 *            a string contendo o cnpj que serÃ¡ mascarado
	 * @return java.lang.String a string contendo o cnpj mascarado.
	 */
	public static String aplicarMascaraCNPJ(String valor) {
		StringBuilder sb = new StringBuilder();
		sb.append(valor.substring(0, 2));
		sb.append(".");
		sb.append(valor.substring(2, 5));
		sb.append(".");
		sb.append(valor.substring(5, 8));
		sb.append("/");
		sb.append(valor.substring(8, 12));
		sb.append("-");
		sb.append(valor.substring(12, 14));
		return sb.toString();
	}

	public static String aplicarMascaraMatricula(String valor) {
		StringBuilder sb = new StringBuilder();
		sb.append(valor.substring(0, valor.length()-1));
		sb.append("-");
		sb.append(valor.substring(valor.length()-1, valor.length()));
		return sb.toString();
	}

	/**
	 * Método que remove a máscara aplicada em uma matrícula de um servidor informada no parâmetro
	 *
	 * @param matriculaComMascara
	 * @return
	 *
	 * @author Jonas Santos - jonas.santos@seplag.ce.gov.br
	 * @since 29/09/2012
	 */
	public static String removerMascara(String matriculaComMascara){
		String matricula = "";
		if (!StringUtil.isEmpty(matriculaComMascara)){
			for (int i = 0; i < matriculaComMascara.length(); i++){
				if (Character.isLetterOrDigit(matriculaComMascara.charAt(i))){
					matricula += matriculaComMascara.charAt(i);
				}
			}
		}
		return matricula;
	}
	
	/**
	 * Método que retorna a parte da matrícula sem o dígito verificador
	 *
	 * @param matriculaSemMascara
	 * @return
	 * 
	 * @author Jonas Santos - jonas.santos@seplag.ce.gov.br
	 * @since 25/08/2014
	 */
	public static String removerDigitoVerificadorMatricula(String matriculaSemMascara){
		return (matriculaSemMascara.substring(0, matriculaSemMascara.length()-1));
	}
	
	/**
	 * Método que remove a máscara aplicada em um cpf
	 *
	 * @param cpfComMascara
	 * @return
	 *
	 * @author fernando.freitas
	 * @since 15/12/2014
	 */
	public static String removerMascaraCPF(String cpfComMascara) {
		String cpf = "";
		if (!StringUtil.isEmpty(cpfComMascara)){
			cpf = cpfComMascara.replace(".", "").replace("-", "").trim();
		}
		return cpf;
	}
	
	public static String removerMascaraCNPJ(String cnpjComMascara){
		String cnpj = "";
		if (!StringUtil.isEmpty(cnpjComMascara)){
			cnpj = cnpjComMascara.replace(".", "").replace("/", "").trim();
		}
		return cnpj;
	}

}
