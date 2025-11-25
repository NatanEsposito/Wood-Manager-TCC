package br.edu.fatec.zl.WoodManager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fornecedor {
	int codigo;
	String nome;
	String telefone;
	
	@Override
	public String toString() {
		return nome;
	}
	
}
