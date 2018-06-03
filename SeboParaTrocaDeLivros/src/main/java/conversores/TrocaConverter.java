package conversores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import excecoes.ServiceDacException;
import model.ProblemaTroca;
import model.Troca;
import service.TrocaService;

@FacesConverter(forClass = Troca.class)
public class TrocaConverter implements Converter{
	
	private TrocaService service = new TrocaService();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {
			Long id = Long.parseLong(value);
			return service.getByID(id);
		} catch (ServiceDacException | NumberFormatException e) {
			String msgErroStr = String.format(
					"Erro de conversão! Não foi possível realizar a conversão da string '%s' para o tipo esperado.",
					value);
			FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErroStr, msgErroStr);
			throw new ConverterException(msgErro);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof Troca)) {
			return null;
		}
		
		Long id = ((Troca) value).getId();
		return (id != null) ? id.toString() : null;
	}

}
