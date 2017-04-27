package mydomain.springbootcamel;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder{

  @Bean
  ServletRegistrationBean camelServlet() {
    // use a @Bean to register the Camel servlet which we need to do
    // because we want to use the camel-servlet component for the Camel REST service
    ServletRegistrationBean mapping = new ServletRegistrationBean();
    mapping.setName("CamelServlet");
    mapping.setLoadOnStartup(1);
    // CamelHttpTransportServlet is the name of the Camel servlet to use
    mapping.setServlet(new CamelHttpTransportServlet());
    mapping.addUrlMappings("/api/*");
    return mapping;
  }

  @Override
  public void configure() throws Exception {

    rest("/")
            .get("{body}")
            .id("getRoute")
            .produces("application/json")
            .to("direct:RouteB");
  }
}
