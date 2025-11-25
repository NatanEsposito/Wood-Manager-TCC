package br.edu.fatec.zl.WoodManager.interfaces;

import java.sql.SQLException;
import java.util.List;
import br.edu.fatec.zl.WoodManager.model.TipoMaterial;

public interface ITipoMaterialDao {

	public String spManterTipoMaterial(String acao, TipoMaterial tp) throws SQLException, ClassNotFoundException;
	public List<TipoMaterial> findTiposMateriaisyOption(String opcao, String parametro) throws SQLException, ClassNotFoundException;
	public List<TipoMaterial> findByName(String nome) throws SQLException, ClassNotFoundException;
}
