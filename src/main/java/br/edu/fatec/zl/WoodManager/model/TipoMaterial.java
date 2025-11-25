package br.edu.fatec.zl.WoodManager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoMaterial {

	int codigo;
	String nome;

	@Override
	public String toString() {
		return nome;
	}
}
