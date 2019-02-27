package com.example.demo;

import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.repository.PersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = DemoApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class DemoApplicationTests {

	@Autowired
	private PersonRepository repo;

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testWhenPostRequestValidPerson_thenCorrectResponse() throws Exception {
		this.deleteBD();
		String person = "{\"name\":\"cristian\",\"surname\":\"pagano\"}";
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post("/persona/1", "id_persona").content(person)
						.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String expected = "{\"dni\":1,\"name\":\"cristian\",\"surname\":\"pagano\",\"age\":null}";

		JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);

	}

	@Test
	public void testWhenPostRequestExistPersonDNI_thenConflictResponse() throws Exception {
		this.deleteBD();

		String person1 = "{\"name\":\"cristian\",\"surname\":\"pagano\"}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/1", "id_persona").content(person1)
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		String person = "{\"name\":\"Juan\",\"surname\":\"Lopez\"}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/1", "id_persona").content(person)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isConflict());

	}

	@Test
	public void testWhenPostRequestInValidPersonDni_thenBadRequestResponse() throws Exception {

		String person = "{\"name\":\"Juan\",\"surname\":\"Lopez\"}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/2a", "id_persona").content(person)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testWhenPostRequestInValidPersonNameWithNumber_thenBadRequestResponse() throws Exception {

		String person = "{\"name\":\"Jua3n\",\"surname\":\"Lopez\"}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/2", "id_persona").content(person)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testWhenPostRequestInValidPersonNameWithBadLength_thenBadRequestResponse() throws Exception {

		String person = "{\"name\":\"J\",\"surname\":\"Lopez\"}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/2", "id_persona").content(person)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testWhenPostRequestInValidPersonNameWithEmptyValue_thenBadRequestResponse() throws Exception {

		String person = "{\"name\":\"\",\"surname\":\"Lopez\"}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/2", "id_persona").content(person)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testWhenPostRequestInValidPersonAgeWithNegativeValue_thenBadRequestResponse() throws Exception {

		String person = "{\"name\":\"Juan\",\"surname\":\"Lopez\",\"age\":-5}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/2", "id_persona").content(person)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testWhenPostRequestInValidPersonAgeWithMore100YearsValue_thenBadRequestResponse() throws Exception {

		String person = "{\"name\":\"Juan\",\"surname\":\"Lopez\",\"age\":101}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/2", "id_persona").content(person)
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void testWhenGivenPersons_whenGetPersons_thenStatusOk() throws Exception {
		this.deleteBD();

		String person1 = "{\"name\":\"cristian\",\"surname\":\"pagano\"}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/1", "id_persona").content(person1)
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		String person2 = "{\"name\":\"Juan\",\"surname\":\"Lopez\",\"age\":50}";
		mvc.perform(MockMvcRequestBuilders.post("/persona/2", "id_persona").content(person2)
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		String uri = "/personas";
		mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)));

	}

	private void deleteBD() {
		repo.deleteAll();
	}
}
