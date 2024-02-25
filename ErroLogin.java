
public class ErroLogin extends RuntimeException {
	
	public ErroLogin(String msg) {
		super(msg);
	}
	
	public ErroLogin(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
