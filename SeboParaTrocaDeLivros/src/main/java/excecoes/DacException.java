package excecoes;

public class DacException extends Exception {

	private static final long serialVersionUID = -7669751088704144947L;

	public DacException(String mensagem) {
		super(mensagem);
	}

	public DacException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
