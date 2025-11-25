package br.edu.fatec.zl.WoodManager.interfaces;

import java.sql.SQLException;
import java.util.List;
import br.edu.fatec.zl.WoodManager.model.SobraMaterial;

public interface ISobraMaterialDao {

	public String spManterSobraMaterial(String acao, SobraMaterial sm) throws SQLException, ClassNotFoundException;
	public List<SobraMaterial> findSobraMateriaisByOption(String opcao, String parametro) throws SQLException, ClassNotFoundException;
	public List<SobraMaterial> findByName(String nome) throws SQLException, ClassNotFoundException;
}
