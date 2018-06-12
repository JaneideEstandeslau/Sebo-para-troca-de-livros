package excecoes;


public class DacRuntimeException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8237339240939654306L;

	public DacRuntimeException(String mensagem) {
		super(mensagem);
	}

	public DacRuntimeException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
