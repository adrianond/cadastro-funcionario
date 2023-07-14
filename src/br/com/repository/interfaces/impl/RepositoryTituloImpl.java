package br.com.repository.interfaces.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.project.model.classes.Titulo;
import br.com.repository.interfaces.RepositoryTitulo;

@Repository
public class RepositoryTituloImpl extends ImplementacaoCrud<Titulo> implements
		RepositoryTitulo {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Map<String, Object>> getMediaPorOrigem(int dias) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) as quantidade, tituloorigem as situacao, trunc(avg(coalesce(titulovalor, 0.00)),2 ) as media_valor ");
		sql.append(" from titulo ");
		sql.append(" where  cast(titulodatahora as date) > (current_date -");
		sql.append(dias);
		sql.append(" ) and  cast(titulodatahora as date) <= current_date ");
		sql.append(" group by tituloorigem order by quantidade; ");
		return super.getSimpleJdbcTemplate().queryForList(sql.toString());
	}

	@Override
	public List<Map<String, Object>> getMediaPorTipoReceberPagar(int dias) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) as quantidade, titulotipo as situacao, trunc(avg(coalesce(titulovalor, 0.00)),2 ) as media ");
		sql.append("  from titulo  ");
		sql.append("  where  cast(titulodatahora as date) > (current_date - ");
		sql.append(dias);
		sql.append(" ) and  cast(titulodatahora as date) <= current_date  ");
		sql.append("  group by titulotipo;  ");
		return super.getSimpleJdbcTemplate().queryForList(sql.toString());
	}

	@Override
	public List<Map<String, Object>> getMediaPorTipoAbertoFechado(int dias) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) as quantidade, case when titulobaixado then 'BAIXADO' else 'EM ABERTO' end as situacao, trunc(avg(coalesce(titulovalor, 0.00)),2 ) as media ");
		sql.append("  from titulo ");
		sql.append("  where  cast(titulodatahora as date) > (current_date - ");
		sql.append(dias);
		sql.append(" ) and  cast(titulodatahora as date) <= current_date ");
		sql.append(" group by titulobaixado; ");
		return super.getSimpleJdbcTemplate().queryForList(sql.toString());
	}

	@Override
	public List<Map<String, Object>> getTitulosUltimosDias(int dias) {
		StringBuilder sql = new StringBuilder();
		sql.append("   (SELECT count(1) AS quantidade,                                      ");
		sql.append("           tituloorigem AS situacao,                                      ");
		sql.append("           trunc(avg(coalesce(titulovalor, 0.00)),2) AS media       ");
		sql.append("    FROM titulo                                                         ");
		sql.append("    WHERE cast(titulodatahora AS date) > (CURRENT_DATE -  " + dias + ")   ");
		sql.append("      AND cast(titulodatahora AS date) <= CURRENT_DATE                    ");
		sql.append("    GROUP BY tituloorigem                                                 ");
		sql.append("    UNION SELECT count(1) AS quantidade,                                ");
		sql.append("                 CASE                                                   ");
		sql.append("                     WHEN titulobaixado THEN 'BAIXADO'                    ");
		sql.append("                     ELSE 'EM ABERTO'                                   ");
		sql.append("                 END AS situacao,                                       ");
		sql.append("                 trunc(avg(coalesce(titulovalor, 0.00)),2) AS media ");
		sql.append("    FROM titulo                                                         ");
		sql.append("    WHERE cast(titulodatahora AS date) > (CURRENT_DATE - " + dias + ")    ");
		sql.append("      AND cast(titulodatahora AS date) <= CURRENT_DATE                    ");
		sql.append("    GROUP BY titulobaixado                                                ");
		sql.append("    UNION SELECT count(1) AS quantidade,                                ");
		sql.append("                 titulotipo AS situacao,                                  ");
		sql.append("                 trunc(avg(coalesce(titulovalor, 0.00)),2) AS media ");
		sql.append("    FROM titulo                                                         ");
		sql.append("    WHERE cast(titulodatahora AS date) > (CURRENT_DATE - " + dias + ")    ");
		sql.append("      AND cast(titulodatahora AS date) <= CURRENT_DATE                    ");
		sql.append("    GROUP BY titulotipo)                                                  ");
		sql.append(" ORDER BY quantidade,                                                   ");
		sql.append("          media                                                   ");

		return super.getSimpleJdbcTemplate().queryForList(sql.toString());
	}

}