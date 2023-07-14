package br.com.project.geral.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.enums.TipoTitulo;
import br.com.project.enums.TituloOrigem;
import br.com.project.model.classes.Titulo;
import br.com.repository.interfaces.RepositoryTitulo;

@Controller
public class TituloController extends ImplementacaoCrud<Titulo> implements InterfaceCrud<Titulo> {
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private RepositoryTitulo repositoryTitulo;
	
	
	
	@RequestMapping("**/gerarGraficoInicial")
	public @ResponseBody String gerarGraficoInicial(@RequestParam(value = "dias") int dias) {
		
		List<Map<String, Object>> titulosUltimosDias =  getTitulosUltimosDias(dias);
		int quantidadeLinhas = titulosUltimosDias.size() + 1;
		String[] dados = new String[quantidadeLinhas]; 
		int contador = 0;
		
		if (dados.length == 0)
			dados[contador++] = "[\"" + "Sem Dados" + "\"," + "\"" + 0 + "\"," + "\"" + 0 + "\"]";
		else {
			dados[contador] = "[\"" + "Tipo" + "\"," + "\"" + "Quantidade" + "\"," + "\"" + "Média" + "\"]";
			contador++;
			for (Map<String, Object> objeto : titulosUltimosDias) {
				
				dados[contador] = "[\"" + objeto.get("situacao") + "\"," 
						+ "\"" + objeto.get("quantidade") + "\"," + "\"" 
						+ objeto.get("media") + "\"]";
				
				contador++;
			}
		}
		return Arrays.toString(dados);
		
	}


	public void setRepositoryTitulo(RepositoryTitulo repositoryTitulo) {
		this.repositoryTitulo = repositoryTitulo;
	}

	public List<SelectItem> getListTipoTitulo() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoTitulo tipoTitulo : TipoTitulo.values()) {
			items.add(new SelectItem(tipoTitulo.name(), tipoTitulo.toString()));
		}
		return items;
	}
	
	public List<SelectItem> getListTipoTituloOrigem() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TituloOrigem tipoTitulo : TituloOrigem.values()) {
			items.add(new SelectItem(tipoTitulo.name(), tipoTitulo.toString()));
		}
		return items;
	}
	
	public List<Map<String, Object>> getMediaPorOrigem(int dias) {
		return repositoryTitulo.getMediaPorOrigem(dias);
	}
	
	public List<Map<String, Object>> getMediaPorTipoReceberPagar(int dias) {
		return repositoryTitulo.getMediaPorTipoReceberPagar(dias);
	}
	
	public List<Map<String, Object>> getTitulosUltimosDias(int dias) {
		return repositoryTitulo.getTitulosUltimosDias(dias);
	}
	
	public List<Map<String, Object>> getMediaPorTipoAbertoFechado(int dias) {
		return repositoryTitulo.getMediaPorTipoAbertoFechado(dias);
	}

}
