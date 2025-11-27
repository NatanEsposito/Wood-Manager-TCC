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

import br.edu.fatec.zl.WoodManager.model.Material;
import br.edu.fatec.zl.WoodManager.model.SobraMaterial;
import br.edu.fatec.zl.WoodManager.persistence.MaterialDao;
import br.edu.fatec.zl.WoodManager.persistence.SobraMaterialDao;

@Controller
public class VisualizacaoController {

    @Autowired
    MaterialDao mDao;

    @Autowired
    SobraMaterialDao smDao;

    // GET → apenas abre a tela limpa
    @RequestMapping(name = "visualizacao", value = "/visualizacao", method = RequestMethod.GET)
    public ModelAndView visualizacaoGet(ModelMap model) {

        String erro = "";
        List<Material> materiais = new ArrayList<>();

        try {
            materiais = listarMateriais();
        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        }

        model.addAttribute("materiais", materiais);
        model.addAttribute("sobraMateriais", null);
        model.addAttribute("saida", null);
        model.addAttribute("erro", erro);
        model.addAttribute("sobramaterial", new SobraMaterial());

        return new ModelAndView("visualizacao");
    }

    // POST → Buscar ou Limpar
    @RequestMapping(name = "visualizacao", value = "/visualizacao", method = RequestMethod.POST)
    public ModelAndView visualizacaoPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {

        String cmd = allRequestParam.get("botao");

        String estoque = allRequestParam.get("estoque");
        String material = allRequestParam.get("material");
        String largura = allRequestParam.get("largura");
        String comprimento = allRequestParam.get("comprimento");
        String espessura = allRequestParam.get("espessura");

        String saida = "";
        String erro = "";

        SobraMaterial filtro = new SobraMaterial();
        Material m = new Material();

        List<SobraMaterial> listaResultado = new ArrayList<>();
        List<Material> materiais = new ArrayList<>();

        try {

            materiais = listarMateriais();

            // Se clicar em LIMPAR → zera tudo
            if (cmd.equals("Limpar")) {
                model.addAttribute("sobramaterial", new SobraMaterial());
                model.addAttribute("sobraMateriais", null);
                model.addAttribute("materiais", materiais);
                return new ModelAndView("visualizacao");
            }

            // Se clicar em BUSCAR → monta o filtro
            if (cmd.equals("Buscar")) {

                if (estoque != null && !estoque.isEmpty()) {
                    filtro.setEstoque(estoque);
                }

                if (material != null && !material.equals("0")) {
                    m.setCodigo(Integer.parseInt(material));
                    filtro.setMaterial(m);
                }

                if (largura != null && !largura.isEmpty()) {
                    filtro.setLargura(largura);
                }

                if (comprimento != null && !comprimento.isEmpty()) {
                    filtro.setComprimento(comprimento);
                }

                if (espessura != null && !espessura.isEmpty()) {
                    filtro.setEspessura(espessura);
                }

                listaResultado = smDao.filtrar(filtro);

                if (listaResultado.isEmpty()) {
                    saida = "Nenhum registro encontrado para os filtros informados.";
                } else {
                    saida = listaResultado.size() + " registro(s) encontrado(s).";
                }

            }

        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        }

        // devolve dados para a view
        model.addAttribute("saida", saida);
        model.addAttribute("erro", erro);
        model.addAttribute("sobramaterial", filtro);
        model.addAttribute("sobraMateriais", listaResultado);
        model.addAttribute("materiais", materiais);

        return new ModelAndView("visualizacao");
    }


    // === MÉTODOS AUXILIARES ===

    private List<Material> listarMateriais() throws SQLException, ClassNotFoundException {
        return mDao.findAll();
    }
 // Filtro fixo para Nicho
    @RequestMapping(value = "/visualizacao/nicho", method = RequestMethod.GET)
    public ModelAndView filtroNicho(ModelMap model) {
        SobraMaterial filtro = new SobraMaterial();
        filtro.setLargura("150");
        filtro.setComprimento("450");
        return filtrarSobraMaterial(filtro, model);
    }

    // Filtro fixo para Prateleira
    @RequestMapping(value = "/visualizacao/prateleira", method = RequestMethod.GET)
    public ModelAndView filtroPrateleira(ModelMap model) {
        SobraMaterial filtro = new SobraMaterial();
        filtro.setLargura("200");
        filtro.setComprimento("900");
        return filtrarSobraMaterial(filtro, model);
    }

    // Filtro fixo para Porta Copos
    @RequestMapping(value = "/visualizacao/portacopos", method = RequestMethod.GET)
    public ModelAndView filtroPortaCopos(ModelMap model) {
        SobraMaterial filtro = new SobraMaterial();
        filtro.setLargura("100");
        filtro.setComprimento("100");
        return filtrarSobraMaterial(filtro, model);
    }

    // Método privado reutilizando a lógica de filtro existente
    private ModelAndView filtrarSobraMaterial(SobraMaterial filtro, ModelMap model) {
        List<SobraMaterial> sobraMateriais = new ArrayList<>();
        String saida = "";
        String erro = "";

        try {
            sobraMateriais = smDao.filtrar(filtro); // reutiliza seu método de filtro do DAO
            if (sobraMateriais.isEmpty()) {
                saida = "Nenhuma sobra encontrada para o filtro aplicado.";
            } else {
                saida = "Foram encontrados " + sobraMateriais.size() + " resultados.";
            }
        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        }

        model.addAttribute("sobraMateriais", sobraMateriais);
        model.addAttribute("erro", erro);
        model.addAttribute("saida", saida);
        model.addAttribute("sobramaterial", filtro); // mantém os valores no formulário

        return new ModelAndView("visualizacao");
    }
}
