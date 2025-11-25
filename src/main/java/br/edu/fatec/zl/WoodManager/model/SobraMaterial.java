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
public class SobraMaterial {
	
	int codigo;
	String estoque;
	Date data;
	Material material;
	String largura;
    String comprimento;
    String espessura;
    
	@Override
	public String toString() {
		return estoque;
	}
	
}
