package br.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.example.model.Feriado;

public interface FeriadoRepository extends CrudRepository<Feriado, Long> {

		Feriado findByData(Date data);
		
		List<Feriado> findByTipoLike(String tipo);
		
		List<Feriado> findByDescricaoLike (String descricao);
		
		Feriado findByDataAndTipoAndDescricao(Date data, String tipo, String descricao);
}
