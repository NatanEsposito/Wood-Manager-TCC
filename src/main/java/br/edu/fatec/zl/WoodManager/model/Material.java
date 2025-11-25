package br.edu.fatec.zl.WoodManager.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Material {
	
	int codigo;
	String nome;
	float preco;
	String cor;
	Date data;
	Fornecedor fornecedor;
	Cliente cliente;
    String largura;
    String comprimento;
    String espessura;
    TipoMaterial tipoMaterial;
	
	@Override
	public String toString() {
		return nome;
	}
	
}
