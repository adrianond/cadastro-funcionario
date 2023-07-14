package br.com.project.bean.view;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagedViewAbstract;
import br.com.project.carregamento.lazy.CarregamentoLazyListForObject;
import br.com.project.geral.controller.CidadeController;
import br.com.project.model.classes.Cidade;

@Controller
@Scope(value = "session")
@ManagedBean(name = "cidadeBeanView")
public class CidadeBeanView extends BeanManagedViewAbstract {

	private static final long serialVersionUID = 1L;

	private String url = "/cadastro/cad_cidade.jsf?faces-redirect=true";

	private String urlFind = "/cadastro/find_cidade.jsf?faces-redirect=true";

	private Cidade objetoSelecionado = new Cidade();

	private CarregamentoLazyListForObject<Cidade> list = new CarregamentoLazyListForObject<Cidade>();

	@Autowired
	private CidadeController cidadeController;

	@Override
	public StreamedContent getArquivoReport() throws Exception {
		super.setNomeRelatorioJasper("report_cidade");
		super.setNomeRelatorioSaida("report_cidade");
		super.setListDataBeanColletionReport(cidadeController.findList(getClassImplement()));
		return super.getArquivoReport();
	}

	public CarregamentoLazyListForObject<Cidade> getList() throws Exception {
		return list;
	}

	@Override
	public String save() throws Exception {
		objetoSelecionado = cidadeController.merge(objetoSelecionado);
		return "";
	}

	@Override
	public void saveNotReturn() throws Exception {
		list.clear();
		objetoSelecionado = cidadeController.merge(objetoSelecionado);
		list.add(objetoSelecionado);
		objetoSelecionado = new Cidade();
		sucesso();
	}

	@Override
	public void saveEdit() throws Exception {
		// faz algum processamento
		saveNotReturn();
	}

	@Override
	public String novo() throws Exception {
		setarVariaveisNulas();
		return url;
	}

	@Override
	public void setarVariaveisNulas() throws Exception {
		list.clear();
		objetoSelecionado = new Cidade();
	}

	@Override
	public String editar() throws Exception {
		list.clear();
		return url;
	}
	
	@RequestMapping("**/findCidade")
	public void findCidade(HttpServletResponse httpServletResponse, 
			@RequestParam(value = "codCidade") Long codCidade) throws Exception {

		Cidade cidade = cidadeController.findByPorId(Cidade.class, codCidade);
		if (cidade != null) {
			httpServletResponse.getWriter().write(cidade.getJson().toString());
		}
	}
	

	@Override
	public void excluir() throws Exception {
		objetoSelecionado = (Cidade) cidadeController.getSession().get(getClassImplement(), objetoSelecionado.getCid_codigo());
		cidadeController.delete(objetoSelecionado);
		list.remove(objetoSelecionado);
		objetoSelecionado = new Cidade();
		sucesso();
	}

	@Override
	protected Class<Cidade> getClassImplement() {
		return Cidade.class;
	}

	public void setObjetoSelecionado(Cidade objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public Cidade getObjetoSelecionado() {
		return objetoSelecionado;
	}

	@Override
	public String redirecionarFindEntidade() throws Exception {
		setarVariaveisNulas();
		return urlFind;
	}

	@Override
	protected InterfaceCrud<Cidade> getController() {
		return cidadeController;
	}

	@Override
	public void consultarEntidade() throws Exception {
		objetoSelecionado = new Cidade();
		list.clear();
		list.setTotalRegistroConsulta(super.totalRegistroConsulta(), super.getSqlLazyQuery());
	}

	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		// TODO Auto-generated method stub
		return "";
	}

}
