package br.example.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.example.model.Feriado;
import br.example.service.FeriadoService;
import br.example.util.CustomErrorType;
import br.example.util.FeriadoUtil;

import static br.example.util.FeriadoUtil.*;

@RestController
@RequestMapping("/v1")
public class FeriadoResource {

	public static final Logger logger = LoggerFactory.getLogger(FeriadoResource.class);

	@Autowired
	private FeriadoService feriadoService;

	@RequestMapping(value = "/feriados/", method = RequestMethod.GET)
	public ResponseEntity<List<Feriado>> listarFeriados() {
		logger.info("Listando Feriados");
		List<Feriado> feriados = feriadoService.buscarTodosOsFeriados();
		if (feriados.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // pode ser NOT_FOUND
		}
		return new ResponseEntity<List<Feriado>>(feriados, HttpStatus.OK);
	}

	@RequestMapping(value = "/feriados/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarFeriado(@PathVariable("id") long id) {
		logger.info("Listando Feriado com id {}", id);
		Feriado feriado = feriadoService.buscarFeriadoPorId(id);
		if (feriado == null) {
			logger.error("Feriado com id {} não encontrado.", id);
			return new ResponseEntity<>(new CustomErrorType("404","Feriado com id " + id + " não encontrado."),HttpStatus.NOT_FOUND);
		}
		logger.info("Feriado com id {} encontrado.", id);
		return new ResponseEntity<Feriado>(feriado, HttpStatus.OK);
	}

	@RequestMapping(value = "/feriados/", method = RequestMethod.POST)
	public ResponseEntity<?> inserirFeriado(@RequestBody Feriado feriado, UriComponentsBuilder ucBuilder) {
		logger.info("Inserindo feriado : {}", feriado.toString());

		if (feriadoService.isFeriadoExist(feriado)) {
			logger.info("Não foi possível criar. Um Feriado com a data {} ja existe:", dateToStringIso8601Format(feriado.getData()));
			return new ResponseEntity<>(new CustomErrorType("409","Não foi possível criar. Um Feriado com a data " + dateToStringIso8601Format(feriado.getData()) + " ja existe"),HttpStatus.CONFLICT);
		}

		feriadoService.salvarFeriado(feriado);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/v1/feriados/{id}").buildAndExpand(feriado.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/feriados/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> alterarFeriado(@PathVariable("id") long id, @RequestBody Feriado feriado) {
		logger.info("Alterando o feriado com o id {}", id);

		Feriado feriadoAtual = feriadoService.buscarFeriadoPorId(id);

		if (feriadoAtual == null) {
			logger.info("Não foi possivel alterar. Feriado com id {} não encontrado", id);
			return new ResponseEntity<>(new CustomErrorType("404","Não foi possivel alterar. Feriado com id "+ id +" não encontrado"),HttpStatus.NOT_FOUND);
		}

		feriadoAtual.setData(dateToStringIso8601Format(feriado.getData()));
		feriadoAtual.setTipo(feriado.getTipo());
		feriadoAtual.setDescricao(feriado.getDescricao());

		feriadoService.salvarFeriado(feriadoAtual);
		return new ResponseEntity<Feriado>(feriadoAtual, HttpStatus.OK);
	}

	@RequestMapping(value = "/feriados/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerFeriado(@PathVariable("id") long id) {
		logger.info("Removendo Usuário com o id {}", id);

		Feriado feriadoAtual = feriadoService.buscarFeriadoPorId(id);
		if (feriadoAtual == null) {
			logger.info("Não foi possivel remover. Feriado com id {} não encontrado", id);
			return new ResponseEntity<>(new CustomErrorType("404","Não foi possivel remover. Feriado com id "+ id + "não encontrado"),HttpStatus.NOT_FOUND);
		}
		feriadoService.removerFeriado(id);
		return new ResponseEntity<Feriado>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/feriados/", method = RequestMethod.DELETE)
	public ResponseEntity<Feriado> removerTodosOsFeriados() {
		logger.info("Removendo todos os Feriados");

		feriadoService.removerTodosOsFeriados();
		return new ResponseEntity<Feriado>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/feriados/isFeriado/{data}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> isFeriado(@PathVariable("data") String data){
		logger.info("Verificando se a data {} é feriado, sabado ou domingo", data);
		Feriado feriadoResult = feriadoService.buscarFeriadoPorData(FeriadoUtil.stringToDateIso8601Format(data));
		
		if(feriadoResult == null){

			if(FeriadoUtil.dateIsSaturday(data)){
				logger.info("A data {} é sabado", data);
				return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			}
			else if(FeriadoUtil.dateIsSunday(data)){
				logger.info("A data {} é domingo", data);
				return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			}
			logger.info("A data {} não é sabado/domingo/feriado", data);
			return new ResponseEntity<Boolean>(false,HttpStatus.NOT_FOUND);
		}
		
		logger.info("A data {} é feriado", data);
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}

}
