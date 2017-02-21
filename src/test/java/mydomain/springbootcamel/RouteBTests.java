package mydomain.springbootcamel;

import org.apache.camel.*;
import org.apache.camel.builder.*;
import org.apache.camel.component.mock.*;
import org.apache.camel.test.spring.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;


@RunWith(CamelSpringBootRunner.class)
@MockEndpoints
@UseAdviceWith
@SpringBootTest(classes = mydomain.springbootcamel.SpringBootCamelExamples.class)
public class RouteBTests {

  @Autowired
  private CamelContext camelContext;

  @Autowired
  private ProducerTemplate template;

  @Test
  public void whereIsBuckinghamPalaceTest() throws Exception {

    camelContext.getRouteDefinition("RouteB")
            .adviceWith(camelContext, new AdviceWithRouteBuilder() {
              @Override
              public void configure() throws Exception {
                replaceFromWith("direct:in");
                // send the outgoing message to mock:out
                weaveAddLast().to("mock:out");
              }
            });

    camelContext.start();
    MockEndpoint mockOut = camelContext.getEndpoint("mock:out", MockEndpoint.class);
    mockOut.expectedMessageCount(1);
    template.sendBody("direct:in","Buckingham Palace");
    mockOut.assertIsSatisfied();

  }
}
