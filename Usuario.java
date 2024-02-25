import java.util.List;
import java.util.Objects;

public abstract class Usuario {
	
	private String login;
	private String senha;
	private List<Conta> contas;
	
	public Usuario(String login, String senha, List<Conta> contas) {
		this.login = login;
		this.senha = senha;
		this.contas = contas;
	}
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(Conta conta) {
		this.contas.add(conta);
	}
	
	public void setContas(List<Conta> conta) {
		this.contas = conta;
	}

	@Override
	public String toString() {
		return "login=" + login + "/senha=" + senha + "/contas=" + contas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(login);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(login, other.login);
	}

}
