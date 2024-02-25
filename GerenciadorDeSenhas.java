import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciadorDeSenhas {
	
	private List<Usuario> usuarios;
	
	private void criarArquivo(File file) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.close();
		} catch(IOException e) {
			System.out.println("Erro ao criar o arquivo.");
		}
	}
	
	private void lerArquivo(File file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] classe = linha.split("%");
				Map<String, String> userMap = new HashMap<>();
				List<Conta> contas = new ArrayList<>();
				
				if(classe[0].equals("UsuarioComum")) {
					Map<String, String> palavrasPasse = new HashMap<>();
					for(String campo : classe[1].split("/")) {
						userMap.put(campo.split("=", 2)[0], campo.split("=", 2)[1]);
					}
					for(String palavraPasse : userMap.get("palavrasPasse").replace("{", "").replace("}", "").split(", ")) {
						palavrasPasse.put(palavraPasse.split("=")[0], palavraPasse.split("=")[1]);
					}
					for(String conta : userMap.get("contas").replace("[", "").replace("]", "").split(", ")) {
						contas.add(new Conta(conta.split("$")[0].split("=")[1], conta.split("$")[1].split("=")[1], conta.split("$")[2].split("=")[1]));
					}
					this.usuarios.add(new UsuarioComum(userMap.get("login"), userMap.get("senha"), palavrasPasse, contas));
				} else {	
					for(String campo : classe[1].split("/")) {
						userMap.put(campo.split("=", 2)[0], campo.split("=", 2)[1]);
					}
					for(String conta : userMap.get("contas").replace("[", "").replace("]", "").split(", ")) {
						contas.add(new Conta(conta.split("$")[0].split("=")[1], conta.split("$")[1].split("=")[1], conta.split("$")[2].split("=")[1]));
					}
					this.usuarios.add(new UsuarioEmpresa(userMap.get("empresa"), userMap.get("login"), userMap.get("senha"), contas));	
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void salvarArquivo(File file) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for(Usuario user : this.usuarios) {
				writer.write(user.toString());
				writer.newLine();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public GerenciadorDeSenhas() {
		
		File file = new File("usuarios.txt");
		this.usuarios = new ArrayList<Usuario>();
		
		if(file.exists()) {
			lerArquivo(file);
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

}
