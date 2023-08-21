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
	void sendHttpRequestsToTestLinks() throws IOException {
		List<String> testLinks = readTestLinksFromFile();
		List<String> comments = readCommentsFromFile();

		for (int i = 0; i < testLinks.size(); i++) {
			String link = testLinks.get(i);
			String comment = comments.get(i);

			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				HttpGet httpGet = new HttpGet(link);
				try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
					String responseBody = new BufferedReader(
							new InputStreamReader(response.getEntity().getContent()))
							.lines().collect(Collectors.joining("\n"));

					System.out.println(comment);
					System.out.println(responseBody);
					System.out.println("==============================================================");
				}
			}
		}
	}

	private List<String> readTestLinksFromFile() throws IOException {
		List<String> testLinks = new ArrayList<>();

		try (InputStream inputStream = ExamApplication.class.getResourceAsStream("/test-links.txt")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.startsWith("#")) {
                        testLinks.add(line);
                    }
                }
            }
        }

		return testLinks;
	}

	private List<String> readCommentsFromFile() throws IOException {
		List<String> comments = new ArrayList<>();

		try (InputStream inputStream = ExamApplication.class.getResourceAsStream("/test-links.txt")) {
			assert inputStream != null;
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("#")) {
						comments.add(line);
					}
				}
			}
		}
		return comments;
	}
}
