package org.glassfish.jersey;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.RealJenkinsExtension;

class JerseyTest {

    @RegisterExtension
    private final RealJenkinsExtension extension = new RealJenkinsExtension();

    @Test
    void json() throws Throwable {
        extension.then(JerseyTest::_json);
    }

    private static void _json(JenkinsRule j) throws Exception {
        j.jenkins.getActions().add(new JerseyResource());
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        URI uri = new URI(j.getURL() + "jersey/employee?q=json");
        Employee employee = client.target(uri).request().get(Employee.class);
        assertEquals("Noah", employee.getFirstName());
    }
}
