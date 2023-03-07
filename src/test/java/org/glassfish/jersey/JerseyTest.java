package org.glassfish.jersey;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.RealJenkinsRule;

public class JerseyTest {

    @Rule public RealJenkinsRule rr = new RealJenkinsRule();

    @Test
    public void json() throws Throwable {
        rr.then(JerseyTest::_json);
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
