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
import br.edu.fatec.zl.WoodManager.interfaces.ISobraMaterialDao;
import br.edu.fatec.zl.WoodManager.model.Material;
import br.edu.fatec.zl.WoodManager.model.SobraMaterial;


@Repository
public class SobraMaterialDao implements ICrud<SobraMaterial>, ISobraMaterialDao {

	private GenericDao gDao;

	public SobraMaterialDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public String spManterSobraMaterial(String acao, SobraMaterial sm) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_iud_sobraMaterial (?,?,?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, sm.getCodigo());
		cs.setString(3, sm.getEstoque());
		cs.setDate(4, sm.getData());
		cs.setString(5, sm.getLargura());
		cs.setString(6, sm.getComprimento());
		cs.setString(7, sm.getEspessura());
		cs.setInt(8, sm.getMaterial().getCodigo());
		cs.registerOutParameter(9, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(9);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public SobraMaterial findBy(SobraMaterial sm) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM v_sobraMaterial WHERE codigo = ?");

		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setInt(1, sm.getCodigo());

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Material m = new Material();
			m.setCodigo(rs.getInt("codigoMaterial"));
			m.setNome(rs.getString("nomeMaterial"));

			sm.setCodigo(rs.getInt("codigo"));
			sm.setEstoque(rs.getString("estoque"));
			sm.setData(rs.getDate("data"));
			sm.setLargura(rs.getString("largura"));
			sm.setComprimento(rs.getString("comprimento"));
			sm.setEspessura(rs.getString("espessura"));

			sm.setMaterial(m);

			rs.close();
			ps.close();
			c.close();
			return sm;
		} else {
			rs.close();
			ps.close();
			c.close();
			return null;
		}
	}

	@Override
	public List<SobraMaterial> findAll() throws SQLException, ClassNotFoundException {
		List<SobraMaterial> sonbraMateriais = new ArrayList<>();
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM v_sobraMaterial");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			SobraMaterial sm = new SobraMaterial();
			Material m = new Material();
			m.setCodigo(rs.getInt("codigoMaterial"));
			m.setNome(rs.getString("nomeMaterial"));

			sm.setCodigo(rs.getInt("codigo"));
			sm.setEstoque(rs.getString("estoque"));
			sm.setData(rs.getDate("data"));
			sm.setLargura(rs.getString("largura"));
			sm.setComprimento(rs.getString("comprimento"));
			sm.setEspessura(rs.getString("espessura"));

			sm.setMaterial(m);
			sonbraMateriais.add(sm);
		}
		rs.close();
		ps.close();
		c.close();
		return sonbraMateriais;
	}

	@Override
	public List<SobraMaterial> findSobraMateriaisByOption(String opcao, String parametro)
			throws SQLException, ClassNotFoundException {
		List<SobraMaterial> sobraMateriais = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM fn_buscar_insumo(?,?) ");

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, opcao);
		ps.setString(2, parametro);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			SobraMaterial sm = new SobraMaterial();
			Material m = new Material();
			m.setCodigo(rs.getInt("codigoMaterial"));
			m.setNome(rs.getString("nomeMaterial"));

			sm.setCodigo(rs.getInt("codigo"));
			sm.setEstoque(rs.getString("estoque"));
			sm.setData(rs.getDate("data"));
			sm.setLargura(rs.getString("largura"));
			sm.setComprimento(rs.getString("comprimento"));
			sm.setEspessura(rs.getString("espessura"));

			sm.setMaterial(m);
			sobraMateriais.add(sm);
		}

		rs.close();
		ps.close();
		con.close();

		return sobraMateriais;
	}

	@Override
	public List<SobraMaterial> findByName(String nome) throws SQLException, ClassNotFoundException {
		List<SobraMaterial> sobraMateriais = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM v_sobraMaterial WHERE estoque LIKE ?");

		PreparedStatement ps = con.prepareStatement(sql.toString());
		// "%" Para fazer buscas aproximadas
		ps.setString(1, "%" + nome + "%");
		// ps.setString(1, nome);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			SobraMaterial sm = new SobraMaterial();
			Material m = new Material();
			m.setCodigo(rs.getInt("codigoMaterial"));
			m.setNome(rs.getString("nomeMaterial"));

			sm.setCodigo(rs.getInt("codigo"));
			sm.setEstoque(rs.getString("estoque"));
			sm.setData(rs.getDate("data"));
			sm.setLargura(rs.getString("largura"));
			sm.setComprimento(rs.getString("comprimento"));
			sm.setEspessura(rs.getString("espessura"));

			sm.setMaterial(m);
			sobraMateriais.add(sm);
		}

		rs.close();
		ps.close();
		con.close();

		return sobraMateriais;
	}
	
	@Override
	public List<SobraMaterial> filtrar(SobraMaterial filtro) throws SQLException, ClassNotFoundException {

	    List<SobraMaterial> lista = new ArrayList<>();

	    Connection c = gDao.getConnection();

	    String sql = "SELECT sm.codigo, sm.estoque, sm.largura, sm.comprimento, sm.espessura, sm.data, "
	               + "m.codigo AS matCodigo, m.nome AS matNome "
	               + "FROM SobraMaterial sm "
	               + "INNER JOIN Material m ON sm.material = m.codigo "
	               + "WHERE 1 = 1 ";

	    List<Object> params = new ArrayList<>();

	    // --- filtros opcionais ---
	    if (filtro.getEstoque() != null && !filtro.getEstoque().isEmpty()) {
	        sql += " AND sm.estoque LIKE ? ";
	        params.add("%" + filtro.getEstoque() + "%");
	    }

	    if (filtro.getMaterial() != null && filtro.getMaterial().getCodigo() > 0) {
	        sql += " AND sm.material = ? ";
	        params.add(filtro.getMaterial().getCodigo());
	    }

	    if (filtro.getLargura() != null && !filtro.getLargura().isEmpty()) {
	        sql += " AND sm.largura = ? ";
	        params.add(filtro.getLargura());
	    }

	    if (filtro.getComprimento() != null && !filtro.getComprimento().isEmpty()) {
	        sql += " AND sm.comprimento = ? ";
	        params.add(filtro.getComprimento());
	    }

	    if (filtro.getEspessura() != null && !filtro.getEspessura().isEmpty()) {
	        sql += " AND sm.espessura = ? ";
	        params.add(filtro.getEspessura());
	    }

	    PreparedStatement ps = c.prepareStatement(sql);

	    // aplica os parâmetros
	    for (int i = 0; i < params.size(); i++) {
	        ps.setObject(i + 1, params.get(i));
	    }

	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {

	        Material m = new Material();
	        m.setCodigo(rs.getInt("matCodigo"));
	        m.setNome(rs.getString("matNome"));

	        SobraMaterial sm = new SobraMaterial();
	        sm.setCodigo(rs.getInt("codigo"));
	        sm.setEstoque(rs.getString("estoque"));
	        sm.setLargura(rs.getString("largura"));
	        sm.setComprimento(rs.getString("comprimento"));
	        sm.setEspessura(rs.getString("espessura"));
	        sm.setData(rs.getDate("data"));
	        sm.setMaterial(m);

	        lista.add(sm);
	    }

	    rs.close();
	    ps.close();
	    c.close();

	    return lista;
	}

	
	
}