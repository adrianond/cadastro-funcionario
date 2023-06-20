package br.com.project.model.classes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.NoArgsConstructor;

@Audited
@Entity
@Table(name = "entidade_acesso")
@Data
@NoArgsConstructor
@SequenceGenerator(name = "entidade_acesso_seq", sequenceName = "entidade_acesso_seq", initialValue = 1, allocationSize = 1)
public class EntidadeAcesso implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entidade_acesso_seq")
	private Long codigo;

	private String descricao;
	
	@JoinColumn(name = "id_entidade", referencedColumnName = "ent_codigo", nullable = false)
	@ForeignKey(name = "codigo_entidade_fk")
	@ManyToOne(targetEntity = Entidade.class, fetch = FetchType.EAGER)
	private Entidade entidade;
	

	public EntidadeAcesso(Entidade entidade ) {
		this.setEntidade(entidade);
	}
}
