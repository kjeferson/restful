package br.example.resource;


import org.hamcrest.Matchers;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ctc.wstx.util.DataUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.example.mock.FeriadosMock;
import br.example.model.Feriado;
import br.example.service.FeriadoService;
import br.example.util.FeriadoUtil;


@RunWith(SpringRunner.class)// It is alias for SpringJUnit4ClassRunner . It will add Spring TestContext Framework support.
@WebMvcTest(FeriadoResource.class)//  Auto configure Spring MVC infrastructure and MockMvc. It will only scan beans related to Web layer, like @Controller , @ControllerAdvice , WebMvcConfigurer  etc. This is useful because we are interested only in web layer when unit testing Controller classes. In our case we are limiting it to  Auto configure Spring MVC infrastructure and MockMvc. It will only scan beans related to Web layer, like @Controller , @ControllerAdvice , WebMvcConfigurer  etc. This is useful because we are interested only in web layer when unit testing Controller classes. In our case we are limiting it to FeriadoResource.class ..class .
public class FeriadoResourceTest {

 private static final String URL = "http://localhost:9000/v1/feriados/";

 @Autowired
 private MockMvc mockMvc; //Provide Spring MVC infrastructure without starting the HTTP Server.
  
 @MockBean //Provides Mockito mock bean for a given class’s instance. Normally used to inject mock dependency. In our case, mock instance of FeriadoService  is injected because class under test ( FeriadoResource ) requires it.
 FeriadoService feriadoService;
 
	 @Test
	 public void shouldGetAllFeriado() throws Exception{
		 
		 when(feriadoService.buscarTodosOsFeriados()).thenReturn(FeriadosMock.getFeriados());
		 
			MvcResult result = mockMvc
					.perform(MockMvcRequestBuilders.get(URL)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk()) //Verifica Status
					.andReturn();
			
			verify(feriadoService).buscarTodosOsFeriados();
			verifyNoMoreInteractions(feriadoService);
			
			//Transformando json em feriado
			List<Feriado> feriados = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
					.fromJson(result.getResponse().getContentAsString(), new TypeToken<List<Feriado>>(){}.getType());

			assertNotNull(feriados);
			assertEquals(16, feriados.size());
			assertEquals(1l, feriados.get(0).getId().longValue());
			assertEquals(2l, feriados.get(1).getId().longValue());
			assertEquals(3l, feriados.get(2).getId().longValue());
			assertEquals(4l, feriados.get(3).getId().longValue());
			assertEquals(5l, feriados.get(4).getId().longValue());
			assertEquals(6l, feriados.get(5).getId().longValue());
			assertEquals(7l, feriados.get(6).getId().longValue());
			assertEquals(8l, feriados.get(7).getId().longValue());
			assertEquals(9l, feriados.get(8).getId().longValue());
			assertEquals(10l, feriados.get(9).getId().longValue());
			assertEquals(11l, feriados.get(10).getId().longValue());
			assertEquals(12l, feriados.get(11).getId().longValue());
			assertEquals(13l, feriados.get(12).getId().longValue());
			assertEquals(14l, feriados.get(13).getId().longValue());
			assertEquals(15l, feriados.get(14).getId().longValue());			
			assertEquals(16l, feriados.get(15).getId().longValue());			
	 }
	 
 	@Test
	public void shouldGetFeriadoById() throws Exception {

		// ao buscar por qualquer id retorna uma feriado mockado
		Feriado feriadoMock = FeriadosMock.getFeriado();
		when(feriadoService.buscarFeriadoPorId(any(Long.class))).thenReturn(feriadoMock);

		// Simula execução do get passando a URL e o id
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{id}", feriadoMock.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()) //Verifica Status
				.andReturn();

		// Verificar que o método do service foi chamado uma vez, equivalente: verify(feriadoService, times(1)).buscarFeriadoPorId(any(Long.class));
		verify(feriadoService).buscarFeriadoPorId(any(Long.class));
		// Verificar se teve mais execuções além da de buscarFeriadoPorId
		verifyNoMoreInteractions(feriadoService);
		
		//Transformando json em feriado
		Feriado resultFeriado = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
				.fromJson(result.getResponse().getContentAsString(), Feriado.class);

		assertNotNull(resultFeriado);
		assertEquals(1l, resultFeriado.getId().longValue());
		
	}
 	
