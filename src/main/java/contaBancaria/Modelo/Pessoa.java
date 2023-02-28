package contaBancaria.Modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pessoas")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private BigDecimal saldo = new BigDecimal(0.0);
	private LocalDate date = LocalDate.now();
	
	public Pessoa() {}
	
	public Pessoa(String nome, String cpf, String email) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return "\nID: " +getId() +
			   "\nNome: " +getNome() +
			   "\nCPF: " +getCpf() +
			   "\nE-mail: " +getEmail() +
			   "\nSaldo: " +getSaldo();
	}
}





