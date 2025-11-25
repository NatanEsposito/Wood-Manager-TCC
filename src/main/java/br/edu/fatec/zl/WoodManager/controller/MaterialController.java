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

import br.edu.fatec.zl.WoodManager.model.Cliente;
import br.edu.fatec.zl.WoodManager.model.Fornecedor;
import br.edu.fatec.zl.WoodManager.model.Material;
import br.edu.fatec.zl.WoodManager.model.TipoMaterial;
import br.edu.fatec.zl.WoodManager.persistence.ClienteDao;
import br.edu.fatec.zl.WoodManager.persistence.FornecedorDao;
import br.edu.fatec.zl.WoodManager.persistence.GenericDao;
import br.edu.fatec.zl.WoodManager.persistence.MaterialDao;
import br.edu.fatec.zl.WoodManager.persistence.TipoMaterialDao;

@Controller
public class MaterialController {

	@Autowired
	GenericDao gDao;

	@Autowired
	FornecedorDao fDao;

	@Autowired
	MaterialDao mDao;

	@Autowired
	TipoMaterialDao tmDao;
	
	@Autowired
	ClienteDao cDao;
	

	// Método que lida com requisições GET para a página de material
	@RequestMapping(name = "material", value = "/material", method = RequestMethod.GET)
	public ModelAndView materialget(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		// Declaração de variáveis para mensagens de erro e saída
		String erro = "";
		String saida = "";

		// Listas para armazenar dados de materiais, fornecedores e tipos de materiais
		List<Material> materiais = new ArrayList<>();
		List<Fornecedor> fornecedores = new ArrayList<>();
		List<TipoMaterial> tiposMateriais = new ArrayList<>();
		List<Cliente> clientes = new ArrayList<>();

		// Objeto Material que será preenchido com o resultado da busca
		Material m = null;

		try {
			// Pega os parâmetros da requisição
			String cmd = allRequestParam.get("cmd");
			String codigo = allRequestParam.get("codigo");

			// Lista os fornecedores e tipos de materiais disponíveis
			fornecedores = listarFornecedores();
			tiposMateriais = listarTiposMateriais();
			clientes = cDao.findAll();

			// Verifica o valor de "cmd" para determinar a ação a ser executada
			if (cmd != null) {
				if (cmd.contains("alterar")) {
					// Se o comando for "alterar", busca o material com o código fornecido
					m = new Material(); // Inicializa o objeto Material
					m.setCodigo(Integer.parseInt(codigo)); // Define o código do material
					m = buscarMaterial(m); // Busca o material no banco de dados

				} else if (cmd.contains("excluir")) {
					// Se o comando for "excluir", busca e exclui o material
					m = new Material(); // Inicializa o objeto Material
					m.setCodigo(Integer.parseInt(codigo)); // Define o código do material
					m = buscarMaterial(m); // Busca o material
					saida = excluirMaterial(m); // Exclui o material e armazena a mensagem de saída
					m = null; // Limpa o objeto material após exclusão
				}
				// Atualiza a lista de materiais após a exclusão ou alteração
				materiais = listarMateriais();
				fornecedores = listarFornecedores(); // Atualiza a lista de fornecedores
				tiposMateriais = listarTiposMateriais(); // Atualiza a lista de tipos de materiais
			}

		} catch (ClassNotFoundException | SQLException error) {
			// Captura e armazena mensagens de erro de SQL ou de classe não encontrada
			erro = error.getMessage();
		} finally {
			// Atribui os atributos ao modelo para disponibilizá-los na página de
			// visualização
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("material", m);
			model.addAttribute("materiais", materiais);
			model.addAttribute("fornecedores", fornecedores);
			model.addAttribute("tiposMateriais", tiposMateriais);
			model.addAttribute("clientes", clientes);
		}

		// Retorna a view "material" com o modelo preenchido
		return new ModelAndView("material");
	}

	// Método que lida com requisições POST para a página de material
	@RequestMapping(name = "material", value = "/material", method = RequestMethod.POST)
	public ModelAndView materialPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

		// Obtenção do valor do botão acionado e dos parâmetros do formulário
		String cmd = allRequestParam.get("botao");
		String codigo = allRequestParam.get("codigo");
		String nome = allRequestParam.get("nome");
		String preco = allRequestParam.get("preco");
		String cor = allRequestParam.get("cor");
		String data = allRequestParam.get("data");
		String fornecedor = allRequestParam.get("fornecedor");
		String cliente = allRequestParam.get("cliente");
		String tipoMaterial = allRequestParam.get("tipoMaterial");
		String largura = allRequestParam.get("largura");
		String comprimento = allRequestParam.get("comprimento");
		String espessura = allRequestParam.get("espessura");

