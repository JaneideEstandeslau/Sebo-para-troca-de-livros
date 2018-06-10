package Beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SessionTimeout extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -145085060797727995L;

	public void init() {
		reportarMensagemDeErro("Sua sessão expirou. Faça autenticação novamente no sistema!");
	}
	
}
