import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class UsuarioComum extends Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Map<String, String> palavrasPasse;

	public UsuarioComum(String login, String senha, Map<String, String> palavrasPasse) {
		super(login, senha);
		this.palavrasPasse = palavrasPasse;
	}
	
	public UsuarioComum(String login, String senha, Map<String, String> palavrasPasse, List<Conta> contas) {
		super(login, senha, contas);
		this.palavrasPasse = palavrasPasse;
	}

	public Map<String, String> getPalavrasPasse() {
		return palavrasPasse;
	}

	public void setPalavrasPasse(Map<String, String> palavrasPasse) {
		this.palavrasPasse = palavrasPasse;
	}
	
	public void recuperarSenha() {
		Scanner sc = new Scanner(System.in);
		int contador = 1;
		
		for(Map.Entry<String, String> elem : this.palavrasPasse.entrySet()) {
			System.out.println(contador + "° Pergunta: '" + elem.getKey() + "'\nResposta: ");
			String resposta = sc.nextLine();
			if(!resposta.equals(elem.getValue())) {
				sc.close();
				throw new ErroRecuperarSenha("Resposta incorreta.");
			}			
			contador++;
		}
		
		System.out.println("Digite a nova senha: ");
		this.setSenha(sc.nextLine());
		sc.close();
		System.out.println("Senha alterado com sucesso.");
		
	}

	@Override
	public String toString() {
		return "UsuarioComum palavrasPasse=" + palavrasPasse + "/" + super.toString();
	}
		
}
