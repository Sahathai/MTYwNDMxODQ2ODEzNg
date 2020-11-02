package com.candidateTest.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SomeExternalAPINameService {

	@Autowired
	private WebClient webClient = WebClient.create("https://asia-east2-candidateplayground.cloudfunctions.net");
	private ObjectMapper objectMapper = new ObjectMapper();

	public JsonNode key() throws JsonMappingException, JsonProcessingException {
		String response = webClient
			.method(HttpMethod.GET)
			.uri("/key")
			.retrieve()
			.bodyToMono(String.class)
			.block();
		return objectMapper.readTree(response);
	}
	public JsonNode value(String key) throws JsonMappingException, JsonProcessingException {
		String response = webClient
			.method(HttpMethod.GET)
			.uri("/value")
			.header("Authorization", key)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		return objectMapper.readTree(response);
	}
}
