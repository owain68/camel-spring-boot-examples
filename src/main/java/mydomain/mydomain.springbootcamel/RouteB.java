package mydomain.springbootcamel;

import org.apache.camel.*;
import org.apache.camel.builder.*;

import org.springframework.stereotype.Component;

@Component
public class RouteB extends RouteBuilder{

  @Override
  public void configure() throws Exception {

    from("direct:RouteB")
            .routeId("RouteB")
            .log(LoggingLevel.INFO, "Received Body: ${body}").id("log-input")
            .setBody().simple("Who lives at ${body}")
            .log(LoggingLevel.INFO, "Converted Body: ${body}").id("log-result")
    ;
  }
}
