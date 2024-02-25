import java.util.List;

public class UsuarioEmpresa extends Usuario {
	
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
		return "UsuarioEmpresa%empresa=" + empresa + "/" + super.toString();
	}
	

}
