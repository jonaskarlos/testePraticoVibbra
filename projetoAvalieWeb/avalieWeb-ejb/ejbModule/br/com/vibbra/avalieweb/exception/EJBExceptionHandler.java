package br.com.vibbra.avalieweb.exception;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class EJBExceptionHandler {
	
	@AroundInvoke
	public Object handle(InvocationContext context) throws Exception {
		Object result = null;
		try {
			result = context.proceed();			
		} catch (Exception ex) {
			if (ex instanceof BusinessException) {
				BusinessException bx = (BusinessException) ex;
				if (!bx.isPrinted()) {
					ex.printStackTrace();
				}
				throw bx;
			}
			throw new BusinessException(ex);
		}
		return result;
	}

}
