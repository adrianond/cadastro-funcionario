package br.com.project.bean.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.project.geral.controller.ArquivoUploadControler;
import br.com.project.geral.controller.EntidadeController;
import br.com.project.model.classes.ArquivoUpload;
import lombok.extern.slf4j.Slf4j;

@Component
@ManagedBean(name = "uploadArquivoBean")
@Scope(value = "request")
@Slf4j
public class UploadArquivoBean {

	private ArquivoUpload arquivo = new ArquivoUpload();

	private UploadedFile uploadedFile;

	@Autowired
	private ArquivoUploadControler arquivoUploadControler;
	
	@Autowired
	private EntidadeController entidadeController;

	public void upload() throws Exception {

		try {
			byte[] data = buildUploadedFileToByteArray();
			arquivo.setDescricao(uploadedFile.getFileName());
			arquivo.setArquivo(data);
			
			arquivoUploadControler.save(arquivo);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private byte[] buildUploadedFileToByteArray() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream inputStream = uploadedFile.getInputstream();
		int nRead;
		byte[] data = new byte[4];

		while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		return buffer.toByteArray();
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
}
