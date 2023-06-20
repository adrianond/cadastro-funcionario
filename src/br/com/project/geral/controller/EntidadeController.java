package br.com.project.geral.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.enums.TipoCadastro;
import br.com.project.model.classes.Entidade;
import br.com.srv.interfaces.SrvEntidade;

@Controller
public class EntidadeController extends ImplementacaoCrud<Entidade> implements
		InterfaceCrud<Entidade> {
	
	@Autowired
	private SrvEntidade srvEntidade;

	private static final long serialVersionUID = 1L;
	
	
	public Entidade findUserLogado(String userLogado) throws Exception {
		return super.findInuqueByProperty(Entidade.class, 
				userLogado, "ent_login", " and entity.ent_inativo is false");
	}
	
	public Date getUltimoAcessoEntidadeLogada(String login) {
		return srvEntidade.getUltimoAcessoEntidadeLogada(login);
	}

	public void updateUltimoAcessoUser(String name) {
		srvEntidade.updateUltimoAcessoUser(name);
	}
	
	public boolean existeUsuario(String ent_login) {
		return srvEntidade.existeUsuario(ent_login);
	}
	
	public Entidade findFuncionario(Long codEntidade) throws Exception {
		return findUninqueByPropertyId(Entidade.class, codEntidade,
				"ent_codigo", " and entity.ent_inativo is false and entity.ent_tipo = '"
						+ TipoCadastro.TIPO_CADASTRO_FUNCIONARIO.name() + "'");
	}

}

