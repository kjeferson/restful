package br.example.mock;

import java.util.List;

import org.assertj.core.util.Lists;

import br.example.model.Feriado;

public class FeriadosMock {

	public static List<Feriado> getFeriados() {
		List<Feriado> feriados = Lists.newArrayList(
				new Feriado(1l,	"2015-01-01T00:00:00", "feriado nacional", 	"Confraternização universal"),
				new Feriado(2l,	"2015-02-16T00:00:00", "ponto facultativo", "Carnaval"),
				new Feriado(3l,	"2015-02-17T00:00:00", "ponto facultativo", "Carnaval"),
				new Feriado(4l,	"2015-02-18T00:00:00", "ponto facultativo até as 14 horas", "Quarta-feira de Cinzas"),
				new Feriado(5l,	"2015-04-06T00:00:00", "ponto facultativo", "Paixão de Cristo"),
				new Feriado(6l,	"2015-04-21T00:00:00", "feriado nacional", 	"Tiradentes"),
				new Feriado(7l,	"2015-05-01T00:00:00", "feriado nacional", 	"Dia Mundial do Trabalho"),
				new Feriado(8l,	"2015-06-07T00:00:00", "ponto facultativo", "Corpus Christi"),
				new Feriado(9l,	"2015-09-07T00:00:00", "feriado nacional", 	"Independência do Brasil"),
				new Feriado(10l,"2015-10-12T00:00:00", "feriado nacional", 	"Nossa Senhora Aparecida"),
				new Feriado(11l,"2015-10-28T00:00:00", "ponto facultativo", "Dia do Servidor Público"),
				new Feriado(12l,"2015-11-02T00:00:00", "feriado nacional", 	"Finados"),
				new Feriado(13l,"2015-11-15T00:00:00", "feriado nacional", 	"Proclamação da República"),
				new Feriado(14l,"2015-12-24T00:00:00", "ponto facultativo", "Véspera do Natal"),
				new Feriado(15l,"2015-12-25T00:00:00", "feriado nacional", 	"Natal"),
				new Feriado(16l,"2015-12-31T00:00:00", "ponto facultativo", "Véspera do Natal"));
		return feriados;
	}

	public static Feriado getFeriado(){
		return new Feriado(1l,"2015-01-01T00:00:00", "feriado nacional", "Confraternização universal");
	}
}
