package br.com.vibbra.avalieweb.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;

import br.com.vibbra.avalieweb.entity.Usuario;
import br.com.vibbra.avalieweb.exception.BusinessException;

@Name("jsfUtil")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class JSFUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@In(create=true)
	private StatusMessages statusMessages;
	
	public void showErrorMessage(BusinessException exception) {
		showErrorMessage(exception.getMessage(), exception.getParams());
	}
	
	/**
	 * Método para adicionar uma mensagem do tipo "SEVERITY ERROR" no contexto
	 * JSF.
	 * 
	 * @param message
	 *            Mensagem a ser exibida.
	 */
	public void showErrorMessage(String messageName, Object... params) {
		addMessage(Severity.ERROR, messageName, params);
	}

	/**
	 * Método para adicionar uma mensagem do tipo "SEVERITY FATAL" no contexto
	 * JSF.
	 * 
	 * @param message
	 *            Mensagem a ser exibida.
	 */
	public void showFatalMessage(String messageName, Object... params) {
		addMessage(Severity.FATAL, messageName, params);
	}

	/**
	 * Método para adicionar uma mensagem do tipo "SEVERITY INFO" no contexto
	 * JSF.
	 * 
	 * @param message
	 *            Mensagem a ser exibida.
	 */
	public void showInfoMessage(String messageName, Object... params) {
		addMessage(Severity.INFO, messageName, params);
	}
	
	public void showWarnMessage(BusinessException exception) {
		showWarnMessage(exception.getMessage(), exception.getParams());
	}

	/**
	 * Método para adicionar uma mensagem do tipo "SEVERITY WARN" no contexto
	 * JSF.
	 * 
	 * @param message
	 *            Mensagem a ser exibida.
	 */
	public void showWarnMessage(String messageName, Object... params) {
		addMessage(Severity.WARN, messageName, params);
	}
	
	public void showWarnMessageId(String id, String messageName, Object... params) {
		addToControlMessage(id, Severity.WARN, messageName, params);
	}
	
	public void showInfoMessageId(String id, String messageName, Object... params){
		addToControlMessage(id, Severity.INFO, messageName, params);
	}
	
	private void addToControlMessage(String id, StatusMessage.Severity severity, String messageName, Object... params){
		statusMessages.addToControlFromResourceBundle(id, severity, messageName, params);
	}

	private void addMessage(StatusMessage.Severity severity, String messageName, Object... params){
		statusMessages.addFromResourceBundle(severity, messageName, params);	
	}
	
	public void clearMessages(){
		if (statusMessages != null){
			statusMessages.clear();
		}
	}
	
	public ExternalContext getExternalContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext();
	}

	public ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	@SuppressWarnings("rawtypes")
	public String getRequestParameter(String parameterName) {
		Map paramMap = getExternalContext().getRequestParameterMap();
		return (String) paramMap.get(parameterName);
	}
	
	public String getHttpServletRequestParameter(String parameterName){
		/* Recuperando o parâmetro informado na tela via request*/
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
		
		return req.getParameter(parameterName);
	}
	
	@SuppressWarnings("rawtypes")
	public Object getRequestAttribute(String attributeName) {
		Map attrMap = getExternalContext().getRequestMap();
		return attrMap.get(attributeName);
	}

	@SuppressWarnings("rawtypes")
	public Object getSessionAttribute(String attributeName) {
		Map attrMap = getExternalContext().getSessionMap();
		return attrMap.get(attributeName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSessionAttribute(String attributeName, Object object) {
		Map attrMap = getExternalContext().getSessionMap();
		attrMap.put(attributeName, object);
	}
	
	public Usuario getUsuarioLogado(){
		return (Usuario) getSessionAttribute("usuarioLogado");
	}

	@SuppressWarnings("rawtypes")
	public Object getApplicationAttribute(String attributeName) {
		Map reqAttrMap = getExternalContext().getApplicationMap();
		return reqAttrMap.get(attributeName);
	}

	public HttpServletResponse getResponse(FacesContext context) {
		return (HttpServletResponse) context.getExternalContext().getResponse();
	}

	public HttpServletRequest getRequest(FacesContext context) {
		return (HttpServletRequest) context.getExternalContext().getRequest();
	}

	public String getInitParameter(FacesContext context, String parameter) {
		return context.getExternalContext().getInitParameter(parameter);
	}

	public String getContextParam(String paramName) {
		return getInitParameter(FacesContext.getCurrentInstance(), paramName);
	}

	/**
	 * Método auxiliar para renderizar um relatório em pdf na tela.
	 * 
	 * @param array
	 *            Array contendo o conteúdo do relatório em bytes.
	 * @throws IOException
	 *             Erro ao renderizar o relatório no browser.
	 */
	public void renderPdf(byte[] array) throws IOException {
		HttpServletResponse res = getResponse(FacesContext.getCurrentInstance());
		// Configurando cabeçalho
		res.setContentType("application/pdf");
		res.setHeader("Pragma", "public");
		res.setHeader("Cache-control", "must-revalidate");
		// Enviando o pdf para ao navegador
		ServletOutputStream servletOutputStream = res.getOutputStream();
		servletOutputStream.write(array);
		FacesContext.getCurrentInstance().responseComplete();
	}


	@SuppressWarnings("deprecation")
	public String getRealPath() {
		return getRequest(FacesContext.getCurrentInstance()).getRealPath("");
	}
	
	
	/**
	 * Método que verifica se o servidor está configurado para o ambiente de Teste
	 *
	 * @return
	 *
	 * @author Jonas Santos - jonas.santos@seplag.ce.gov.br
	 * @since 11/09/2012
	 */
	public boolean isServidorTeste(){
		String versao = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("servidorTeste");
		return (versao != null && versao.equals("true"));
	}
	
	public String getNomeUsuarioLogado(){
		Usuario usuarioLogado = getUsuarioLogado();
		return (usuarioLogado == null ? "" : usuarioLogado.getEmail());
	}
	
	public String getGrupoUsuarioLogado(){
		return (String)getSessionAttribute("userGrupo");
	}
	
	
	/**
     * Método retirado da internet do blog de Rafael Ponte (http://www.rponte.com.br/tag/cleansubmittedvalues/)
     * 
     * @author rponte
     * 
     * "Limpa os dados dos componentes de edição e de seus filhos, recursivamente. Checa se o componente é instância de EditableValueHolder
     * e 'reseta' suas propriedades.
     * <p>
     * Quando este método, por algum motivo, não funcionar, parta para ignorância e limpe o componente assim:
     * <p><blockquote><pre>
     *  component.getChildren().clear()
     * </pre></blockquote>
     * "
     * 
     * 
     */
    public void cleanSubmittedValues(UIComponent component) {
        if(component != null){
            if (component instanceof EditableValueHolder) {
                EditableValueHolder evh = (EditableValueHolder) component;
                evh.setSubmittedValue(null);
                evh.setValue(null);
                evh.setLocalValueSet(false);
                evh.setValid(true);
            }
            if(component.getChildCount()>0){
                for (UIComponent child : component.getChildren()) {
                    cleanSubmittedValues(child);
                }
            }
        }
    }

    /**Recupera o componente através do id antes de limpá-lo */
    public void clenSubmittedValues(String idComponent){
        UIComponent component = FacesContext.getCurrentInstance().getViewRoot().findComponent(idComponent);
        if(component != null)
            cleanSubmittedValues(component);
    }
    
    /**
     * Exibe uma mensagem para um componente <h:message/> de acordo com o seu ID especificado
     * 
     * @param idComponent
     * 			Id do componente <h:message/>
     * @param message
     * 			Valor da mensaem a ser exibida
     * 
     * @author Jonas Karlos - jonas.santos@seplag.ce.gov.br
     * @since 25/10/2012
     */
    public void showIdentifyMessage(String idComponent, String message){
    	UIComponent root = FacesContext.getCurrentInstance().getViewRoot();
    	if (root != null){
    		UIComponent component = root.findComponent(idComponent);
    		if (component != null){
    			FacesContext.getCurrentInstance().addMessage(component.getClientId(FacesContext.getCurrentInstance()),
            			new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
    		}
    	}
    }
    
}
