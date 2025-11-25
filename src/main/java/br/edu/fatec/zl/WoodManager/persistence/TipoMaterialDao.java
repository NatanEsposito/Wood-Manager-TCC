package br.edu.fatec.zl.WoodManager.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import br.edu.fatec.zl.WoodManager.interfaces.ICrud;
import br.edu.fatec.zl.WoodManager.interfaces.ITipoMaterialDao;
import br.edu.fatec.zl.WoodManager.model.TipoMaterial;


@Repository
public class TipoMaterialDao implements ICrud<TipoMaterial>, ITipoMaterialDao {
	private GenericDao gDao;

	public TipoMaterialDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public TipoMaterial findBy(TipoMaterial tp) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM v_tipoMaterial WHERE codigo = ?";
		Connection con = gDao.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, tp.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			tp.setCodigo(rs.getInt("codigo"));
			tp.setNome(rs.getString("nome"));
			rs.close();
			ps.close();
			con.close();
			return tp;
		} else {
			rs.close();
			ps.close();
			con.close();
			return null;
		}

	}

	@Override
	public List<TipoMaterial> findAll() throws SQLException, ClassNotFoundException {
		List<TipoMaterial> tiposMateriais = new ArrayList<>();
		String sql = "SELECT * FROM v_tipoMaterial";
		Connection con = gDao.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			TipoMaterial tp = new TipoMaterial();
			tp.setCodigo(rs.getInt("codigo"));
			tp.setNome(rs.getString("nome"));
	
			tiposMateriais.add(tp);
		}
		ps.close();
		rs.close();
		con.close();
		return tiposMateriais;
	}

	@Override
	public String spManterTipoMaterial(String acao, TipoMaterial tm) throws SQLException, ClassNotFoundException {
		String sql = "CALL sp_iud_tipoMaterial(?,?,?,?)";
		Connection con = gDao.getConnection();
		CallableStatement cs = con.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, tm.getCodigo());
		cs.setString(3, tm.getNome());
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(4);

		cs.close();
		con.close();
		return saida;
	}

	@Override
	public List<TipoMaterial> findByName(String nome) throws SQLException, ClassNotFoundException {
		List<TipoMaterial> patentes = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM v_tipoMaterial WHERE nome LIKE ?");
		PreparedStatement ps = con.prepareStatement(sql.toString());
		// "%" Para fazer buscas aproximadas
		ps.setString(1, "%" + nome + "%");
		// ps.setString(1, nome);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			TipoMaterial tm = new TipoMaterial();
			tm.setCodigo(rs.getInt("codigo"));
			tm.setNome(rs.getString("nome"));
			patentes.add(tm);
		}

		rs.close();
		ps.close();
		con.close();

		return patentes;
	}

	@Override
	public List<TipoMaterial> findTiposMateriaisyOption(String opcao, String parametro)
			throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
