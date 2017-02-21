package mydomain.springbootcamel;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = SpringBootCamelExamples.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class RestDslTest {


  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void getApiTest(){

    ResponseEntity<String> response = restTemplate.getForEntity("/api/Buckingham Palace", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void unknownGetTest(){

    ResponseEntity<String> response = restTemplate.getForEntity("/dummy", String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
