package br.edu.fatec.zl.WoodManager.interfaces;

import java.sql.SQLException;
import java.util.List;

import br.edu.fatec.zl.WoodManager.model.Cliente;

public interface IClienteDao{
	
	public String spManterCliente (String acao, Cliente c) throws SQLException, ClassNotFoundException;
	public List<Cliente> findClientesByOption(String opcao, String parametro) throws SQLException, ClassNotFoundException;
	public List<Cliente> findByName(String nome) throws SQLException, ClassNotFoundException;

}
