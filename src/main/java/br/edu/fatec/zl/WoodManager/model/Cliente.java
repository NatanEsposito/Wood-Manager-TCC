package br.edu.fatec.zl.WoodManager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
	int codigo;
	String nome;
	String telefone;
	String CEP;
	String logradouro;
	String bairro;
	String localidade;
	String UF;
	String complemento;
	String numero;
	
	@Override
	public String toString() {
		return nome;
	}

}
