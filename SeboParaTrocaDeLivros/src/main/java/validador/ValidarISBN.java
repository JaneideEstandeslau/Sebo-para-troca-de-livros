package validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.validator.routines.ISBNValidator;

@FacesValidator("isbnValidar")
public class ValidarISBN implements Validator{
	
	private ISBNValidator validar;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value){
		
		validar = new ISBNValidator();
		
		if (!validar.isValid((String) value)) {
			FacesMessage msg = new FacesMessage("Erro: ISBN invalido.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
