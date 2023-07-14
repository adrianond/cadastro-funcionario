package br.com.repository.interfaces.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.project.model.classes.ArquivoUpload;
import br.com.repository.interfaces.RepositoryArquivoUpload;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional
@Slf4j
public class RepositoryArquivoUploadImpl extends ImplementacaoCrud<ArquivoUpload> implements RepositoryArquivoUpload {

	private static final long serialVersionUID = 1L;

	@Override
	public void save(ArquivoUpload arquivoUpload) {
		try {
			super.persist(arquivoUpload);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}

}
