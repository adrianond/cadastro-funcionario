package br.com.project.geral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.EntidadeEntidade;
import br.com.repository.interfaces.RepositoryEntidadeEntidade;
import br.com.srv.interfaces.SrvEntidadeEntidade;

@Controller
public class EntidadeEntidadeController extends ImplementacaoCrud<EntidadeEntidade> implements InterfaceCrud<EntidadeEntidade> {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SrvEntidadeEntidade srvEntidadeEntidade;
	
	@Autowired
	private RepositoryEntidadeEntidade repositoryEntidadeEntidade;

	public EntidadeEntidadeController() {
	}

	public void setSrvEntidadeEntidade(SrvEntidadeEntidade srvEntidadeEntidade) {
		this.srvEntidadeEntidade = srvEntidadeEntidade;
	}

	public void setRepositoryEntidadeEntidade(RepositoryEntidadeEntidade repositoryEntidadeEntidade) {
		this.repositoryEntidadeEntidade = repositoryEntidadeEntidade;
	}

	public boolean possuiAssociacao(EntidadeEntidade objetoSelecionado) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) >= 1 from entidade_entidade where ");
		sql.append(" ent_codconstrutora = ").append(
				objetoSelecionado.getEnt_codconstrutora().getEnt_codigo());
		sql.append(" and ").append(" ent_codvendedor = ")
				.append(objetoSelecionado.getEnt_codvendedor().getEnt_codigo());
		return super.getJdbcTemplate().queryForObject(sql.toString(),
				Boolean.class);
	}

	public void removeAssociacao(Long ent_codigo) throws Exception {
		this.repositoryEntidadeEntidade.removeAssociacao(ent_codigo);
	}

}
