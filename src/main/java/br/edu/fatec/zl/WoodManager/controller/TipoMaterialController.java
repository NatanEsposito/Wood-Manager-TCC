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
import br.edu.fatec.zl.WoodManager.model.TipoMaterial;
import br.edu.fatec.zl.WoodManager.persistence.GenericDao;
import br.edu.fatec.zl.WoodManager.persistence.TipoMaterialDao;


@Controller
public class TipoMaterialController {

	@Autowired
	GenericDao gDao;

	@Autowired
	TipoMaterialDao tmDao;

	@RequestMapping(name = "tipomaterial", value = "/tipomaterial", method = RequestMethod.GET)
	public ModelAndView tipomaterialGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		String erro = "";
		String saida = "";

		List<TipoMaterial> tiposMateriais = new ArrayList<>();
		TipoMaterial tm = null;

		try {
			String cmd = allRequestParam.get("cmd");
			String codigo = allRequestParam.get("codigo");

			if (cmd != null) {
				if (cmd.contains("alterar")) {
					// Inicializando antes de utilizá-lo
					tm = new TipoMaterial();
					tm.setCodigo(Integer.parseInt(codigo));
					tm = buscarTipoMaterial(tm);

				} else if (cmd.contains("excluir")) {
					// Inicializando antes de utilizá-lo
					tm = new TipoMaterial();
					tm.setCodigo(Integer.parseInt(codigo));
					saida = excluirTipoMaterial(tm);
					tm = null;
				}
				tiposMateriais = listarTiposMateriais();
			}

		} catch (ClassNotFoundException | SQLException error) {
			erro = error.getMessage();
		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("tipomaterial", tm);
			model.addAttribute("tiposMateriais", tiposMateriais);
		}

		return new ModelAndView("tipomaterial");
	}

	@RequestMapping(name = "tipomaterial", value = "/tipomaterial", method = RequestMethod.POST)
	public ModelAndView tipoMaterialPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		// Parâmetros de entrada
		String cmd = allRequestParam.get("botao");
		String nome = allRequestParam.get("nome");
		String codigo = allRequestParam.get("codigo");

		// Parâmetros de saída
		String saida = "";
		String erro = "";

		TipoMaterial tm = new TipoMaterial();
		List<TipoMaterial> tiposMateriais = new ArrayList<>();

		if (cmd != null && !cmd.isEmpty() && cmd.contains("Limpar")) {
			tm = null;

		} else if (!cmd.contains("Listar")) {
			tm.setNome(nome);
		}

		if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			if (codigo != null && !codigo.isEmpty()) {
				tm.setCodigo(Integer.parseInt(codigo));
			}
			tm.setNome(nome);
		}

		try {
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarTipoMaterial(tm);
				tm = null;
			}
			if (cmd.contains("Alterar")) {
				saida = alterarTipoMaterial(tm);
				tm = null;
			}
			if (cmd.contains("Excluir")) {
				tm = new TipoMaterial();
				tm.setCodigo(Integer.parseInt(codigo));
				saida = excluirTipoMaterial(tm);
				tm = null;
			}
			if (cmd.contains("Buscar")) {
				// Buscar clientes pelo nome
				tiposMateriais = buscarTipoMaterialNome(nome);
				// Verificar o número de registros retornados
				if (tiposMateriais.isEmpty()) {
					// Caso não encontre nenhuma Categoria
					saida = "Nenhum Tipo de Material  encontrado com o Nome '" + nome + "'";
				} else if (tiposMateriais.size() == 1) {
					TipoMaterial tipoMaterial = tiposMateriais.get(0);
					saida = "Patente encontrado: " + tipoMaterial.getNome();
					tm = buscarTipoMaterial(tipoMaterial);
				} else {
					// Caso encontre mais de uma patente
					saida = "Foram encontrados " + tiposMateriais.size() + " tipos de Materiais com o Nome '" + nome + "'";

				}
			}

			if (cmd.contains("Listar")) {
				tiposMateriais = listarTiposMateriais();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("tipomaterial", tm);
			model.addAttribute("tiposMateriais", tiposMateriais);
		}
		return new ModelAndView("tipomaterial");
	}

	private String cadastrarTipoMaterial(TipoMaterial p) throws ClassNotFoundException, SQLException {
		String saida = tmDao.spManterTipoMaterial("I", p);
		return saida;
	}

	private String alterarTipoMaterial(TipoMaterial p) throws ClassNotFoundException, SQLException {
		String saida = tmDao.spManterTipoMaterial("U", p);
		return saida;
	}

	private String excluirTipoMaterial(TipoMaterial p) throws ClassNotFoundException, SQLException {
		String saida = tmDao.spManterTipoMaterial("D", p);
		return saida;
	}

	private TipoMaterial buscarTipoMaterial(TipoMaterial tm) throws ClassNotFoundException, SQLException {
		tm = tmDao.findBy(tm);
		return tm;
	}

	private List<TipoMaterial> buscarTipoMaterialNome(String nome) throws ClassNotFoundException, SQLException {
		List<TipoMaterial> patentes = new ArrayList<>();
		patentes = tmDao.findByName(nome);
		return patentes;
	}

	private List<TipoMaterial> listarTiposMateriais() throws ClassNotFoundException, SQLException {
		List<TipoMaterial> tiposMateriais = new ArrayList<>();
		tiposMateriais = tmDao.findAll();
		return tiposMateriais;
	}
}
