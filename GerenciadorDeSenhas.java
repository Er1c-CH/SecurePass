import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorDeSenhas {
	
	private Scanner sc;
	private List<Usuario> usuarios;
	
	private void criarArquivo(File file) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.close();
		} catch(IOException e) {
			System.out.println("Erro ao criar o arquivo.");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void lerArquivo() {
		FileInputStream fis;
		try {
			
			fis = new FileInputStream("Usuarios.tmp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.usuarios = (List<Usuario>) ois.readObject();
			ois.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void salvarArquivo() {
		FileOutputStream fos;	
		try {
			
			fos = new FileOutputStream("Usuarios.tmp");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.usuarios);
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public GerenciadorDeSenhas() {
		
		File file = new File("Usuarios.tmp");
		this.sc = new Scanner(System.in);
		this.usuarios = new ArrayList<Usuario>();
		
		if(file.exists()) {
			lerArquivo();
		} else {
			criarArquivo(file);
		}		
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario usuarios) {
		this.usuarios.add(usuarios);
	}
	
	public Usuario entrar(String login, String senha) {
		for(Usuario usuario : this.usuarios) {
			if(usuario.getLogin().equals(login)) {
				if(usuario.getSenha().equals(senha)) {
					return usuario;
				} else {
					throw new ErroLogin("Senha incorreta.");
				}
			}
		}
		throw new ErroLogin("E-mail invalido.");
	}
	
	public void cadastrarUsuarioComum(String login, String senha, Map<String, String> palavraPasse) {
		UsuarioComum user = new UsuarioComum(login, senha, palavraPasse);
			
		for(Usuario i : this.usuarios) {
			if(i.getLogin().equals(login))
				throw new ErroCadastro("Login já existe.");
		}
		
		this.usuarios.add(user);
	}
	
	public void cadastrarUsuarioEmpresa(String empresa, String login, String senha) {
		UsuarioEmpresa user = new UsuarioEmpresa(login, senha, empresa);
		
		for(Usuario i : this.usuarios) {
			if(i.getLogin().equals(login))
				throw new ErroCadastro("Login já existe.");
		}
		
		this.usuarios.add(user);
	}
	
	public void iniciar() {
		this.telaInicial();
	}
	
	private void telaInicial() {
		System.out.print(
				"\nBem-vindo ao Secure Pass!\n\n" +
				"1) Fazer login\n" +
				"2) Fazer cadastro\n" +
				"3) Esqueci a senha\n" +
				"4) Finalizar\n\n" +
				"Entrada: "			
		);
		
		String decisao = this.sc.nextLine();

		switch (decisao) {
		case "1":
			this.telaLogin();
		case "2":
			this.telaCadastro();
		case "3":
			this.esqueciASenha();
		case "4":
			System.out.println("Finalizando programa.");
			this.salvarArquivo();
			System.exit(0);
		default:
			this.telaInicial();
		}
		
	}
	
	private void telaLogin() {
		System.out.println("Fazendo login no Secure Pass.");
		System.out.print("E-mail: ");
		String email = sc.nextLine();
		System.out.print("Senha: ");
		String senha = sc.nextLine();
		
		try {
			Usuario user = entrar(email, senha);
			this.telaPrincipal(user);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			this.telaInicial();
		}
	}
	
	private void telaCadastro() {
		System.out.println("Fazendo cadastro no Secure Pass.");
		System.out.println("1) Usuario comum\n2) Usuario de empresa\n");
		System.out.print("Entrada: ");
		String decisao = sc.nextLine();
		
		switch (decisao) {
		case "1":
			this.telaCadastroComum();
		case "2":
			this.telaCadastroEmpresa();
		default:
			this.telaInicial();
		}
	}
	
	private void telaCadastroComum() {
		System.out.print("E-mail: ");
		String email = sc.nextLine();
		System.out.print("Senha: ");
		String senha = sc.nextLine();
		System.out.println("Cadastrando chaves de segurança.");
		Map<String, String> recuperacao = new HashMap<>();
		while (true) {
			System.out.print("Chave: ");
			String chave = sc.nextLine();
			System.out.print("Valor: ");
			String valor = sc.nextLine();
			recuperacao.put(chave, valor);
			System.out.println("1) para continuar cadastrando chaves.");
			String decisao = sc.nextLine();
			if(!decisao.equals("1"))
				break;
		}
		try {
			this.cadastrarUsuarioComum(email, senha, recuperacao);
			this.telaInicial();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			this.telaInicial();
		}
	}
	
	private void telaCadastroEmpresa() {
		System.out.print("Empresa: ");
		String empresa = sc.nextLine();
		System.out.print("E-mail: ");
		String email = sc.nextLine();
		System.out.print("Senha: ");
		String senha = sc.nextLine();
		try {
			this.cadastrarUsuarioEmpresa(empresa, email, senha);
			this.telaInicial();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			this.telaInicial();
		}
	}
	
	private void esqueciASenha() {
		System.out.println("Recuperação de senha.");
		System.out.print("E-mail: ");
		String email = sc.nextLine();
		for(Usuario user : this.usuarios) {
			if(user.getLogin().equals(email)) {
				if(user instanceof UsuarioComum) {
					UsuarioComum u = (UsuarioComum) user;
					for(Map.Entry<String, String> entry : u.getPalavrasPasse().entrySet()) {
						System.out.println("Chave: " + entry.getKey());
						System.out.print("Valor: ");
						String valor = sc.nextLine();
						if(!entry.getValue().equals(valor)) {
							System.out.println("Valor errado.");
							this.telaInicial();
						}
					}
					System.out.println("Verificação concluida.");
					System.out.print("Nova senha: ");
					String novaSenha = sc.nextLine();
					user.setSenha(novaSenha);
					this.telaInicial();
				}
			} else {
				System.out.println("Usuarios de empresa não tem recuperação de senha, entre em contato com a empresa.");
				this.telaInicial();
			}
		}		
	}
	
	private void telaPrincipal(Usuario user) {
		System.out.print(
				"1) Visualizar contas cadastradas.\n"
				 + "2) Cadastrar nova conta.\n"
				 + "3) Deletar conta.\n"
				 + "4) Sair.\n\n"
				 + "Entrada: "
		);
		
		String decisao = sc.nextLine();
		
		switch(decisao) {
		case "1": {
			System.out.println(user.getContas().toString());
			this.telaPrincipal(user);
		}
		case "2": 
			this.cadastrarNovaConta(user);
		case "3":
			this.deletarConta(user);
		case "4":
			this.telaInicial();
		default:
			this.telaPrincipal(user);
			
		}
	}
	
	private void cadastrarNovaConta(Usuario user) {
		System.out.print("Plataforma: ");
		String plataforma = sc.nextLine();
		System.out.print("Login: ");
		String login = sc.nextLine();
		System.out.print("Senha: ");
		String senha = sc.nextLine();
		System.out.print("Descrição: ");
		String descricao = sc.nextLine();
		user.setConta(new Conta(plataforma, login, senha, descricao));
		this.telaPrincipal(user);
	}
	
	private void deletarConta(Usuario user) {
		System.out.print("Plataforma: ");
		String plataforma = sc.nextLine();
		for(Conta c : user.getContas()) {
			if(c.getPlataforma().equals(plataforma)) {
				user.getContas().remove(c);
			}
		}
		this.telaPrincipal(user);
	}
}
