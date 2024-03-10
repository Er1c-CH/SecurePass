import java.io.Serializable;
import java.util.List;

public final class UsuarioEmpresa extends Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String empresa;
	
	public UsuarioEmpresa(String login, String senha, String empresa) {
		super(login, senha);
		this.empresa = empresa;
	}
	
	public UsuarioEmpresa(String login, String senha, String empresa, List<Conta> contas) {
		super(login, senha, contas);
		this.empresa = empresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "UsuarioEmpresa empresa=" + empresa + "/" + super.toString();
	}
	
}
