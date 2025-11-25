package br.edu.fatec.zl.WoodManager.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import br.edu.fatec.zl.WoodManager.model.Fornecedor;
import br.edu.fatec.zl.WoodManager.persistence.FornecedorDao;
import br.edu.fatec.zl.WoodManager.persistence.GenericDao;
import br.edu.fatec.zl.WoodManager.util.Util;
import jakarta.servlet.http.HttpSession;

@Controller
public class FornecedorController {

	@Autowired
	GenericDao gDao;

	@Autowired
	FornecedorDao fDao;

	@RequestMapping(name = "fornecedor", value = "/fornecedor", method = RequestMethod.GET)
	public ModelAndView fornecedorGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String erro = "";
		String saida = "";

		List<Fornecedor> fornecedores = new ArrayList<>();
		Fornecedor f = new Fornecedor();

		try {
			String cmd = allRequestParam.get("cmd");
			String codigo = allRequestParam.get("codigo");

			if (cmd != null) {
				if (cmd.contains("alterar")) {
					f.setCodigo(Integer.parseInt(codigo));
					f = buscarFornecedor(f);

				} else if (cmd.contains("excluir")) {
					f.setCodigo(Integer.parseInt(codigo));
					saida = excluirFornecedor(f);
					f = null;

				}
				fornecedores = listarFornecedores();
			}

		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("fornecedor", f);
			model.addAttribute("fornecedores", fornecedores);
		}

		return new ModelAndView("fornecedor");
	}

	@RequestMapping(name = "fornecedor", value = "/fornecedor", method = RequestMethod.POST)
	public ModelAndView fornecedorPost(@RequestParam Map<String, String> allRequestParam, ModelMap model,
			HttpSession session) {

		String cmd = allRequestParam.get("botao");
		String codigo = allRequestParam.get("codigo");
		String nome = allRequestParam.get("nome");
		String telefone = allRequestParam.get("telefone");

		String saida = "";
		String erro = "";

		Fornecedor f = new Fornecedor();
		List<Fornecedor> fornecedores = new ArrayList<>();

		telefone = Util.removerMascara(telefone);

		if (cmd != null && !cmd.isEmpty() && cmd.contains("Limpar")) {
			f = null;

		} else if (!cmd.contains("Listar")) {
			f.setNome(nome);
		}
		try {
			if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
				if (codigo != null && !codigo.isEmpty()) {
					f.setCodigo(Integer.parseInt(codigo));
				}
				f.setNome(nome);
				f.setTelefone(telefone);
			}
			if (cmd.contains("Cadastrar")) {
				cadastrarFornecedor(f);
				saida = "Fornecedor Cadastrado com sucesso!";
				f = null;
			}
			if (cmd.contains("Alterar")) {
				alterarFornecedor(f);
				saida = "Fornecedor Alterado com sucesso!";
				f = null;
			}
			if (cmd.contains("Buscar")) {
				// Buscar equipamentos pelo nome
				fornecedores = buscarFornecedorNome(nome);
				// Verificar o número de registros retornados
				if (fornecedores.isEmpty()) {
					// Caso não encontre nenhum Equipamento
					saida = "Nenhum Fornecedor encontrado com o Nome '" + nome + "'";
				} else if (fornecedores.size() == 1) {
					Fornecedor fornecedor = fornecedores.get(0);
					saida = "Fornecedor encontrado: " + fornecedor.getNome();
					f = buscarFornecedor(fornecedor);
				} else {
					// Caso encontre mais de um Equipamento
					saida = "Foram encontrados " + fornecedores.size() + " fornecedores com o Nome '" + nome + "'";

				}
			}
			if (cmd.contains("Excluir")) {
				f = new Fornecedor();
				f.setCodigo(Integer.parseInt(codigo));
				saida = excluirFornecedor(f);
				f = null;
			}
			if (cmd.contains("Listar")) {
				fornecedores = listarFornecedores();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("fornecedor", f);
			model.addAttribute("fornecedores", fornecedores);
		}
		return new ModelAndView("fornecedor");
	}

	private String cadastrarFornecedor(Fornecedor f) throws SQLException, ClassNotFoundException {
		String saida = fDao.spManterFornecedor("I", f);
		return saida;

	}

	private String alterarFornecedor(Fornecedor f) throws SQLException, ClassNotFoundException {
		String saida = fDao.spManterFornecedor("U", f);
		return saida;

	}

	private String excluirFornecedor(Fornecedor f) throws SQLException, ClassNotFoundException {
		String saida = fDao.spManterFornecedor("D", f);
		return saida;
	}

	private Fornecedor buscarFornecedor(Fornecedor f) throws SQLException, ClassNotFoundException {
		f = fDao.findBy(f);
		return f;
	}

	private List<Fornecedor> listarFornecedores() throws SQLException, ClassNotFoundException {
		List<Fornecedor> fornecedores = fDao.findAll();
		return fornecedores;
	}

	private List<Fornecedor> buscarFornecedorNome(String nome) throws ClassNotFoundException, SQLException {
		List<Fornecedor> fornecedores = new ArrayList<>();
		fornecedores = fDao.findByName(nome);
		return fornecedores;
	}

}
