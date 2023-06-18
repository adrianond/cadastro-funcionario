package br.com.srv.implementacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryBairro;
import br.com.srv.interfaces.SrvBairro;

@Service
public class SrvBairroImpl implements SrvBairro {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RepositoryBairro repositoryBairro;

	public void setRepositoryBairro(RepositoryBairro repositoryBairro) {

		this.repositoryBairro = repositoryBairro;
	}
}