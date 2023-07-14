package br.com.project.model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;
import org.primefaces.json.JSONObject;

import br.com.project.annotation.IdentificaCampoPesquisa;
import br.com.project.enums.TipoCadastro;
import br.com.project.enums.TipoPessoa;

@Audited
@Entity
@Table(name = "entidade")
@SequenceGenerator(name = "entidade_seq", sequenceName = "entidade_seq", initialValue = 1, allocationSize = 1)
public class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@IdentificaCampoPesquisa(descricaoCampo = "Codigo", campoConsulta = "ent_codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entidade_seq")
	private Long ent_codigo;

	private String ent_login = null;

	private String ent_senha;

	private String ent_cpf;
	
	@Column(nullable = true)
	private Boolean ent_mudarsenha;
	
	private boolean ent_inativo = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ent_datacadastro;
	
	@Temporal(TemporalType.DATE)
	private Date ent_datanascimento;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Nome Fantasia", campoConsulta = "ent_nomefantasia", principal = 1)
	@Column(length = 100)
	private String ent_nomefantasia;
	
	@Column(length = 15)
	private String ent_fone;

	@Column(length = 15)
	private String ent_fax;

	@Column(length = 100)
	private String ent_emailcontato;
	
	@Enumerated(EnumType.STRING)
	private TipoCadastro ent_tipo;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Cidade", campoConsulta = "cidade.cid_descricao")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@ForeignKey(name = "cid_codigo_fk")
	@JoinColumn(name = "cid_codigo")
	private Cidade cidade = new Cidade();
	
	
	@IdentificaCampoPesquisa(descricaoCampo = "Bairro", campoConsulta = "bairro.bai_descricao")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@ForeignKey(name = "bai_codigo_fk")
	@JoinColumn(name = "bai_codigo")
	private Bairro bairro = new Bairro();
	
	@IdentificaCampoPesquisa(descricaoCampo = "Filial", campoConsulta = "filial.fil_descricao")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@ForeignKey(name = "fil_codigo_fk")
	@JoinColumn(name = "fil_codigo")
	private Filial filial = new Filial();
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ent_ultimoacesso;
	
	@Column(length = 100)
	private String ent_email;
	
	@Enumerated(EnumType.STRING)
	private TipoPessoa ent_tipopessoa;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Razão Social", campoConsulta = "ent_razao", principal = 2)
	@Column(length = 100)
	private String ent_razao;
	
	@Column(length = 100)
	private String ent_endereco;
	
	@Column(length = 50)
	private String ent_complemento;
	
	@Column(length = 9)
	private String ent_cep;
	
	@Column(length = 15)
	private String ent_celular;
	
	@Column(length = 80)
	private String ent_contato;
	
	@Column(length = 250)
	private String ent_observacao;
	
	/*
	@CollectionOfElements
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@JoinTable(name = "entidadeacesso", uniqueConstraints = { @UniqueConstraint(name = "unique_acesso_entidade_key"
	, columnNames = {"ent_codigo", "esa_codigo" }) }, joinColumns = { @JoinColumn(name = "ent_codigo") })
	@Column(name = "esa_codigo", length = 20)
	private Set<String> acessos = new HashSet<String>();
	*/
	
	@OneToMany(mappedBy = "entidade", targetEntity = EntidadeAcesso.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EntidadeAcesso> entidadesAcesso = new ArrayList<>();
	

	public List<EntidadeAcesso> getEntidadesAcesso() {
		return entidadesAcesso;
	}

	public void setEntidadeAcesso(List<EntidadeAcesso> entidadesAcesso) {
		this.entidadesAcesso = entidadesAcesso;
	}

	public TipoCadastro getEnt_tipo() {
		return ent_tipo;
	}

	public void setEnt_tipo(TipoCadastro tipoCadastro) {
		this.ent_tipo = tipoCadastro;
	}

	public String getEnt_fone() {
		return ent_fone;
	}

	public void setEnt_fone(String ent_fone) {
		this.ent_fone = ent_fone;
	}

	public String getEnt_fax() {
		return ent_fax;
	}

	public void setEnt_fax(String ent_fax) {
		this.ent_fax = ent_fax;
	}

	public String getEnt_emailcontato() {
		return ent_emailcontato;
	}

	public void setEnt_emailcontato(String ent_emailcontato) {
		this.ent_emailcontato = ent_emailcontato;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getEnt_nomefantasia() {
		return ent_nomefantasia;
	}

	public void setEnt_nomefantasia(String ent_nomefantasia) {
		this.ent_nomefantasia = ent_nomefantasia;
	}

	
	public void setEnt_ultimoacesso(Date ent_ultimoacesso) {
		this.ent_ultimoacesso = ent_ultimoacesso;
	}
	
	public Date getEnt_ultimoacesso() {
		return ent_ultimoacesso;
	}
	
	public void setEnt_inativo(boolean ent_inativo) {
		this.ent_inativo = ent_inativo;
	}
	
	public boolean getEnt_inativo() {
		return ent_inativo;
	}

	public void setEnt_login(String ent_login) {
		this.ent_login = ent_login;
	}

	public String getEnt_login() {
		return ent_login;
	}

	public void setEnt_senha(String ent_senha) {
		this.ent_senha = ent_senha;
	}

	public String getEnt_senha() {
		return ent_senha;
	}
	
	public void setEnt_codigo(Long ent_codigo) {
		this.ent_codigo = ent_codigo;
	}
	
	public Long getEnt_codigo() {
		return ent_codigo;
	}
	
	public String getEnt_email() {
		return ent_email;
	}

	public void setEnt_email(String ent_email) {
		this.ent_email = ent_email;
	}
	
	public Date getEnt_datacadastro() {
		return ent_datacadastro;
	}

	public void setEnt_datacadastro(Date ent_datacadastro) {
		this.ent_datacadastro = ent_datacadastro;
	}
	
	public TipoPessoa getEnt_tipopessoa() {
		return ent_tipopessoa;
	}

	public void setEnt_tipopessoa(TipoPessoa ent_tipopessoa) {
		this.ent_tipopessoa = ent_tipopessoa;
	}
	
	public String getEnt_razao() {
		return ent_razao;
	}

	public void setEnt_razao(String ent_razao) {
		this.ent_razao = ent_razao;
	}
	
	public Date getEnt_datanascimento() {
		return ent_datanascimento;
	}

	public void setEnt_datanascimento(Date ent_datanascimento) {
		this.ent_datanascimento = ent_datanascimento;
	}
	
	public String getEnt_endereco() {
		return ent_endereco;
	}

	public void setEnt_endereco(String ent_endereco) {
		this.ent_endereco = ent_endereco;
	}
	
	public Boolean getEnt_mudarsenha() {
		return ent_mudarsenha;
	}

	public void setEnt_mudarsenha(Boolean ent_mudarsenha) {
		this.ent_mudarsenha = ent_mudarsenha;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public String getEnt_complemento() {
		return ent_complemento;
	}

	public void setEnt_complemento(String ent_complemento) {
		this.ent_complemento = ent_complemento;
	}

	public String getEnt_cep() {
		return ent_cep;
	}

	public void setEnt_cep(String ent_cep) {
		this.ent_cep = ent_cep;
	}

	public String getEnt_celular() {
		return ent_celular;
	}

	public void setEnt_celular(String ent_celular) {
		this.ent_celular = ent_celular;
	}

	public String getEnt_contato() {
		return ent_contato;
	}

	public void setEnt_contato(String ent_contato) {
		this.ent_contato = ent_contato;
	}

	public String getEnt_observacao() {
		return ent_observacao;
	}

	public void setEnt_observacao(String ent_observacao) {
		this.ent_observacao = ent_observacao;
	}
	
	public String getEnt_cpf() {
		return ent_cpf;
	}

	public void setEnt_cpf(String ent_cpf) {
		this.ent_cpf = ent_cpf;
	}
	

	public JSONObject getJson() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("ent_codigo", ent_codigo);
		map.put("ent_login", ent_login);
		map.put("ent_nomefantasia", ent_nomefantasia);
		return new JSONObject(map);
	}

}
