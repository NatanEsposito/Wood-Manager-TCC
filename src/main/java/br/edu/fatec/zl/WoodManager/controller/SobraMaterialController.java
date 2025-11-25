package br.edu.fatec.zl.WoodManager.controller;

import java.sql.Date;
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
import br.edu.fatec.zl.WoodManager.model.Material;
import br.edu.fatec.zl.WoodManager.model.SobraMaterial;
import br.edu.fatec.zl.WoodManager.persistence.FornecedorDao;
import br.edu.fatec.zl.WoodManager.persistence.GenericDao;
import br.edu.fatec.zl.WoodManager.persistence.MaterialDao;
import br.edu.fatec.zl.WoodManager.persistence.SobraMaterialDao;
import br.edu.fatec.zl.WoodManager.persistence.TipoMaterialDao;

@Controller
public class SobraMaterialController {

	@Autowired
	GenericDao gDao;

	@Autowired
	FornecedorDao fDao;

	@Autowired
	MaterialDao mDao;

	@Autowired
	SobraMaterialDao smDao;

	@Autowired
	TipoMaterialDao tmDao;

	// Método que lida com requisições GET para a página de material
	@RequestMapping(name = "sobramaterial", value = "/sobramaterial", method = RequestMethod.GET)
	public ModelAndView sobramaterialget(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		// Declaração de variáveis para mensagens de erro e saída
		String erro = "";
		String saida = "";

		// Listas para armazenar dados de materiais, e tipos de materiais
		List<Material> materiais = new ArrayList<>();

		// Objeto Sobra Material que será preenchido com o resultado da busca
		SobraMaterial sm = null;

		try {
			// Pega os parâmetros da requisição
			String cmd = allRequestParam.get("cmd");
			String codigo = allRequestParam.get("codigo");

			// Lista os fornecedores e tipos de materiais disponíveis
			materiais = listarMateriais();

			// Verifica o valor de "cmd" para determinar a ação a ser executada
			if (cmd != null) {
				if (cmd.contains("alterar")) {
					// Se o comando for "alterar", busca o material com o código fornecido
					sm = new SobraMaterial(); // Inicializa o objeto Material
					sm.setCodigo(Integer.parseInt(codigo)); // Define o código do material
					sm = buscarSobraMaterial(sm); // Busca o material no banco de dados

				} else if (cmd.contains("excluir")) {
					// Se o comando for "excluir", busca e exclui o material
					sm = new SobraMaterial(); // Inicializa o objeto Material
					sm.setCodigo(Integer.parseInt(codigo)); // Define o código do material
					sm = buscarSobraMaterial(sm); // Busca o material
					saida = excluirSobraMaterial(sm); // Exclui o material e armazena a mensagem de saída
					sm = null; // Limpa o objeto material após exclusão
				}
				// Atualiza a lista de materiais após a exclusão ou alteração
				materiais = listarMateriais();

			}

		} catch (ClassNotFoundException | SQLException error) {
			// Captura e armazena mensagens de erro de SQL ou de classe não encontrada
			erro = error.getMessage();
		} finally {
			// Atribui os atributos ao modelo para disponibilizá-los na página de
			// visualização
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("sobramaterial", sm);
			model.addAttribute("materiais", materiais);

		}

		// Retorna a view "material" com o modelo preenchido
		return new ModelAndView("sobramaterial");
	}

	// Método que lida com requisições POST para a página de material
	@RequestMapping(name = "sobramaterial", value = "/sobramaterial", method = RequestMethod.POST)
	public ModelAndView sobramaterialPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		// Obtenção do valor do botão acionado e dos parâmetros do formulário
		String cmd = allRequestParam.get("botao");
		String codigo = allRequestParam.get("codigo");
		String estoque = allRequestParam.get("estoque");
		String data = allRequestParam.get("data");
		String material = allRequestParam.get("material");
		String largura = allRequestParam.get("largura");
		String comprimento = allRequestParam.get("comprimento");
		String espessura = allRequestParam.get("espessura");

		String saida = ""; // Variável para armazenar mensagens de sucesso
		String erro = ""; // Variável para armazenar mensagens de erro
		SobraMaterial sm = new SobraMaterial(); // Objeto Material para manipular dados do formulário
		Material m = new Material(); // Objeto TipoMaterial para atribuir o tipo ao Material

		// Listas para armazenar dados para a View
		List<Material> materiais = new ArrayList<>();
		List<SobraMaterial> sobraMateriais = new ArrayList<>();

		// Limpa o objeto Material se o comando for "Limpar"
		if (cmd != null && !cmd.isEmpty() && cmd.contains("Limpar")) {
			sm = null;
		} else if (!cmd.contains("Listar")) {
			sm.setEstoque(estoque); // Atribui o nome ao Material se o comando não for "Listar"
		}