 	@Test
 	public void shouldNotGetFeriadoByIdIfNotExistAndSendError() throws Exception{
 		
 		//Não precisa preparar o comportamento do mock neste cenário
 		//when(feriadoService.buscarFeriadoPorId(any(Long.class))).thenReturn(null);
 			 
 		mockMvc	.perform(MockMvcRequestBuilders.get(URL + "{id}",new Long(1)))
 				.andExpect(MockMvcResultMatchers.status().isNotFound())
 				.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("404")))
 				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Feriado com id 1 não encontrado.")));
 				
 		verify(feriadoService).buscarFeriadoPorId(any(Long.class));
 		verifyNoMoreInteractions(feriadoService);	
 	}
 	
 	@Test
 	public void shouldInsertFeriado() throws Exception{
 		
 		Feriado feriadoMock = FeriadosMock.getFeriado();
		when(feriadoService.salvarFeriado(any(Feriado.class))).thenReturn(feriadoMock);
 			 
		mockMvc	.perform(MockMvcRequestBuilders.post(URL)
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(new GsonBuilder()
 						.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
 						.toJson(feriadoMock)))
 				.andExpect(MockMvcResultMatchers.status().isCreated())
 				.andExpect(MockMvcResultMatchers.header().string("location", Matchers.containsString(URL + feriadoMock.getId())));
 		
		verify(feriadoService).isFeriadoExist(any(Feriado.class));
		verify(feriadoService).salvarFeriado(any(Feriado.class));
 		verifyNoMoreInteractions(feriadoService);
 	}
 	
 	@Test
 	public void shouldNotInsertFeriadoIfAlreadyExist() throws Exception{
 		
		Feriado feriadoMock = FeriadosMock.getFeriado();
		when(feriadoService.isFeriadoExist(any(Feriado.class))).thenReturn(true);
 		
		mockMvc	.perform(MockMvcRequestBuilders.post(URL)
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(new GsonBuilder()
 						.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
 						.toJson(feriadoMock)))
 				.andExpect(MockMvcResultMatchers.status().isConflict())
 				.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("409")))
 				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Não foi possível criar. Um Feriado com a data " +  FeriadoUtil.dateToStringIso8601Format(feriadoMock.getData()) + " ja existe")));
 		
 		verify(feriadoService).isFeriadoExist(any(Feriado.class));
 		verifyNoMoreInteractions(feriadoService);
 	}
	
 	@Test
 	public void shouldUpdateFeriado() throws Exception{
 		
 		Feriado feriadoMock = FeriadosMock.getFeriado();
		when(feriadoService.buscarFeriadoPorId(any(Long.class))).thenReturn(feriadoMock);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put(URL+"{id}", feriadoMock.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
						.toJson(feriadoMock)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		Feriado feriadoResult = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
				.fromJson(result.getResponse().getContentAsString(), Feriado.class);
		
		verify(feriadoService).buscarFeriadoPorId(any(Long.class));
		verify(feriadoService).salvarFeriado(any(Feriado.class));
		verifyNoMoreInteractions(feriadoService);
		
		assertNotNull(feriadoResult);
		assertEquals(1l,feriadoResult.getId().longValue());
 	}
 	
 	@Test
 	public void shouldNotUpdateFeriadoIfNotExistAndSendErrorMessage() throws Exception{
 		Feriado feriadoMock = FeriadosMock.getFeriado();
 		when(feriadoService.buscarFeriadoPorId(any(Long.class))).thenReturn(null);
 		
 		mockMvc
			.perform(MockMvcRequestBuilders.put(URL+"{id}", feriadoMock.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
					.toJson(feriadoMock)))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("404")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Não foi possivel alterar. Feriado com id "+ feriadoMock.getId() +" não encontrado")));
		
 		verify(feriadoService).buscarFeriadoPorId(any(Long.class));
 		verifyNoMoreInteractions(feriadoService);
 		
 	}
 	
 	@Test
 	public void shouldDeleteFeriado() throws Exception{
 		Feriado feriadoMock = FeriadosMock.getFeriado();
		when(feriadoService.buscarFeriadoPorId(any(Long.class))).thenReturn(feriadoMock);
		
	 mockMvc.perform(MockMvcRequestBuilders.delete(URL+"{id}", feriadoMock.getId()))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		verify(feriadoService).buscarFeriadoPorId(any(Long.class));
		verify(feriadoService).removerFeriado(any(Long.class));
		verifyNoMoreInteractions(feriadoService);
 	}
 	
 	@Test
 	public void shouldNotDeleteIfFeriadoNotExistAndSendErrorMessage() throws Exception{
 		Feriado feriadoMock = FeriadosMock.getFeriado();
 		when(feriadoService.buscarFeriadoPorId(any(Long.class))).thenReturn(null);
 		
 		mockMvc
			.perform(MockMvcRequestBuilders.delete(URL+"{id}", feriadoMock.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
					.toJson(feriadoMock)))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("404")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Não foi possivel remover. Feriado com id "+ feriadoMock.getId() + "não encontrado")));
		
 		verify(feriadoService).buscarFeriadoPorId(any(Long.class));
 		verifyNoMoreInteractions(feriadoService);
 	}
 	
 	@Test
 	public void shouldDeleteAllFeriado() throws Exception{
 		
	 mockMvc.perform(MockMvcRequestBuilders.delete(URL))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		verify(feriadoService).removerTodosOsFeriados();
		verifyNoMoreInteractions(feriadoService);
 	}
 	
 	@Test
 	public void shouldReturnTrueIfDateIsFeriadoOrSabadoOrDomingo() throws Exception{
 		
 		Feriado feriadoMock = FeriadosMock.getFeriado();
		when(feriadoService.buscarFeriadoPorData(any(Date.class))).thenReturn(feriadoMock);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "/isFeriado/{id}", "2015-01-01T00:00:00")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()) //Verifica Status
				.andReturn();
		
		verify(feriadoService).buscarFeriadoPorData(any(Date.class));
		verifyNoMoreInteractions(feriadoService);
		
		Boolean dataIsFeriado = new Gson().fromJson(result.getResponse().getContentAsString(),Boolean.class); 
 		
		assertNotNull(dataIsFeriado);
		assertTrue(dataIsFeriado);
 	}
 	@Test
 	public void shouldReturnFalseIfDateIsNotFeriado() throws Exception{
 		
 		when(feriadoService.buscarFeriadoPorData(any(Date.class))).thenReturn(null);
 		
 		MvcResult result = mockMvc
 				.perform(MockMvcRequestBuilders.get(URL + "/isFeriado/{id}", "2015-01-01T00:00:00")
 						.accept(MediaType.APPLICATION_JSON))
 				.andExpect(MockMvcResultMatchers.status().isNotFound())
 				.andReturn();
 		
 		verify(feriadoService).buscarFeriadoPorData(any(Date.class));
 		verifyNoMoreInteractions(feriadoService);
 		
 		Boolean dataIsFeriado = new Gson().fromJson(result.getResponse().getContentAsString(),Boolean.class); 
 		
 		assertNotNull(dataIsFeriado);
 		assertFalse(dataIsFeriado);
 	}
}
