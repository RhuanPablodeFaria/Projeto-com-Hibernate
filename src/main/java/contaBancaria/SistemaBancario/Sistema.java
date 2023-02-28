package contaBancaria.SistemaBancario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import contaBancaria.DAO.PessoaDAO;
import contaBancaria.Modelo.Pessoa;
import contaBancaria.Util.JPAUtil;

public class Sistema {

	static Scanner input = new Scanner(System.in);
	static EntityManager entityManager = JPAUtil.getEntityManager();
	static PessoaDAO pessoaDAO = new PessoaDAO(entityManager);
	
	public static void main(String[] args) throws InterruptedException {
		operacoes();

	}

	public static void operacoes() throws InterruptedException {

		System.out.println("------------------------------------------------------");
		System.out.println("-------------Bem vindos a nossa Agência---------------");
		System.out.println("------------------------------------------------------");
		System.out.println("***** Selecione uma operação que deseja realizar *****");
		System.out.println("------------------------------------------------------");
		System.out.println("	   	|   Opção 1 - Criar conta  	   |");
		System.out.println("	   	|   Opção 2 - Depositar  	   |");
		System.out.println("	   	|   Opção 3 - Sacar 	       |");
		System.out.println("	   	|   Opção 4 - Transferir	   |");
		System.out.println("	   	|   Opção 5 - Listar	       |");
		System.out.println("	   	|   Opção 6 - Excluir Conta    |");
		System.out.println("	   	|   Opção 7 - Sair 	      	   |");
		System.out.println();
		int valor = input.nextInt();

		switch (valor) {

		case 1:
			criarConta();
			break;

		case 2:
			depositar();
			break;

		case 3:
			sacar();
			break;

		case 4:
			transferir();
			break;

		case 5:
			listarContas();
			break;
			
		case 6:
			excluirConta();
			break;

		case 7:
			System.out.println("Obrigado pela preferencia!");
			System.exit(0);

		default:
			System.out.println("Numero invalido!");
			operacoes();
			break;

		}
	}


	private static void criarConta() throws InterruptedException {

		System.out.println("Digite seu Nome: ");
		String nome = input.next();

		System.out.println("Digite seu CPF: ");
		String cpf = input.next();		
		
		if (cpf.length() != 11) {
			while (cpf.length() != 11) {
				System.out.println("Digite novamente seu CPF: ");
				cpf = input.next();
			}
		}

		System.out.println("Digite seu E-mail: ");
		String email = input.next();
		Pessoa pessoa = new Pessoa(nome, cpf, email);

		pessoaDAO.cadastrarPessoa(pessoa);
		
		operacoes();
	}

	private static void depositar() throws InterruptedException {
		System.out.println("Qual o ID da conta que deseja depositar? ");
		Long id = input.nextLong();
		
		System.out.println("Qual o valor que deseja depositar? ");
		BigDecimal valorDeposito = input.nextBigDecimal();

		pessoaDAO.depositar(id, valorDeposito);		
		
		operacoes();
	}
	

	private static void transferir() throws InterruptedException {
		System.out.println("Informe o ID do remetente: ");
		Long idRemetente = input.nextLong();
		System.out.println("Informe o ID do destinatário: ");
		Long idDestinatario = input.nextLong();
		System.out.println("Valor a ser transferido: ");
		BigDecimal valorDaTrasferencia = input.nextBigDecimal();
		
		pessoaDAO.transferencia(idRemetente, idDestinatario, valorDaTrasferencia);
		
		operacoes();
	}

	private static void sacar() throws InterruptedException {
		System.out.println("Qual é a conta que deseja fazer o saque: ");
		Long id = input.nextLong();
		
		System.out.println("Qual valor que deseja sacar: ");
		BigDecimal valorDoSaque = input.nextBigDecimal();
		pessoaDAO.sacar(id, valorDoSaque);
		
		operacoes();
	}

	private static void listarContas() throws InterruptedException{
		List<Pessoa> listaBancaria = pessoaDAO.listarPessoas();
		listaBancaria.forEach(e -> System.out.println(e));
		
		operacoes();
	}

	private static void excluirConta() throws InterruptedException {
		System.out.println("Qual ID da conta que deseja excluir: ");
		Long idExclusao = input.nextLong();
		
		pessoaDAO.excluirConta(idExclusao);
		
		operacoes();
	}
}
