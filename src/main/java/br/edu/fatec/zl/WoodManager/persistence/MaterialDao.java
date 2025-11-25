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
import br.edu.fatec.zl.WoodManager.interfaces.IMaterialDao;
import br.edu.fatec.zl.WoodManager.model.Cliente;
import br.edu.fatec.zl.WoodManager.model.Fornecedor;
import br.edu.fatec.zl.WoodManager.model.Material;
import br.edu.fatec.zl.WoodManager.model.TipoMaterial;

@Repository
public class MaterialDao implements ICrud<Material>, IMaterialDao {

	private GenericDao gDao;

	public MaterialDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public String spManterMaterial(String acao, Material m) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_iud_material (?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, acao);
		cs.setInt(2, m.getCodigo());
		cs.setString(3, m.getNome());
		cs.setFloat(4, m.getPreco());
		cs.setString(5, m.getCor());
		cs.setDate(6, m.getData());
		cs.setString(7, m.getLargura());
		cs.setString(8, m.getComprimento());
		cs.setString(9, m.getEspessura());
		cs.setInt(10, m.getFornecedor().getCodigo());
		cs.setInt(11, m.getTipoMaterial().getCodigo());
		cs.setInt(12, m.getCliente().getCodigo());
		cs.registerOutParameter(13, Types.VARCHAR);
		cs.execute();
		String saida = cs.getString(13);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public Material findBy(Material m) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM v_material WHERE codigo = ?");

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setInt(1, m.getCodigo());

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Fornecedor f = new Fornecedor();
			f.setCodigo(rs.getInt("codigoFornecedor"));
			f.setNome(rs.getString("nomeFornecedor"));

			TipoMaterial tm = new TipoMaterial();
			tm.setCodigo(rs.getInt("codigoTipoMaterial"));
			tm.setNome(rs.getString("nomeTipoMaterial"));
			
			Cliente c = new Cliente();
			c.setCodigo(rs.getInt("codigoCliente"));
			c.setNome(rs.getString("nomeCliente"));

			m.setCodigo(rs.getInt("codigo"));
			m.setNome(rs.getString("nome"));
			m.setPreco(rs.getFloat("preco"));
			m.setCor(rs.getString("cor"));
			m.setData(rs.getDate("data"));
			m.setLargura(rs.getString("largura"));
			m.setComprimento(rs.getString("comprimento"));
			m.setEspessura(rs.getString("espessura"));
			
			m.setFornecedor(f);
			m.setTipoMaterial(tm);
			m.setCliente(c);

			rs.close();
			ps.close();
			con.close();
			return m;
		} else {
			rs.close();
			ps.close();
			con.close();
			return null;
		}
	}

	@Override
	public List<Material> findAll() throws SQLException, ClassNotFoundException {
		List<Material> materiais = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM v_material");
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Material m = new Material();
			Fornecedor f = new Fornecedor();
			f.setCodigo(rs.getInt("codigoFornecedor"));
			f.setNome(rs.getString("nomeFornecedor"));

			TipoMaterial tm = new TipoMaterial();
			tm.setCodigo(rs.getInt("codigoTipoMaterial"));
			tm.setNome(rs.getString("nomeTipoMaterial"));
			
			Cliente c = new Cliente();
			c.setCodigo(rs.getInt("codigoCliente"));
			c.setNome(rs.getString("nomeCliente"));

			m.setCodigo(rs.getInt("codigo"));
			m.setNome(rs.getString("nome"));
			m.setPreco(rs.getFloat("preco"));
			m.setCor(rs.getString("cor"));
			m.setData(rs.getDate("data"));
			m.setLargura(rs.getString("largura"));
			m.setComprimento(rs.getString("comprimento"));
			m.setEspessura(rs.getString("espessura"));
			
			m.setFornecedor(f);
			m.setTipoMaterial(tm);
			m.setCliente(c);
			
			materiais.add(m);
		}
		rs.close();
		ps.close();
		con.close();
		return materiais;
	}

	@Override
	public List<Material> findMateriaisByOption(String opcao, String parametro)
			throws SQLException, ClassNotFoundException {
		List<Material> materiais = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT * FROM fn_buscar_insumo(?,?) ");

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, opcao);
		ps.setString(2, parametro);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Material m = new Material();
			Fornecedor f = new Fornecedor();
			f.setCodigo(rs.getInt("codigoFornecedor"));
			f.setNome(rs.getString("nomeFornecedor"));

			TipoMaterial tm = new TipoMaterial();
			tm.setCodigo(rs.getInt("codigoTipoMaterial"));
			tm.setNome(rs.getString("nomeTipoMaterial"));
			
			Cliente c = new Cliente();
			c.setCodigo(rs.getInt("codigoCliente"));
			c.setNome(rs.getString("nomeCliente"));

			m.setCodigo(rs.getInt("codigo"));
			m.setNome(rs.getString("nome"));
			m.setPreco(rs.getFloat("preco"));
			m.setCor(rs.getString("cor"));
			m.setData(rs.getDate("data"));
			m.setLargura(rs.getString("largura"));
			m.setComprimento(rs.getString("comprimento"));
			m.setEspessura(rs.getString("espessura"));
			
			m.setFornecedor(f);
			m.setTipoMaterial(tm);
			m.setCliente(c);
			materiais.add(m);
		}

		rs.close();
		ps.close();
		con.close();

		return materiais;
	}

	@Override
	public List<Material> findByName(String nome) throws SQLException, ClassNotFoundException {
		List<Material> materiais = new ArrayList<>();
		Connection con = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM v_material WHERE nome LIKE ?");

		PreparedStatement ps = con.prepareStatement(sql.toString());
		// "%" Para fazer buscas aproximadas
		ps.setString(1, "%" + nome + "%");
		// ps.setString(1, nome);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Material m = new Material();
			Fornecedor f = new Fornecedor();
			f.setCodigo(rs.getInt("codigoFornecedor"));
			f.setNome(rs.getString("nomeFornecedor"));

			TipoMaterial tm = new TipoMaterial();
			tm.setCodigo(rs.getInt("codigoTipoMaterial"));
			tm.setNome(rs.getString("nomeTipoMaterial"));
			
			Cliente c = new Cliente();
			c.setCodigo(rs.getInt("codigoCliente"));
			c.setNome(rs.getString("nomeCliente"));

			m.setCodigo(rs.getInt("codigo"));
			m.setNome(rs.getString("nome"));
			m.setPreco(rs.getFloat("preco"));
			m.setCor(rs.getString("cor"));
			m.setData(rs.getDate("data"));
			m.setLargura(rs.getString("largura"));
			m.setComprimento(rs.getString("comprimento"));
			m.setEspessura(rs.getString("espessura"));
			
			m.setFornecedor(f);
			m.setTipoMaterial(tm);
			m.setCliente(c);
			materiais.add(m);
		}

		rs.close();
		ps.close();
		con.close();

		return materiais;
	}

}