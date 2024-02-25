
public class Conta {
	
	private String plataforma;
	private String email;
	private String password;
	private String descricao;
	
	public Conta(String plataforma, String email, String password, String descricao) {
		this.plataforma = plataforma;
		this.email = email;
		this.password = password;
		this.descricao = descricao.replace(" ", "_");
	}
	
	public Conta(String plataforma, String email, String password) {
		this.plataforma = plataforma;
		this.email = email;
		this.password = password;
		this.descricao = "Não_há_descrição.";
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "plataforma=" + plataforma + "$email=" + email + "$password=" + password + "$descricao=" + descricao;
	}
	
	

}
