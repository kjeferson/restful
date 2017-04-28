package br.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.example.model.Feriado;
import br.example.repository.FeriadoRepository;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class FeriadoService {

	@Autowired
	private FeriadoRepository feriadoRepository;

	public List<Feriado> buscarTodosOsFeriados() {

		Iterable<Feriado> feriados = feriadoRepository.findAll();

		return Lists.newArrayList(feriados);
	}

	public Feriado buscarFeriadoPorId(Long id) {
		return feriadoRepository.findOne(id);
	}

	public Feriado salvarFeriado(Feriado feriado) {
		return feriadoRepository.save(feriado);
	}

	public void removerFeriado(Long id) {
		feriadoRepository.delete(id);
	}

	public Feriado buscarFeriadoPorData(Date data) {
		return feriadoRepository.findByData(data);
	}

	public List<Feriado> buscarFeriadoPorTipo(String tipo) {
		return feriadoRepository.findByTipoLike(tipo);
	}

	public List<Feriado> buscarFeriadoPorDescricao(String descricao) {
		return feriadoRepository.findByDescricaoLike(descricao);
	}
	
	public void removerTodosOsFeriados(){
		feriadoRepository.deleteAll();
	}

	public boolean isFeriadoExist(Feriado feriado) {
		return this.buscarFeriadoPorData(feriado.getData()) != null;
	}

}
