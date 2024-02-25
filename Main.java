import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    
    	Scanner sc = new Scanner(System.in);
    	GerenciadorDeSenhas gerenciador = new GerenciadorDeSenhas();
    	
    	String decisao = "";
    	while(decisao.equals(decisao)) {
    		
    		System.out.println("Bem-vindo ao SecurePass.");
    		System.out.println("1) Login.");
    		System.out.println("2) Cadastro.");
    		System.out.println("0) Sair.");
    		System.out.print("Entrada: ");
    		decisao = sc.nextLine();
    		
    		switch (decisao) {
			case "1": {
				System.out.print("Email: ");
				String email = sc.nextLine();
				System.out.print("Senha: ");
				String senha = sc.nextLine();
				gerenciador.entrar(email, senha);
				
			}
			case "2":
				System.out.print("1) Para empresa.\n2) Para cadastro comum.\nResposta: ");
				String decisaoCadastro = sc.nextLine();
				switch (decisaoCadastro) {
				case "1":
					System.out.print("Empresa: ");
					String empresa = sc.nextLine();
					System.out.print("Email: ");
					String email = sc.nextLine();
					System.out.print("Senha: ");
					String senha = sc.nextLine();
				}
			}
    	}
    }

}

