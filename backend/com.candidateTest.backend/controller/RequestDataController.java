package com.candidateTest.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.candidateTest.backend.dao.DAORequestData;
import com.candidateTest.backend.dto.DTOJsonSaveData;
import com.candidateTest.backend.dto.DTOReqSaveData;
import com.candidateTest.backend.entity.RequestData;
import com.candidateTest.backend.service.SomeExternalAPINameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RequestDataController {

	@Autowired
	private DAORequestData daoReqData;
	private WebClient webClient = WebClient.create("http://localhost:8080");

	@RequestMapping(value = "getData", method = RequestMethod.GET)
	public ResponseEntity<?> getData() {
		return ResponseEntity.ok(daoReqData.findAll());
	}

	@RequestMapping(value = "saveData", method = RequestMethod.POST)
	public ResponseEntity<?> saveData(@RequestBody DTOReqSaveData reqBody) {
		long timestamp = reqBody.getTimestamp();
		if (timestamp == 0)
			timestamp = System.currentTimeMillis();
		daoReqData.save(new RequestData(timestamp, reqBody.getJson()));
		return ResponseEntity.ok(false);
	}

	@RequestMapping(value = "transformKeyValue", method = RequestMethod.GET)
	public ResponseEntity<?> transformKeyValue(@RequestParam(value = "timestamp", required = false) Long timestamp) throws JsonMappingException, JsonProcessingException {
		SomeExternalAPINameService extService = new SomeExternalAPINameService();
		ObjectMapper objectMapper = new ObjectMapper();
		DTOJsonSaveData jsonData = new DTOJsonSaveData();

		JsonNode extKeyResponse = extService.key();
		jsonData.setKey(extKeyResponse.get("key").asLong());
		JsonNode extValueResponse = extService.value(Long.toString(jsonData.getKey()));
		int[] values = objectMapper.convertValue(extValueResponse.get("value"), int[].class);
		int sum = 0;
		for (int i = 0; i < values.length; i++)
			sum += (int) values[i];
		jsonData.setSum(sum);

		DTOReqSaveData reqBody = new DTOReqSaveData();
		reqBody.setJson(objectMapper.writeValueAsString(jsonData));
		webClient.method(HttpMethod.POST)
			.uri("/saveData")
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(reqBody), DTOReqSaveData.class)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		return ResponseEntity.ok(false);
	}
}
