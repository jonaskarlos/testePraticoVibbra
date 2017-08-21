package br.com.vibbra.avalieweb.exception;

import javax.ejb.EJBException;

import br.com.vibbra.avalieweb.util.StringUtil;

public class BusinessException extends EJBException {
	private static final long serialVersionUID = -3787224591821659687L;
	
	private Object[] params;
	
	private boolean printed = false;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String msg) {
		super(msg);
	}
	
	public BusinessException(String msg, Object... params) {
		super(msg);
		this.params = params;
	}
	
/*	public BusinessException(String msg, Throwable nested) {
		super(msg, nested);
	}*/
	
	public BusinessException(String msg, Exception nested) {
		super(msg, nested);
	}
	
	/*public BusinessException(String msg, Throwable nested, Object... params) {
		super(msg, nested);
		this.params = params;
	}*/
	
	public BusinessException(String msg, Exception nested, Object... params) {
		super(msg, nested);
		this.params = params;
	}
	
	/*public BusinessException(Throwable nested) {
		super(nested);
	}
	*/
	public BusinessException(Exception nested) {
		super(nested);
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
	
	/* (non-Javadoc)
	 * @see javax.ejb.EJBException#getMessage()
	 */
	@Override
	public String getMessage() {
		String msg = "";
		if (this.getCause() != null && !StringUtil.isEmpty(this.getCause().getMessage())){
			msg = this.getCause().getMessage();
		}else{
			msg = super.getMessage();
		}
		return msg;
	}

	public boolean isPrinted() {
		return printed;
	}
	
	@Override
	public void printStackTrace() {
		printed = true;
		super.printStackTrace();
	}
}
