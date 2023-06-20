package br.com.project.bean.geral;

import org.hibernate.Query;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.stereotype.Component;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.annotation.IdentificaCampoPesquisa;
import br.com.project.enums.CondicaoPesquisa;
import br.com.project.enums.TipoCadastro;
import br.com.project.enums.TituloSituacao;
import br.com.project.report.util.BeanReportView;
import br.com.project.util.all.Messagens;
import br.com.project.util.all.UtilitariaRegex;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.JoinColumn;

@Component
public abstract class BeanManagedViewAbstract extends BeanReportView {

	private static final long serialVersionUID = 1L;

	public ObjetoCampoConsulta campoPesquisaSelecionado;

	public CondicaoPesquisa condicaoPesquisaSelecionado;

	public List<SelectItem> listaCampoPesquisa;

	public List<SelectItem> listaCondicaoPesquisa;

	public String valorPesquisa;
	
	private HtmlInputHidden htmlInputHidden;

	public HtmlInputHidden getHtmlInputHidden() {
		return htmlInputHidden;
	}

	public void setHtmlInputHidden(HtmlInputHidden htmlInputHidden) {
		this.htmlInputHidden = htmlInputHidden;
	}

	private HtmlInputHidden htmlInputHiddenTitulo;

	protected abstract Class<?> getClassImplement();

	protected abstract InterfaceCrud<?> getController();

	public abstract String condicaoAndParaPesquisa() throws Exception;

	public ObjetoCampoConsulta getCampoPesquisaSelecionado() {
		return campoPesquisaSelecionado;
	}

	/**
	 * Monta o restante a pesquisa atraves da opção selecionada
	 * 
	 * @param campoPesquisaSelecionado
	 */
	public void setCampoPesquisaSelecionado(ObjetoCampoConsulta campoPesquisaSelecionado) {
		if (campoPesquisaSelecionado != null) {
			for (Field field : getClassImplement().getDeclaredFields()) {
				if (field.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
					if (campoPesquisaSelecionado.getCampoBanco().equalsIgnoreCase(field.getName())) {
						String descricaoCampo = field.getAnnotation(IdentificaCampoPesquisa.class).descricaoCampo();
						campoPesquisaSelecionado.setDescricao(descricaoCampo);
						campoPesquisaSelecionado.setTipoClass(field.getType().getCanonicalName());
						break;
					}
				}
			}
		}
		this.campoPesquisaSelecionado = campoPesquisaSelecionado;
	}

	public CondicaoPesquisa getCondicaoPesquisaSelecionado() {
		return condicaoPesquisaSelecionado;
	}

	public void setCondicaoPesquisaSelecionado(CondicaoPesquisa condicaoPesquisaSelecionado) {
		this.condicaoPesquisaSelecionado = condicaoPesquisaSelecionado;
	}

	public String getValorPesquisa() {
		return valorPesquisa != null ? new UtilitariaRegex().retiraAcentos(valorPesquisa.trim()) : "";
	}

	public void setValorPesquisa(String valorPesquisa) {
		this.valorPesquisa = valorPesquisa;

	}

	/**
	 * 
	 * @return List<SelectItem> para o combo de condição de pesquisa
	 */
	public List<SelectItem> getListaCondicaoPesquisa() {
		listaCondicaoPesquisa = new ArrayList<SelectItem>();
		for (CondicaoPesquisa enumCp : CondicaoPesquisa.values()) {
			listaCondicaoPesquisa.add(new SelectItem(enumCp, enumCp.toString()));
		}
		return listaCondicaoPesquisa;
	}

