package br.com.repository.interfaces.impl;

import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.project.model.classes.Bairro;
import br.com.repository.interfaces.RepositoryBairro;

@Repository
public class RepositoryBairroImpl extends ImplementacaoCrud<Bairro> implements RepositoryBairro {

	private static final long serialVersionUID = 1L;
}