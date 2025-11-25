package br.edu.fatec.zl.WoodManager.interfaces;

import java.sql.SQLException;
import java.util.List;
import br.edu.fatec.zl.WoodManager.model.Material;

public interface IMaterialDao {

	public String spManterMaterial(String acao, Material m) throws SQLException, ClassNotFoundException;
	public List<Material> findMateriaisByOption(String opcao, String parametro) throws SQLException, ClassNotFoundException;
	public List<Material> findByName(String nome) throws SQLException, ClassNotFoundException;
}
