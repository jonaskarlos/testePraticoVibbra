package br.com.vibbra.avalieweb.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.MaskFormatter;

import antlr.StringUtils;

public class StringUtil extends StringUtils {

	public static String formatarValorMonetario(BigDecimal valor){
		NumberFormat nf;
		nf = new DecimalFormat("R$ ###,##0.00");
		return nf.format(valor);
	}

	public static String lpad(String valueToPad, String filler, int size) {
		while (valueToPad.length() < size) {
			valueToPad = filler + valueToPad;
		}
		return valueToPad;
	}

	public static String rpad(String valueToPad, String filler, int size) {
		StringBuffer result = new StringBuffer(valueToPad);
		while (result.length() < size) {
			result.append(filler);
		}
		return result.toString();
	}
	
	/**
	 * Verifica se a String é realmente vazia e diferente de nula
	 * 
	 * @param str
	 * 		String informada
	 * @return
	 *
	 * @author Jonas Santos - jonas.santos@seplag.ce.gov.br
	 * @date 19/07/2012
	 */
	public static boolean isEmpty(String str){
		return (str == null || str.trim().equals(""));		
	}
	
	/**
	 * Verifica se o parâmetro informado possui algum valor diferente de zero 
	 *
	 * @param valor
	 * @return
	 * 
	 * @author Jonas Santos - jonas.santos@seplag.ce.gov.br
	 * @since 13/05/2013
	 */
	public static boolean possuiValor(BigDecimal valor){
		return (valor != null && (valor.compareTo(BigDecimal.ZERO)) > 0);		
	}
	
	public static String preencheZerosEsquerda(String num, int length) {
		String result = (num == null) ? "": num.trim();
		while (result.length() < length) {
			result = "0" + result;
		}
		return result;
	}
	
	/**
	 * Formata um valor real com duas casas decimais para o formato do Brasil (Ex: "009.999.999,99")
	 *
	 * @param floatValue    Valor no formato brasileiro que será formatado
	 */		
	public static String formatFloat(String floatValue) {
		String valueAux, inteiro, decimal;
		int i, tam;

		valueAux = cleanNumericField(floatValue);
		tam = valueAux.length();

		inteiro = valueAux.substring(0, tam - 2);
		decimal = valueAux.substring(tam - 2);
		
		tam = inteiro.length();
		for (i = tam - 3; i > 0; i -= 3) {
			inteiro = inteiro.substring(0, i) + '.' + inteiro.substring(i);
		}
		floatValue = inteiro + ',' + decimal;
		
		return floatValue;
	}	
	
	/**
	 * Retorna somente os digitos de uma palavra
	 * Ex: "1.234,56" -> "123456"
	 * @param number	String que terá somente os dígitos retornados
	 * 
	 * @return 			String somente com digitos
	 */		
	public static String cleanNumericField(String number) {
		StringBuffer cleanNumber = new StringBuffer();
		if (number != null) {
			for (int i = 0; i < number.length(); i++) {
				if (new String("0123456789").indexOf(number.charAt(i)) >= 0) {
					cleanNumber.append(number.charAt(i));
				}
			}
		}
		return cleanNumber.toString();
	}

