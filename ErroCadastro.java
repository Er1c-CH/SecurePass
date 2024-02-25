
public class ErroCadastro extends RuntimeException {
	
	public ErroCadastro(String msg) {
		super(msg);
	}
	
	public ErroCadastro(String msg, Throwable cause) {
		super(msg, cause);
	}

}