	/**
	 * 
	 * @return List<SelectItem> para combo de campo da tela de pesquisa
	 */
	public List<SelectItem> getListaCampoPesquisa() {
		listaCampoPesquisa = new ArrayList<SelectItem>();
		List<ObjetoCampoConsulta> listTemp = new ArrayList<ObjetoCampoConsulta>();

		for (Field field : getClassImplement().getDeclaredFields()) {
			if (field.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
				String descricaoCampo = field.getAnnotation(IdentificaCampoPesquisa.class).descricaoCampo();
				String descricaoCampoConsulta = field.getAnnotation(IdentificaCampoPesquisa.class).campoConsulta();
				int isPrincipal = field.getAnnotation(IdentificaCampoPesquisa.class).principal();

				ObjetoCampoConsulta objetoCampoConsulta = new ObjetoCampoConsulta();
				objetoCampoConsulta.setDescricao(descricaoCampo);
				objetoCampoConsulta.setCampoBanco(descricaoCampoConsulta);
				objetoCampoConsulta.setTipoClass(field.getType().getCanonicalName());
				objetoCampoConsulta.setPrincipal(isPrincipal);

				listTemp.add(objetoCampoConsulta);
			}
		}

		ordernarReverse(listTemp);

		for (ObjetoCampoConsulta objetoCampoConsulta : listTemp) {
			listaCampoPesquisa.add(new SelectItem(objetoCampoConsulta));
		}
		return listaCampoPesquisa;
	}

	private void ordernarReverse(List<ObjetoCampoConsulta> listTemp) {
		Collections.sort(listTemp, new Comparator<ObjetoCampoConsulta>() {

			@Override
			public int compare(ObjetoCampoConsulta objeto1, ObjetoCampoConsulta objeto2) {
				return objeto1.getPrincipal().compareTo(objeto2.getPrincipal());
			}
		});

		// Collections.reverse(listTemp);
	}

