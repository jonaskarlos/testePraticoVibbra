package br.com.vibbra.avalieweb.action;

import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.Controller;
import org.jboss.seam.log.Log;

import br.com.vibbra.avalieweb.util.JSFUtil;

public class GenericAction extends Controller {

	private static final long serialVersionUID = 1L;
	
	private static  final Integer NUMERO_LINHAS = 15;
	
	@Logger
	protected Log logger;

	@In
	private FacesMessages facesMessages;

	@In
	protected JSFUtil jsfUtil;

	public FacesMessages getFacesMessages() {
		return this.facesMessages;
	}

	protected String getRequestParameter(String key) {
		HttpServletRequest request = (HttpServletRequest) getFacesContext()
				.getExternalContext().getRequest();
		return request.getParameter(key);
	}
	
	public  Integer getNumeroLinha() {
		return NUMERO_LINHAS;
	}

}