	public static Date processaDataOpcao(Date pDataOpcao, Date pDataAdmissao,
			String pCategoria) {

		Date dataOpcao = pDataOpcao;
		Calendar dataLimiteOpcaoTemp = Calendar.getInstance();
		dataLimiteOpcaoTemp.set(1988, 10, 5); // yyyy/mm/dd
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		Date dataLimiteOpcao;
		try {
			dataLimiteOpcao = simpleDateFormat.parse(dataLimiteOpcaoTemp
					.get(Calendar.DATE)	+ "/" + dataLimiteOpcaoTemp.get(Calendar.MONTH)	+ "/" + dataLimiteOpcaoTemp.get(Calendar.YEAR));

			//Deve ser maior ou igual a data de admissão e limitada a 05/10/1988 quando a data de admissão for menor que 05/10/1988, para as categorias 1 e 3.
			if(pCategoria.equals("01") || pCategoria.equals("03")){
				if(pDataAdmissao.compareTo(dataLimiteOpcao) <= 0){
					if(dataOpcao == null)
					  dataOpcao = pDataAdmissao;
				}
				//Deve ser igual a data de admissao quando a mesma for maior ou igual a 05/10/1988
				if(pDataAdmissao.compareTo(dataLimiteOpcao) >= 0){
					dataOpcao = pDataAdmissao;
				}
			}

/*			if ((pCategoria.equals("01") || pCategoria.equals("03")) && pDataAdmissao.compareTo(dataLimiteOpcao) <= 0) {
				if (pDataOpcao.compareTo(pDataAdmissao) >= 0) {
					if (pDataOpcao.compareTo(dataLimiteOpcao) >= 0) {
						// gfipTrabalhadorArquivo.set_13DataOpcaoFGTS(sdf.format(new
						// Date("05/10/1988")));
						dataOpcao = dataLimiteOpcao;
					}
				}
			}*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dataOpcao;
	}

	public static BigDecimal convertStringToBigDecimal(String valor){
		String valorFormatado = valor.replace(".", "");
		valorFormatado = valorFormatado.replace(",", ".");
		BigDecimal valorBigDecimal = new BigDecimal(valorFormatado);
		return valorBigDecimal;
	}

	public static String normalizarEAbreviar(String pString, Integer tamanhoMaximo){
		String strResult = normalizar(pString);
		if(strResult != null){
			strResult = ( strResult.length() <= tamanhoMaximo ? strResult : strResult.substring(0, tamanhoMaximo));
			strResult = strResult.toUpperCase();
		}
		return strResult;
	}
    public static String normalizar(String str) {
    	if (str == null){
    		return null;
    	}
        
        /** Troca os caracteres acentuados por não acentuados **/  
        String[][] caracteresAcento = {  
                {"Á", "A"}, {"á", "a"},  
                {"É", "E"}, {"é", "e"},  
                {"Í", "I"}, {"í", "i"},  
                {"Ó", "O"}, {"ó", "o"},  
                {"Ú", "U"}, {"ú", "u"},  
                {"À", "A"}, {"à", "a"},  
                {"È", "E"}, {"è", "e"},  
                {"Ì", "I"}, {"ì", "i"},  
                {"Ò", "O"}, {"ò", "o"},  
                {"Ù", "U"}, {"ù", "u"},  
                {"Â", "A"}, {"â", "a"},  
                {"Ê", "E"}, {"ê", "e"},  
                {"Î", "I"}, {"î", "i"},  
                {"Ô", "O"}, {"ô", "o"},  
                {"Û", "U"}, {"û", "u"},  
                {"Ä", "A"}, {"ä", "a"},  
                {"Ë", "E"}, {"ë", "e"},  
                {"Ï", "I"}, {"ï", "i"},  
                {"Ö", "O"}, {"ö", "o"},  
                {"Ü", "U"}, {"ü", "u"},  
                {"Ã", "A"}, {"ã", "a"},   
                {"Õ", "O"}, {"õ", "o"},  
                {"Ç", "C"}, {"ç", "c"},  
                {"0", "O"},
        };  

        for (int i = 0; i < caracteresAcento.length; i++) {  
            str = str.replaceAll(caracteresAcento[i][0], caracteresAcento[i][1]);  
        }  
          
        /** Troca os caracteres especiais da string por "" **/  
        String[] caracteresEspeciais = {"\\.", ",", "-", ":", "\\(", "\\)", "ª", "\\|", "\\\\", "°", "\\/", "\\[", "\\]", "\\{", "\\}","\\º","\\ª","\\","\\¾"};  
          
        for (int i = 0; i < caracteresEspeciais.length; i++) {  
            str = str.replaceAll(caracteresEspeciais[i], "");  
        }  
  
        /** Troca os espaços no início por "" **/  
        str = str.replaceAll("^\\s+", "");  
        /** Troca os espaços no início por "" **/  
        str = str.replaceAll("\\s+$", "");  
        /** Troca os espaços duplicados, tabulações e etc por  " " **/  
        str = str.replaceAll("\\s+", " ");  

        str = str.replaceAll("NNN", "NN");
        str = str.replaceAll("SSS", "SS");
        str = str.replaceAll("LLL", "LL");
        str = str.replaceAll("RRR", "RR");
        return str;  
    }  
    
    /**
     * Método que retorna a String do CPF formatado
     * @param cpf
     * @return
     */
    public static String formatarCpf(String cpf){
    	String cpfFormatado = null;
    	if (!isEmpty(cpf)){
    		try{
	    		MaskFormatter mask = new MaskFormatter("###.###.###-##");
	    		mask.setValueContainsLiteralCharacters(false);
	    		cpfFormatado = mask.valueToString(cpf);
    		}catch(Exception e){
    			cpfFormatado = null;
    		}
    	}
    	return cpfFormatado;
    }
    
    /**
     * Método que retorna a String do CPF formatado
     * @param cpf
     * @return
     */
    public static String formatarCnpj(String cnpj){
    	String cnpjFormatado = null;
    	if (!isEmpty(cnpj)){
    		try{
	    		MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
	    		mask.setValueContainsLiteralCharacters(false);
	    		cnpjFormatado = mask.valueToString(cnpj);
    		}catch(Exception e){
    			cnpjFormatado = null;
    		}
    	}
    	return cnpjFormatado;
    }
    
    /**
     * Retorna uma string com o formato de hora e minuto exibível nas páginas
     * 
     * @param hora
     * @param minuto
     * @return
     * 
     * @author Jonas Santos - jonas.santos@seplag.ce.gov.br
     * @since 19/01/2016
     */
    public static String formatarHoraMinuto(Integer hora, Integer minuto){
    	String horaMinutoAux;
		if (hora == null && minuto == null) {
			horaMinutoAux = "";
		} else {
			horaMinutoAux = StringUtil.preencheZerosEsquerda((hora == null) ? "" : String.valueOf(hora), 3);
			horaMinutoAux += ":";
			horaMinutoAux += StringUtil.preencheZerosEsquerda((minuto == null) ? "" : String.valueOf(minuto), 2);
		}
		return horaMinutoAux;
    }

    /** PIS/PASEP Digit Count*/  
    public static final int DIGIT_COUNT = 11;  
    /**PIS/PASEP Validation.<P> 
      * Returns true if valid or false if invalid. 
      * @param pisOrPasep The PIS/PASEP to validate. 
      * @return true if valid or false if invalid. 
      */  
    public static boolean isValid(String pisOrPasep){  
         if (pisOrPasep == null) return false;  
         String n = pisOrPasep.replaceAll("[^0-9]*","");  
         if (n.length() != DIGIT_COUNT) return false;  
         int i;          // just count   
         int digit;      // A number digit  
         int coeficient; // A coeficient    
         int sum;        // The sum of (Digit * Coeficient)  
         int foundDv;    // The found Dv (Chek Digit)  
         int dv = Integer.parseInt(String.valueOf(n.charAt(n.length()-1)));        
         sum = 0;  
         coeficient = 2;  
         for (i = n.length() - 2; i >= 0 ; i--){  
             digit = Integer.parseInt(String.valueOf(n.charAt(i)));                 
             sum += digit * coeficient;  
             coeficient ++;  
             if (coeficient > 9) coeficient = 2;                  
         }                  
         foundDv = 11 - sum % 11;  
         if (foundDv >= 10) foundDv = 0;          
         return dv == foundDv;  
    }  

    /**
     * Valida o digito verificador do PIS e também do NIT
     * @param plPIS
     * @return
     */
    public static boolean validateDigitoPIS (String plPIS) {

        Integer subStr3 = new Integer(plPIS.substring(0, 3));
        if(subStr3 > 299){
        	return false; 
        }

        int liTamanho = 0;  
        StringBuffer lsAux = null;  
        StringBuffer lsMultiplicador = new StringBuffer("3298765432");  
        int liTotalizador = 0;  
        int liResto = 0;  
        int liMultiplicando = 0;  
        int liMultiplicador = 0; 
        boolean lbRetorno = true;  
        int liDigito = 99;

        lsAux = new StringBuffer().append(plPIS);  
        liTamanho = lsAux.length();  

        if (liTamanho != 11) {  
            lbRetorno = false;  
        }  

        if (lbRetorno) {  
            for (int i=0; i<10; i++) {  

                liMultiplicando = Integer.parseInt(lsAux.substring(i, i+1));  
                liMultiplicador = Integer.parseInt(lsMultiplicador.substring(i, i+1));  
                liTotalizador += liMultiplicando * liMultiplicador;  
            }  

            liResto = 11 - liTotalizador % 11;  
            liResto = liResto == 10 || liResto == 11 ? 0 : liResto;  

            liDigito = Integer.parseInt("" + lsAux.charAt(10));  
            lbRetorno = liResto == liDigito;  
        }

        //Validando o NIT - Utilizar apenas para a RAIS. Desconsiderar para a GFIP
        //lbRetorno = validarNIT(plPIS);
        	
        return lbRetorno;  
    }

    public static boolean validarNIT (String nit) {  

        boolean retorno = true;  

        Integer subStr3 = new Integer(nit.substring(0, 3));

        if(subStr3.equals(167) || subStr3.equals(168) || subStr3.equals(267) ){
        	return false;
        }

        if(subStr3 >= 109 && subStr3 <= 119 ){
        	return false;
        }

        if(subStr3 < 100 || subStr3 > 299  ){
        	return false;
        }

        return retorno;  
    }

    //Se validaPIS() entao 
    //   entra pra GFIP();
    //   Se isNIT() então entra p/ inconsistência
    //Senão entra p/ inconsitência 
    public static boolean isNIT(String numPISouNIT) {  

        boolean retorno = false;  

        Integer subStr3 = new Integer(numPISouNIT.substring(0, 3));

        if(subStr3.equals(167) || subStr3.equals(168) || subStr3.equals(267) ){
        	return true;
        }

        if(subStr3 >= 109 && subStr3 <= 119 ){
        	return true;
        }

        if(subStr3 < 100 || subStr3 > 299  ){
        	return true;
        }

        return retorno;  
    }

    public static boolean validarPISPASEP(String pis){
     	int total = 0;
    	int resto = 0;
    	String numPIS = "0";
    	String strResto = "";
    	numPIS = pis;
    	String ftap = "3298765432";
    	Integer resultado = null;

        if(numPIS.equals("") || numPIS==null){
        	return false;
        }

        for(int i=0;i<=9;i++){
        	//resultado = (numPIS.slice(i,i+1))*(ftap.slice(i,i+1));
        	resultado = Integer.parseInt((numPIS.substring(i, i+1))) * Integer.parseInt((ftap.substring(i,i+1)));
        	total = total + resultado;
        }

        resto = (total % 11);

        if (resto != 0)
        {
        resto=11-resto;
        }

        if (resto==10 || resto==11)
        {
        strResto=resto+"";
        resto = Integer.parseInt(strResto.substring(1,2));
        }

        if (resto!=(Integer.parseInt(numPIS.substring(10,11))))
        {
        return false;
        }

        return true;
        }

    
/* // VERIFICAR O PIS

    var ftap="3298765432";
    var total=0;
    var i;
    var resto=0;
    var numPIS=0;
    var strResto="";

    function ChecaPIS(pis)
    {

    total=0;
    resto=0;
    numPIS=0;
    strResto="";

    numPIS=pis;

    if (numPIS=="" || numPIS==null)
    {
    return false;
    }

    for(i=0;i<=9;i++)
    {
    resultado = (numPIS.slice(i,i+1))*(ftap.slice(i,i+1));
    total=total+resultado;
    }

    resto = (total % 11)

    if (resto != 0)
    {
    resto=11-resto;
    }

    if (resto==10 || resto==11)
    {
    strResto=resto+"";
    resto = strResto.slice(1,2);
    }

    if (resto!=(numPIS.slice(10,11)))
    {
    return false;
    }

    return true;
    }

    // VALIDAR O PIS

    function ValidaPis()
    {
    pis=document.cadastro.numPIS.value;

    if (!ChecaPIS(pis))
    {
    alert("PIS INVALIDO");
    } else {
    alert("PIS VALIDO");
    }
    }*/

    public static boolean validaCNPJ(String strCNPJ) {
        int iSoma = 0, iDigito;
        char[] chCaracteresCNPJ;
        String strCNPJ_Calculado;
     
        if (! strCNPJ.substring(0,1).equals("")){
            try{
                strCNPJ=strCNPJ.replace('.',' ');
                strCNPJ=strCNPJ.replace('/',' ');
                strCNPJ=strCNPJ.replace('-',' ');
                strCNPJ=strCNPJ.replaceAll(" ","");
                strCNPJ_Calculado = strCNPJ.substring(0,12);
                if ( strCNPJ.length() != 14 ) return false;
                chCaracteresCNPJ = strCNPJ.toCharArray();
                for(int i = 0; i < 4; i++) {
                    if ((chCaracteresCNPJ[i]-48 >= 0) && (chCaracteresCNPJ[i]-48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i] - 48 ) * (6 - (i + 1)) ;
                    }
                }
                for( int i = 0; i < 8; i++ ) {
                    if ((chCaracteresCNPJ[i+4]-48 >= 0) && (chCaracteresCNPJ[i+4]-48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i+4] - 48 ) * (10 - (i + 1)) ;
                    }
                }
                iDigito = 11 - (iSoma % 11);
                strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
                   /* Segunda parte */
                iSoma = 0;
                for (int i = 0; i < 5; i++) {
                    if ((chCaracteresCNPJ[i]-48 >= 0) && (chCaracteresCNPJ[i]-48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i] - 48) * (7 - (i + 1)) ;
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if ((chCaracteresCNPJ[i+5]-48 >= 0) && (chCaracteresCNPJ[i+5]-48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i+5] - 48) * (10 - (i + 1)) ;
                    }
                }
                iDigito = 11 - (iSoma % 11);
                strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
                return strCNPJ.equals(strCNPJ_Calculado);
            } catch (Exception e) {
                return false;
            }
        } else return false;
    }
}