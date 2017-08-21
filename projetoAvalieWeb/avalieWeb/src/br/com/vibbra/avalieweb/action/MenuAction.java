package br.com.vibbra.avalieweb.action;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Manager;
import org.jboss.seam.security.Identity;

@Name("menuAction")
@Scope(ScopeType.SESSION)
public class MenuAction implements Serializable {
	private static final long serialVersionUID = 6467605123671783817L;
	
	@In Identity identity;

	public String callPage(String page) {
		Manager.instance().endRootConversation(true);
		return page;
	}
}
