package br.com.project.bean.view;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.bean.geral.BeanManagedViewAbstract;
import br.com.project.carregamento.lazy.CarregamentoLazyListForObject;
import br.com.project.geral.controller.EntidadeController;
import br.com.project.geral.controller.MensagemController;
import br.com.project.model.classes.Entidade;
import br.com.project.model.classes.Mensagem;
import br.com.project.util.all.Messagens;

@Controller
@Scope(value = "session")
@ManagedBean(name = "mensagemBeanView")
public class MensagemBeanView extends BeanManagedViewAbstract {
	private static final long serialVersionUID = 1L;

	private CarregamentoLazyListForObject<Mensagem> list = new CarregamentoLazyListForObject<Mensagem>();
	private Mensagem objetoSelecionado = new Mensagem();
	private String url = "/cadastro/cad_mensagem.jsf?faces-redirect=true";

	@Autowired
	private ContextoBean contextoBean;

	@Autowired
	private EntidadeController entidadeController;

	@Autowired
	private MensagemController mensagemController;

	@Override
	public String novo() throws Exception {
		setarVariaveisNulas();
		objetoSelecionado = new Mensagem();
		objetoSelecionado.setUsr_origem(contextoBean.getEntidadeLogada());
		//objetoSelecionado.setUsr_destino(new Entidade());
		return "";
	}

	@Override
	protected Class<?> getClassImplement() {
		return null;
	}

	@Override
	protected InterfaceCrud<?> getController() {
		return null;
	}

	@Override
	public String condicaoAndParaPesquisa() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Mensagem getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Mensagem objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	@RequestMapping("**/buscarUsuarioDestinoMsg")
	public void buscarUsuarioDestinoMsg(HttpServletResponse response,
			@RequestParam(value = "codEntidade") Long codEntidade) throws Exception {

		Entidade entidade = entidadeController.findByPorId(Entidade.class, codEntidade);
		if (null != entidade) {
			objetoSelecionado.setUsr_destino(entidade);
			response.getWriter().write(entidade.getJson().toString());
		}
	}

	@Override
	public void saveNotReturn() throws Exception {
		if (objetoSelecionado.getUsr_destino().getEnt_codigo()
				.equals(objetoSelecionado.getUsr_origem().getEnt_codigo())) {
			Messagens.msgSeverityWarn("Origem não pode ser igual ao destino.");
			return;
		}

		if (objetoSelecionado.getUsr_destino() == null || objetoSelecionado.getUsr_destino().getEnt_login() == null
				|| objetoSelecionado.getUsr_destino().getEnt_codigo() <= 0) {
			Messagens.msgSeverityWarn("Informe o usuário de destino.");
			return;
		}

		mensagemController.merge(objetoSelecionado);
		novo();
		addMsg("Mensagem enviada com sucesso.");
	}

}
