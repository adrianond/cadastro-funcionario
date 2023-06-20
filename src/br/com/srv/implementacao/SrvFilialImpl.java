package br.com.srv.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryFilial;
import br.com.srv.interfaces.SrvFilial;

@Service
public class SrvFilialImpl implements SrvFilial {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RepositoryFilial repositoryFilial;

	public void setRepositoryFilial(RepositoryFilial repositoryFilial) {

		this.repositoryFilial = repositoryFilial;
	}
}