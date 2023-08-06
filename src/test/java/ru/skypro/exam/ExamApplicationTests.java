package ru.skypro.exam;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ExamApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void sendHttpRequestsToTestLinks() {
		List<String> testLinks = readTestLinksFromFile("/test-links.txt");

		for (String link : testLinks) {
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				HttpGet httpGet = new HttpGet(link);
				try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
					String responseBody = new BufferedReader(
							new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					System.out.println(responseBody);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<String> readTestLinksFromFile(String fileName) {
		List<String> testLinks = new ArrayList<>();

		try (InputStream inputStream = ExamApplication.class.getResourceAsStream(fileName);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				testLinks.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return testLinks;
	}
}