		try {
			// Carrega as listas de fornecedores e tipos de materiais para popular a View
			materiais = listarMateriais();

			// Se o comando for "Cadastrar" ou "Alterar", preenche o objeto Material com os
			// dados do formulário
			if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
				if (codigo != null && !codigo.isEmpty()) {
					sm.setCodigo(Integer.parseInt(codigo));
				}
				if (material != null && !material.isEmpty()) {
					m.setCodigo(Integer.parseInt(material));
					m = buscarMaterial(m); // Busca o TipoMaterial pelo código
					sm.setMaterial(m); // Atribui o TipoMaterial ao Material
				}

				sm.setEstoque(estoque);
				sm.setLargura(largura);
				sm.setComprimento(comprimento);
				sm.setEspessura(espessura);
				sm.setData(Date.valueOf(data)); // Converte a data para o tipo Date

			}
			// Ações de cadastro, atualização, exclusão e busca
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarSobraMaterial(sm); // Chama o método para cadastrar a Sobra Material
				sm = null;
			}
			if (cmd.contains("Alterar")) {
				saida = alterarSobraMaterial(sm); // Chama o método para atualizar a Sobra Material
				sm = null;
			}
			if (cmd.contains("Excluir")) {
				sm = new SobraMaterial();
				sm.setCodigo(Integer.parseInt(codigo));
				m.setCodigo(Integer.parseInt(material));
				m = buscarMaterial(m);
				sm.setMaterial(m);
				saida = excluirSobraMaterial(sm); // Chama o método para excluir o Material
				m = null;
			}
			if (cmd.contains("Buscar")) {
				// Busca os materiais pelo nome fornecido
				sobraMateriais = buscarSobraMaterialNome(estoque);
				// Mensagens de retorno de acordo com o número de registros encontrados
				if (sobraMateriais.isEmpty()) {
					saida = "Nenhuma de Material encontrado no Estoque '" + estoque + "'";
				} else if (sobraMateriais.size() == 1) {
					SobraMaterial sobraMaterial = sobraMateriais.get(0);
					saida = "Sobra Materiais encontrado: " + sobraMaterial.getEstoque();
					sm = buscarSobraMaterial(sobraMaterial); // Carrega os dados do Material encontrado
				} else {
					saida = "Foram encontrados " + sobraMateriais.size() + " Materiais com o Nome '" + estoque + "'";
				}
			}
			if (cmd.contains("Listar")) {
				sobraMateriais = listarSobrasMateriais(); // Lista todos os materiais
			}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage(); // Captura mensagens de erro e as armazena
		} finally {
			// Adiciona dados ao Model para serem exibidos na View
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("sobramaterial", sm);
			model.addAttribute("materiais", materiais);
			model.addAttribute("sobraMateriais", sobraMateriais);

		}

		return new ModelAndView("sobramaterial"); // Retorna a View "material" com os dados preenchidos no ModelMap
	}

	// Método para cadastrar um novo Material
	private String cadastrarSobraMaterial(SobraMaterial sm) throws SQLException, ClassNotFoundException {
		// Chama o método spManterMaterial do DAO com a operação "I" (inserção) e o
		// objeto Sobra Material
		String saida = smDao.spManterSobraMaterial("I", sm);
		return saida; // Retorna a saída (mensagem de sucesso ou erro) para o controller
	}

	// Método para alterar uma SobraMaterial existente
	private String alterarSobraMaterial(SobraMaterial sm) throws SQLException, ClassNotFoundException {
		// Chama o método spManterMaterial do DAO com a operação "U" (atualização) e o
		// objeto Sobra Material
		String saida = smDao.spManterSobraMaterial("U", sm);
		return saida; // Retorna a saída para o controller
	}

	// Método para excluir uma Sobra de Material
	private String excluirSobraMaterial(SobraMaterial sm) throws SQLException, ClassNotFoundException {
		// Chama o método spManterMaterial do DAO com a operação "D" (deleção) e o
		// objeto Sobra de Material
		String saida = smDao.spManterSobraMaterial("D", sm);
		return saida; // Retorna a saída para o controller
	}

	// Método para buscar uma Sobra de Material específica
	private SobraMaterial buscarSobraMaterial(SobraMaterial sm) throws SQLException, ClassNotFoundException {
		// Chama o método findBy do DAO para buscar a sobra de material no banco de dados pelo identificador
		sm = smDao.findBy(sm);
		return sm; // Retorna o objeto Material preenchido
	}

	// Método para listar todos os Materiais
	private List<Material> listarMateriais() throws SQLException, ClassNotFoundException {
		// Chama o método findAll do DAO para obter todos os materiais
		List<Material> materiais = mDao.findAll();
		return materiais; // Retorna a lista de materiais para o controller
	}
	
	// Método para listar todos as Sobras de Materiais
	private List<SobraMaterial> listarSobrasMateriais() throws SQLException, ClassNotFoundException {
		// Chama o método findAll do DAO para obter todos as Sobras de materiais
		List<SobraMaterial> sobraMateriais = smDao.findAll();
		return sobraMateriais; // Retorna a lista de Sobra de materiais para o controller
	}

	// Método para buscar materiais pelo nome
	private List<SobraMaterial> buscarSobraMaterialNome(String nome) throws ClassNotFoundException, SQLException {
		// Inicializa a lista de materiais e chama o método findByName do DAO
		List<SobraMaterial> sobraMateriais = smDao.findByName(nome);
		return sobraMateriais; // Retorna a lista de materiais que correspondem ao nome fornecido
	}

	// Método para buscar um Material específico
	private Material buscarMaterial(Material m) throws SQLException, ClassNotFoundException {
		// Chama o método findBy do DAO para buscar o material no banco de dados pelo
		// identificador
		m = mDao.findBy(m);
		return m; // Retorna o objeto Material preenchido
	}

}