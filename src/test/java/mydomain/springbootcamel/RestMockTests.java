package mydomain.springbootcamel;

import org.apache.camel.*;
import org.apache.camel.builder.*;
import org.apache.camel.component.mock.*;
import org.apache.camel.test.spring.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.web.client.*;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(CamelSpringBootRunner.class)
@MockEndpoints
@UseAdviceWith
@SpringBootTest(classes = mydomain.springbootcamel.SpringBootCamelExamples.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestMockTests {

  @Autowired
  private CamelContext camelContext;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void mockTestOneNotFound() throws Exception {

    camelContext.getRouteDefinition("getRoute")
            .adviceWith(camelContext, new AdviceWithRouteBuilder() {
              @Override
              public void configure() throws Exception {
                // send the outgoing message to mock
                weaveByToUri("direct:RouteB").replace().inOut("mock:routeB");
              }
            });

    camelContext.start();
    MockEndpoint mockOut = camelContext.getEndpoint("mock:routeB", MockEndpoint.class);
    mockOut.expectedMessageCount(0);
    ResponseEntity<String> response = restTemplate.getForEntity("/dummy", String.class);
    mockOut.assertIsSatisfied();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void mockTestTwoFound() throws Exception {

    camelContext.getRouteDefinition("getRoute")
            .adviceWith(camelContext, new AdviceWithRouteBuilder() {
              @Override
              public void configure() throws Exception {
                // send the outgoing message to mock
                weaveByToUri("direct:RouteB").replace().inOut("mock:routeB");
              }
            });

    camelContext.start();
    MockEndpoint mockOut = camelContext.getEndpoint("mock:routeB", MockEndpoint.class);
    mockOut.expectedMessageCount(1);
    ResponseEntity<String> response = restTemplate.getForEntity("/api/something", String.class);
    mockOut.assertIsSatisfied();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
