package br.com.repository.interfaces;

import java.io.Serializable;

import br.com.project.model.classes.ArquivoUpload;

public interface RepositoryArquivoUpload extends Serializable {

	public void save(ArquivoUpload arquivoUpload);
}
