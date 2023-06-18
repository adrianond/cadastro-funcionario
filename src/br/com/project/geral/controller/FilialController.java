package br.com.project.geral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Filial;
import br.com.repository.interfaces.RepositoryFilial;
import br.com.srv.interfaces.SrvFilial;

@Controller
public class FilialController extends ImplementacaoCrud<Filial> implements InterfaceCrud<Filial> {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SrvFilial srvFilial;
	
	@Autowired
	private RepositoryFilial repositoryFilial;

	public void setSrvFilial(SrvFilial srvFilial) {
		this.srvFilial = srvFilial;
	}

	public void setRepositoryFilial(RepositoryFilial repositoryFilial) {
		this.repositoryFilial = repositoryFilial;
	}

}
