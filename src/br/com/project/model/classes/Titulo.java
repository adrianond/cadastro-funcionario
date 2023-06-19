package br.com.project.model.classes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import br.com.project.annotation.IdentificaCampoPesquisa;
import br.com.project.enums.TipoTitulo;
import br.com.project.enums.TituloOrigem;

@Audited
@Entity
@Table(name = "titulo")
@SequenceGenerator(name = "titulo_seq", sequenceName = "titulo_seq", initialValue = 1, allocationSize = 1)
public class Titulo implements Serializable {

	private static final long serialVersionUID = 1L;

	@IdentificaCampoPesquisa(descricaoCampo = "Codigo", campoConsulta = "tit_codigo")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titulo_seq")
	private Long tituloCodigo;

	@IdentificaCampoPesquisa(descricaoCampo = "Documento", campoConsulta = "tit_documento")
	@Column(length = 18)
	private String tituloDocumento;

	@IdentificaCampoPesquisa(descricaoCampo = "Tipo", campoConsulta = "tit_tipo")
	@Enumerated(EnumType.STRING)
	private TipoTitulo tituloTipo;

	@IdentificaCampoPesquisa(descricaoCampo = "Origem", campoConsulta = "tit_origem")
	@Enumerated(EnumType.STRING)
	private TituloOrigem tituloOrigem;

	@IdentificaCampoPesquisa(descricaoCampo = "Vencimento", campoConsulta = "tit_datavencimento")
	@Temporal(TemporalType.DATE)
	private Date tituloDatavencimento;

	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date tituloDatahora = new Date();

	@IdentificaCampoPesquisa(descricaoCampo = "Data da baixa", campoConsulta = "tit_databaixa")
	@Temporal(TemporalType.DATE)
	private Date tituloDatabaixa;

	@IdentificaCampoPesquisa(descricaoCampo = "Usuario abertura", campoConsulta = "ent_codigoabertura.ent_nomefantasia", principal = 1)
	@Basic
	@ManyToOne
	@JoinColumn(nullable = false, name = "ent_codigoabertura", updatable = false)
	@ForeignKey(name = "ent_codigoabertura_fk")
	private Entidade entCodigoAbertura = new Entidade(); 

	@IdentificaCampoPesquisa(descricaoCampo = "Usuario baixa", campoConsulta = "ent_codigobaixa.ent_nomefantasia")
	@Basic
	@ManyToOne
	@JoinColumn(nullable = true, name = "ent_codigobaixa", updatable = true)
	@ForeignKey(name = "ent_codigobaixa_fk")
	private Entidade entCodigoBaixa = new Entidade();

	@IdentificaCampoPesquisa(descricaoCampo = "Entidade responsavel", campoConsulta = "ent_codigo.ent_nomefantasia")
	@Basic
	@ManyToOne
	@JoinColumn(nullable = false, name = "ent_codigo")
	@ForeignKey(name = "ent_codigo_fk")
	private Entidade entCodigo = new Entidade();

	@IdentificaCampoPesquisa(descricaoCampo = "Valor R$", campoConsulta = "tit_valor")
	@Column(scale = 4, precision = 15)
	private BigDecimal tituloValor = null;

	@IdentificaCampoPesquisa(descricaoCampo = "Desconto em baixa", campoConsulta = "tit_descontobaixa")
	@Column(scale = 4, precision = 15)
	private BigDecimal tituloDescontoBaixa = null;

	@IdentificaCampoPesquisa(descricaoCampo = "Acr�scimo em baixa", campoConsulta = "tit_acrescimobaixa") 
	@Column(scale = 4, precision = 15)
	private BigDecimal tituloAcrescimoBaixa = null;

	@IdentificaCampoPesquisa(descricaoCampo = "Valor baixa", campoConsulta = "tit_valorbaixa")
	@Column(scale = 4, precision = 15)
	private BigDecimal tituloValorBaixa = null; 

	@IdentificaCampoPesquisa(descricaoCampo = "Observa��o", campoConsulta = "tit_observacao")
	@Column(columnDefinition = "text", length = 500)
	private String tituloObservacao;
	