		String saida = ""; // Variável para armazenar mensagens de sucesso
		String erro = ""; // Variável para armazenar mensagens de erro
		Material m = new Material(); // Objeto Material para manipular dados do formulário
		Fornecedor f = new Fornecedor(); // Objeto Fornecedor para atribuir o fornecedor ao Material
		TipoMaterial tm = new TipoMaterial(); // Objeto TipoMaterial para atribuir o tipo ao Material
		Cliente c = new Cliente(); // Objeto Cliente para atribuir o tipo ao Material

		// Listas para armazenar dados para a View
		List<Material> materiais = new ArrayList<>();
		List<Fornecedor> fornecedores = new ArrayList<>();
		List<TipoMaterial> tiposMateriais = new ArrayList<>();
		List<Cliente> clientes = new ArrayList<>();

		// Limpa o objeto Material se o comando for "Limpar"
		if (cmd != null && !cmd.isEmpty() && cmd.contains("Limpar")) {
			m = null;
		} else if (!cmd.contains("Listar")) {
			m.setNome(nome); // Atribui o nome ao Material se o comando não for "Listar"
		}

		try {
			// Carrega as listas de fornecedores e tipos de materiais para popular a View
			fornecedores = listarFornecedores();
			tiposMateriais = listarTiposMateriais();

			// Se o comando for "Cadastrar" ou "Alterar", preenche o objeto Material com os
			// dados do formulário
			if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
				if (codigo != null && !codigo.isEmpty()) {
					m.setCodigo(Integer.parseInt(codigo));
				}
				if (fornecedor != null && !fornecedor.isEmpty()) {
					f.setCodigo(Integer.parseInt(fornecedor));
					f = buscarFornecedor(f); // Busca o Fornecedor pelo código
					m.setFornecedor(f); // Atribui o Fornecedor ao Material
				}
				if (tipoMaterial != null && !tipoMaterial.isEmpty()) {
					tm.setCodigo(Integer.parseInt(tipoMaterial));
					tm = buscarTipoMaterial(tm); // Busca o TipoMaterial pelo código
					m.setTipoMaterial(tm); // Atribui o TipoMaterial ao Material
				}
				if (cliente != null && !cliente.isEmpty()) {
					c.setCodigo(Integer.parseInt(cliente));
					c = cDao.findBy(c); // Busca o Cliente pelo código
					m.setCliente(c); // Atribui o Cliente ao Material
				}

				m.setNome(nome);
				// Remove a máscara do campo de preço (ex.: R$ 1.000,00) e converte para float
				preco = preco.replace("R$", "").replace(".", "").replace(",", ".");
				m.setPreco(Float.parseFloat(preco));
				m.setLargura(largura);
				m.setComprimento(comprimento);
				m.setEspessura(espessura);
				m.setCor(cor);
				m.setData(Date.valueOf(data)); // Converte a data para o tipo Date

			}
			// Ações de cadastro, atualização, exclusão e busca
			if (cmd.contains("Cadastrar")) {
				saida = cadastrarMaterial(m); // Chama o método para cadastrar o Material
				m = null;
			}
			if (cmd.contains("Alterar")) {
				saida = alterarMaterial(m); // Chama o método para atualizar o Material
				m = null;
			}
			if (cmd.contains("Excluir")) {
				m = new Material();
				m.setCodigo(Integer.parseInt(codigo));
				f.setCodigo(Integer.parseInt(fornecedor));
				f = buscarFornecedor(f);
				m.setFornecedor(f);
				saida = excluirMaterial(m); // Chama o método para excluir o Material
				m = null;
			}
			if (cmd.contains("Buscar")) {
				// Busca os materiais pelo nome fornecido
				materiais = buscarMaterialNome(nome);
				// Mensagens de retorno de acordo com o número de registros encontrados
				if (materiais.isEmpty()) {
					saida = "Nenhum Material encontrado com o Nome '" + nome + "'";
				} else if (materiais.size() == 1) {
					Material material = materiais.get(0);
					saida = "Insumo encontrado: " + material.getNome();
					m = buscarMaterial(material); // Carrega os dados do Material encontrado
				} else {
					saida = "Foram encontrados " + materiais.size() + " Materiais com o Nome '" + nome + "'";
				}
			}
			if (cmd.contains("Listar")) {
				materiais = listarMateriais(); // Lista todos os materiais
				clientes = cDao.findAll(); //Lista todos os clientes
			}

		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage(); // Captura mensagens de erro e as armazena
		} finally {
			// Adiciona dados ao Model para serem exibidos na View
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("material", m);
			model.addAttribute("materiais", materiais);
			model.addAttribute("fornecedores", fornecedores);
			model.addAttribute("tiposMateriais", tiposMateriais);
			model.addAttribute("clientes", clientes);
		}

		return new ModelAndView("material"); // Retorna a View "material" com os dados preenchidos no ModelMap
	}
	
	@RequestMapping(name = "cadastrarFornecedor", value = "/cadastrarFornecedor", method = RequestMethod.POST)
	public ModelAndView cadastrarFornecedor(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
	    String nomeFornecedor = allRequestParam.get("nomeFornecedor");
	    String telefoneFornecedor = allRequestParam.get("telefoneFornecedor");

	    Fornecedor fornecedor = new Fornecedor();
	    fornecedor.setNome(nomeFornecedor);
	    fornecedor.setTelefone(telefoneFornecedor);

	    String saida = "";
	    try {
	        saida = fDao.spManterFornecedor("I",fornecedor);
	    } catch (SQLException | ClassNotFoundException e) {
	        saida = "Erro ao cadastrar fornecedor: " + e.getMessage();
	    }

	    model.addAttribute("saida", saida);
	    return new ModelAndView("material", model);
	}

	// Método para cadastrar um novo Material
	private String cadastrarMaterial(Material i) throws SQLException, ClassNotFoundException {
		// Chama o método spManterMaterial do DAO com a operação "I" (inserção) e o
		// objeto Material
		String saida = mDao.spManterMaterial("I", i);
		return saida; // Retorna a saída (mensagem de sucesso ou erro) para o controller
	}

	// Método para alterar um Material existente
	private String alterarMaterial(Material i) throws SQLException, ClassNotFoundException {
		// Chama o método spManterMaterial do DAO com a operação "U" (atualização) e o
		// objeto Material
		String saida = mDao.spManterMaterial("U", i);
		return saida; // Retorna a saída para o controller
	}

	// Método para excluir um Material
	private String excluirMaterial(Material i) throws SQLException, ClassNotFoundException {
		// Chama o método spManterMaterial do DAO com a operação "D" (deleção) e o
		// objeto Material
		String saida = mDao.spManterMaterial("D", i);
		return saida; // Retorna a saída para o controller
	}

	// Método para buscar um Material específico
	private Material buscarMaterial(Material m) throws SQLException, ClassNotFoundException {
		// Chama o método findBy do DAO para buscar o material no banco de dados pelo
		// identificador
		m = mDao.findBy(m);
		return m; // Retorna o objeto Material preenchido
	}

	// Método para listar todos os Materiais
	private List<Material> listarMateriais() throws SQLException, ClassNotFoundException {
		// Chama o método findAll do DAO para obter todos os materiais
		List<Material> materiais = mDao.findAll();
		return materiais; // Retorna a lista de materiais para o controller
	}

	// Método para buscar um Fornecedor específico
	private Fornecedor buscarFornecedor(Fornecedor f) throws SQLException, ClassNotFoundException {
		// Chama o método findBy do DAO para buscar o fornecedor pelo identificador
		f = fDao.findBy(f);
		return f; // Retorna o objeto Fornecedor preenchido
	}

	// Método para listar todos os Fornecedores
	private List<Fornecedor> listarFornecedores() throws SQLException, ClassNotFoundException {
		// Chama o método findAll do DAO para obter todos os fornecedores
		List<Fornecedor> fornecedores = fDao.findAll();
		return fornecedores; // Retorna a lista de fornecedores
	}

	// Método para buscar materiais pelo nome
	private List<Material> buscarMaterialNome(String nome) throws ClassNotFoundException, SQLException {
		// Inicializa a lista de materiais e chama o método findByName do DAO
		List<Material> materiais = mDao.findByName(nome);
		return materiais; // Retorna a lista de materiais que correspondem ao nome fornecido
	}

	// Método para listar todos os Tipos de Materiais
	private List<TipoMaterial> listarTiposMateriais() throws ClassNotFoundException, SQLException {
		// Inicializa a lista de tipos de materiais e chama o método findAll do DAO
		List<TipoMaterial> tiposMateriais = tmDao.findAll();
		return tiposMateriais; // Retorna a lista de tipos de materiais
	}

	// Método para buscar um Tipo de Material específico
	private TipoMaterial buscarTipoMaterial(TipoMaterial tm) throws ClassNotFoundException, SQLException {
	    // Chama o método findBy do DAO para buscar o tipo de material pelo identificador
	    tm = tmDao.findBy(tm);
	    return tm; // Retorna o objeto TipoMaterial preenchido
	}
}