	protected String getSqlLazyQuery() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" select entity from ");
		sql.append(getQueryConsulta());
		sql.append(" order by entity.");
		sql.append(campoPesquisaSelecionado.getCampoBanco());
		return sql.toString();
	}

	protected int totalRegistroConsulta() throws Exception {
		Query query = getController().obterQuery("select count(entity) from " + getQueryConsulta());
		Number result = (Number) query.uniqueResult();
		return result.intValue();
	}

	/*
	 * Retorna Query para consulta
	 */
	private StringBuilder getQueryConsulta() throws Exception {

		valorPesquisa = new UtilitariaRegex().retiraAcentos(valorPesquisa).toUpperCase();
		StringBuilder sql = new StringBuilder();
		sql.append(getClassImplement().getSimpleName());
		sql.append(" entity where ");

		sql.append("upper(cast(entity.");
		sql.append(campoPesquisaSelecionado.getCampoBanco());
		sql.append(" as text)) ");

		if (condicaoPesquisaSelecionado.name().equals(CondicaoPesquisa.IGUAL_A.name())) {
			sql.append(" = upper('");
			sql.append(valorPesquisa);
			sql.append("')");
		} else if (condicaoPesquisaSelecionado.name().equals(CondicaoPesquisa.CONTEM.name())) {
			sql.append("like '%");
			sql.append(valorPesquisa);
			sql.append("%'");
		} else if (condicaoPesquisaSelecionado.name().equals(CondicaoPesquisa.INICIA_COM.name())) {
			sql.append("like upper('");
			sql.append(valorPesquisa);
			sql.append("%')");
		} else if (condicaoPesquisaSelecionado.name().equals(CondicaoPesquisa.TERMINA_COM.name())) {
			sql.append("like upper('%");
			sql.append(valorPesquisa);
			sql.append("')");
		}
		sql.append(" ");
		sql.append(condicaoAndParaPesquisa());
		return sql;
	}
	
	
	protected boolean validarCampoObrigatorio(Object object) throws IllegalArgumentException, IllegalAccessException {
		List<String> msgValidacao = new ArrayList<String>();
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)
					|| field.isAnnotationPresent((Class<? extends Annotation>) JoinColumn.class)) {
				if ((field.getAnnotation(Column.class) != null && !field
						.getAnnotation(Column.class).nullable())
						|| (field.getAnnotation(JoinColumn.class) != null && !field
								.getAnnotation(JoinColumn.class).nullable())) {
					String valor = field.get(object) != null ? (String) field
							.get(object).toString().trim() : null;
					if (valor == null
							|| (valor != null && valor.trim().isEmpty())) {
						if (field
								.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
							String descricaoCampo = field.getAnnotation(
									IdentificaCampoPesquisa.class)
									.descricaoCampo();
							msgValidacao.add(descricaoCampo + "\n");
						} else {
							msgValidacao.add(field.getName() + "\n");
						}
					}
				}
			}
		}

		if (!msgValidacao.isEmpty()) {
			Messagens.msgSeverityWarn("Informe os campos :\n"
					+ msgValidacao.toString().replace("\\,]", "]"));
			return false;
		}

		return true;
	}
	
	/**
	 * Retorna o sql para consultar todos os registros ou apenas ativos
	 * @return String
	 */
	protected String consultarInativos() {
		String retorno = " and entity.ent_inativo is false ";
		boolean consultar = false;
		try {
			Map<String, String> params = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			String consultarInativos = params.get("consultarInativos");

			if (consultarInativos == null) {
				return retorno;
			}

			consultar = Boolean.valueOf(consultarInativos);

		} catch (Exception e) {
			return retorno;
		}

		if (consultar) {
			return "";
		} else {
			return retorno;
		}

	}
	
	
	public Object onRowSelect(SelectEvent event) {
		return  event.getObject();
	}

	public Object onRowUnselect(UnselectEvent event) {
		return  event.getObject();
	}
	
	public TituloSituacao getTituloSituacaoTemp() {

		if (htmlInputHiddenTitulo == null
				|| (htmlInputHiddenTitulo != null && htmlInputHiddenTitulo
						.getAttributes() == null)) {
			return null;
		}

		String tipoEntidade = (String) htmlInputHiddenTitulo.getAttributes()
				.get("tipoTituloEmAberto");

		if (tipoEntidade == null) {
			return null;
		}

		if (tipoEntidade.equals(TituloSituacao.TITULO_ABERTO.name())) {
			return TituloSituacao.TITULO_ABERTO;
		} else if (tipoEntidade.equals(TituloSituacao.TITULO_BAIXADO.name())) {
			return TituloSituacao.TITULO_BAIXADO;
		} else {
			return null;
		}
	}
	
	protected boolean consultarInativosBoolean() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String consultarInativos = params.get("consultarInativos");

		if (consultarInativos == null) {
			return false;
		}

		return Boolean.valueOf(consultarInativos);
	}
	
	public TipoCadastro getTipoEntidadeTemp() {

		String tipoEntidade = null;

		if (htmlInputHidden == null) {
			return null;
		}
		if ((htmlInputHidden != null && htmlInputHidden.getAttributes() == null || htmlInputHidden
				.getAttributes().isEmpty())) {
			return null;
		}

		if (htmlInputHidden.getAttributes() != null
				&& htmlInputHidden.getAttributes().isEmpty()) {
			return null;
		}

		try {
			tipoEntidade = (String) htmlInputHidden.getAttributes().get(
					"tipoEntidadeTemp");
		} catch (Exception e) {
			// execeção omitida
		}

		if (tipoEntidade == null) {
			return null;
		}

		if (tipoEntidade.equals(TipoCadastro.TIPO_CADASTRO_CLIENTE.name())) {
			return TipoCadastro.TIPO_CADASTRO_CLIENTE;
		} else if (tipoEntidade.equals(TipoCadastro.TIPO_CADASTRO_CONSTRUTORA
				.name())) {
			return TipoCadastro.TIPO_CADASTRO_CONSTRUTORA;
		} else if (tipoEntidade.equals(TipoCadastro.TIPO_CADASTRO_FORNECEDOR
				.name())) {
			return TipoCadastro.TIPO_CADASTRO_FORNECEDOR;
		} else if (tipoEntidade.equals(TipoCadastro.TIPO_CADASTRO_FUNCIONARIO
				.name())) {
			return TipoCadastro.TIPO_CADASTRO_FUNCIONARIO;
		} else
			return TipoCadastro.TIPO_CADASTRO_VENDEDOR;
	}
}
