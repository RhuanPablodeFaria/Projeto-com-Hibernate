package contaBancaria.DAO;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import contaBancaria.Modelo.Pessoa;

public class PessoaDAO {

	private EntityManager entityManager;

	public PessoaDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void abrirTransacao() {

	}

	public void cadastrarPessoa(Pessoa pessoa) {
		entityManager.getTransaction().begin();
		entityManager.persist(pessoa);
		entityManager.getTransaction().commit();
		entityManager.clear();

		System.out.println("Seu cadastro foi feito com sucesso!!");
	}

	@SuppressWarnings("unchecked")
	public List<Integer> pegarId() {
		String jpql = "SELECT p.id FROM Pessoa p";
		return entityManager.createQuery(jpql).getResultList();
	}

	@SuppressWarnings("unlikely-arg-type")
	public BigDecimal pegarSaldoAtual(Long id) {
		String jpql = "SELECT p.saldo FROM Pessoa p WHERE p.id = ?1";
		List<Integer> pegarId = pegarId();

		if (pegarId.contains(id)) {
			BigDecimal saldoAtual = entityManager.createQuery(jpql, BigDecimal.class).setParameter(1, id)
					.getSingleResult();
			return saldoAtual;
		} else {
			System.out.println("O ID que você passou não está vinculada a nenhuma pessoa");
			return null;
		}

	}

	public void depositar(Long id, BigDecimal saldoRecebido) {
		BigDecimal saldoAtual = pegarSaldoAtual(id);
		BigDecimal valorDoDeposito = saldoRecebido;
		BigDecimal saldoTotal = valorDoDeposito.add(saldoAtual);

		String jpql = "UPDATE Pessoa p SET p.saldo = ?1 WHERE (p.id = ?2)";

		entityManager.getTransaction().begin();
		entityManager.createQuery(jpql).setParameter(1, saldoTotal).setParameter(2, id).executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.clear();

		System.out.println("Seu deposito foi feito com sucesso!!");
	}

	public List<Pessoa> listarPessoas() {
		String jpql = "SELECT p FROM Pessoa p";
		return entityManager.createQuery(jpql, Pessoa.class).getResultList();

	}

	@SuppressWarnings("unlikely-arg-type")
	public void sacar(Long id, BigDecimal saldoRecebido) {
		String jpql = "UPDATE Pessoa p SET p.saldo = ?1 WHERE p.id = ?2";
		BigDecimal saldoAtual = pegarSaldoAtual(id);

		BigDecimal valorPosSaque = saldoAtual.subtract(saldoRecebido);

		List<Integer> pegarId = pegarId();
		
		if(pegarId.contains(id)) {
		
		if (saldoAtual.compareTo(saldoRecebido) == 1 || saldoAtual.compareTo(saldoRecebido) == 0 ) {
			entityManager.getTransaction().begin();
			entityManager.createQuery(jpql).setParameter(1, valorPosSaque).setParameter(2, id).executeUpdate();
			entityManager.getTransaction().commit();
			entityManager.clear();

			System.out.println("Saque feito com sucesso!!");
		} else {
			System.out.println("Erro ao sacar!");
		}
		}else {
			System.out.println("ID não encontrado");
		}
	}

	public void transferencia(Long idRemetente, Long idDestinatario, BigDecimal valorDaTransferencia) {
		String jpql = "UPDATE Pessoa p SET p.saldo = ?1 WHERE p.id = ?2";

		BigDecimal destinatario = pegarSaldoAtual(idDestinatario);
		BigDecimal remetente = pegarSaldoAtual(idRemetente);

		BigDecimal valorFinalDoRemetente = remetente.subtract(valorDaTransferencia);
		BigDecimal valorFinalDoDestinatario = destinatario.add(valorDaTransferencia);

		if (remetente.compareTo(valorDaTransferencia) == 1 || remetente.compareTo(valorDaTransferencia) == 0) {

			entityManager.getTransaction().begin();
			entityManager.createQuery(jpql).setParameter(1, valorFinalDoRemetente).setParameter(2, idRemetente)
					.executeUpdate();
			entityManager.createQuery(jpql).setParameter(1, valorFinalDoDestinatario).setParameter(2, idDestinatario)
					.executeUpdate();
			entityManager.getTransaction().commit();
			entityManager.clear();
		} else {
			System.out.println("Remetente não possui saldo maio que o valor da transferencia!!");
		}
	}

	public void excluirConta(Long idDeExclusao) {
		String jpql = "DELETE FROM Pessoa p WHERE (p.id = ?1)";

		entityManager.getTransaction().begin();
		entityManager.createQuery(jpql).setParameter(1, idDeExclusao).executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.clear();

		System.out.println("Conta excluida com sucesso!!");
	}
}
