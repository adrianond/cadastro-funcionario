package br.com.project.geral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.ArquivoUpload;
import br.com.repository.interfaces.RepositoryArquivoUpload;

@Controller
public class ArquivoUploadControler extends ImplementacaoCrud<ArquivoUpload> implements InterfaceCrud<ArquivoUpload> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RepositoryArquivoUpload repositoryArquivoUpload;

	public void save(ArquivoUpload arquivoUpload) {
		
		repositoryArquivoUpload.save(arquivoUpload);
	}

}