	private Boolean tituloBaixado = Boolean.FALSE;
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;
	
	protected int getVersionNum() { 
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}
	
	@Transient
	private boolean selecionarParaBaixa;
	
	public void setTituloBaixado(Boolean tit_baixado) {
		this.tituloBaixado = tit_baixado;
	}
	
	public Boolean getTituloBaixado() {
		return tituloBaixado;
	}
	
	public void setSelecionarParaBaixa(boolean selecionarParaBaixa) {
		this.selecionarParaBaixa = selecionarParaBaixa;
	}

	public boolean isSelecionarParaBaixa() {
		return selecionarParaBaixa;
	}

	public Long getTitCodigo() {
		return tituloCodigo;
	}

	public void setTitCodigo(Long tit_codigo) {
		this.tituloCodigo = tit_codigo;
	}

	public Date getTituloDatahora() {
		return tituloDatahora;
	}

	public void setTituloDatahora(Date tit_datahora) {
		this.tituloDatahora = tit_datahora;
	}

	public TipoTitulo getTituloTipo() {
		return tituloTipo;
	}

	public void setTituloTipo(TipoTitulo tit_tipo) {
		this.tituloTipo = tit_tipo;
	}

	public TituloOrigem getTituloOrigem() {
		return tituloOrigem;
	}

	public void setTituloOrigem(TituloOrigem tit_origem) {
		this.tituloOrigem = tit_origem;
	}

	public Date getTituloDatavencimento() {
		return tituloDatavencimento;
	}

	public void setTituloDatavencimento(Date tit_datavencimento) {
		this.tituloDatavencimento = tit_datavencimento;
	}

	public Entidade getEntCodigoAbertura() {
		return entCodigoAbertura;
	}

	public void setEntCodigoAbertura(Entidade ent_codigoabertura) {
		this.entCodigoAbertura = ent_codigoabertura;
	}

	public Entidade getEntCodigo() {
		return entCodigo;
	}

	public void setEntCodigo(Entidade ent_codigo) {
		this.entCodigo = ent_codigo;
	}

	public BigDecimal getTituloValor() {
		return tituloValor;
	}

	public void setTituloValor(BigDecimal tit_valor) {
		this.tituloValor = tit_valor;
	}

	public String getTituloDocumento() {
		return tituloDocumento;
	}

	public void setTituloDocumento(String tit_documento) {
		this.tituloDocumento = tit_documento;
	}

	public BigDecimal getTituloDescontoBaixa() {
		return tituloDescontoBaixa;
	}

	public void setTituloDescontoBaixa(BigDecimal tit_descontobaixa) {
		this.tituloDescontoBaixa = tit_descontobaixa;
	}

	public BigDecimal getTituloAcrescimoBaixa() {
		return tituloAcrescimoBaixa;
	}

	public void setTituloAcrescimoBaixa(BigDecimal tit_acrescimobaixa) {
		this.tituloAcrescimoBaixa = tit_acrescimobaixa;
	}

	public BigDecimal getTituloValorBaixa() {
		return tituloValorBaixa;
	}

	public void setTituloValorBaixa(BigDecimal tit_valorbaixa) {
		this.tituloValorBaixa = tit_valorbaixa;
	}

	public Date getTituloDatabaixa() {
		return tituloDatabaixa;
	}

	public void setTituloDatabaixa(Date tit_databaixa) {
		this.tituloDatabaixa = tit_databaixa;
	}

	public String getTituloObservacao() {
		return tituloObservacao;
	}

	public void setTituloObservacao(String tit_observacao) {
		this.tituloObservacao = tit_observacao;
	}

	public Entidade getEntCodigoBaixa() {
		return entCodigoBaixa;
	}

	public void setEntCodigoBaixa(Entidade ent_codigobaixa) {
		this.entCodigoBaixa = ent_codigobaixa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tituloCodigo == null) ? 0 : tituloCodigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Titulo other = (Titulo) obj;
		if (tituloCodigo == null) {
			if (other.tituloCodigo != null)
				return false;
		} else if (!tituloCodigo.equals(other.tituloCodigo))
			return false;
		return true;
	}

}
