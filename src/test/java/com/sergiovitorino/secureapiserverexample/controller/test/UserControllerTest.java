package com.sergiovitorino.secureapiserverexample.controller.test;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergiovitorino.secureapiserverexample.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TestRestTemplate restTemplete;

	@LocalServerPort
	private Integer port;

	@Test
	public void testIfSignUpIsOk() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String userJSON = "{\"username\":\"sergiovitorino1\",\"password\":\"123456\"}";
		// RequestEntity
		HttpEntity<String> entity = new HttpEntity<String>(userJSON, headers);

		ResponseEntity<String> responseEntity = this.restTemplete
				.postForEntity("http://localhost:" + port + "/api/user/sign-up", entity, String.class);
		User userCreated = mapper.readValue(responseEntity.getBody(), User.class);

		assertFalse(userCreated.getAccessToken().isEmpty());
	}

	@Test
	public void testIfSignInIsOk() throws Exception {
		User user = createUser(UUID.randomUUID().toString());
		String credentialsJSON = "{\"username\":\"" + user.getUsername() + "\",\"password\":\"123456\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(credentialsJSON, headers);
		ResponseEntity<String> responseEntity = this.restTemplete
				.postForEntity("http://localhost:" + port + "/api/user/sign-in", entity, String.class);
		User userAuthorized = mapper.readValue(responseEntity.getBody(), User.class);

		assertFalse(userAuthorized.getAccessToken().isEmpty());
	}

	@Test
	public void testIfFindIsOk() throws Exception {
		User user = createUser(UUID.randomUUID().toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("userId", user.getId());
		headers.add("bearer", user.getAccessToken());

		// RequestEntity
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntityResult = this.restTemplete.exchange(
				"http://localhost:" + port + "/api/user/" + user.getId(), HttpMethod.GET, entity, String.class);

		try {
			User userFound = mapper.readValue(responseEntityResult.getBody(), User.class);
			assertNotNull(userFound);
		} catch (Exception e) {
			fail();
		}
	}

	private User createUser(String username) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String userJSON = "{\"username\":\"" + username + "\",\"password\":\"123456\"}";
		// RequestEntity
		HttpEntity<String> entity = new HttpEntity<String>(userJSON, headers);

		ResponseEntity<String> responseEntity = this.restTemplete
				.postForEntity("http://localhost:" + port + "/api/user/sign-up", entity, String.class);
		return mapper.readValue(responseEntity.getBody(), User.class);
	}

}
