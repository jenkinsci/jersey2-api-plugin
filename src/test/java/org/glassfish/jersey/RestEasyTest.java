package org.glassfish.jersey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.TestExtension;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class RestEasyTest {

    private JenkinsRule j;

    @BeforeEach
    void beforeEach(JenkinsRule rule) {
        j = rule;
    }

    @Test
    void withoutJackson() throws Exception {
        ResteasyClientBuilder builder = new ResteasyClientBuilder();
        final ResteasyClient resteasyClient = builder.connectionPoolSize(60).build();
        final ResteasyWebTarget target = resteasyClient.target(j.getURL() + "jersey");
        final EmployeeService employeeService = target.proxy(EmployeeService.class);
        final String employee = employeeService.getAtAsString("json").trim();
        final String expected = "{\"id\":1,\"firstName\":\"Noah\"}";
        assertEquals(expected, employee);
    }

    @Test
    void withJackson() throws Exception {
        ResteasyClientBuilder builder = new ResteasyClientBuilder();
        final ResteasyClient resteasyClient =
                builder.register(new JacksonFeature()).connectionPoolSize(60).build();
        final ResteasyWebTarget target = resteasyClient.target(j.getURL() + "jersey");
        final EmployeeService employeeService = target.proxy(EmployeeService.class);
        final Employee employee = employeeService.getAt("json");
        assertEquals(1, employee.getId());
        assertEquals("Noah", employee.getFirstName());
    }

    @Path("/employee")
    public interface EmployeeService {

        @GET
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        Employee getAt(@QueryParam("q") String q);

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        String getAtAsString(@QueryParam("q") String q);
    }

    @SuppressWarnings("unused")
    @TestExtension
    public static class RestEasyJerseyResource extends JerseyResource {}
}
