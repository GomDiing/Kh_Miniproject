package comprehensive.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;

@SpringBootApplication
@Slf4j
public class ExerciseApplication {

    public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
        /*
		String url = "https://openapi.naver.com";
		String path = "/v1/search/movie.json";

		String clientId = "X-Naver-Client-ID";
		String clientSecret = "X-Naver-Client-Secret";

		String clientIdValue = "_ctkc1uA4YQ6Jgr0gRgb";
		String clientSecretValue = "8htToOX0Dr";

		URI uri = UriComponentsBuilder
				.fromUriString(url)
				.path(path)
				.queryParam("query", "매트릭스")
				.queryParam("display", 10)
				.encode()
				.build()
				.toUri();

		RequestEntity requestEntity = RequestEntity
				.get(uri)
				.header(clientId, clientIdValue)
				.header(clientSecret, clientSecretValue)
				.build();

		log.info(requestEntity.toString());

		RestTemplate restTemplate = new RestTemplate();

		restTemplate.exchange(requestEntity, String.class);
		*/
	}
}

