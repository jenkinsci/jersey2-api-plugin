package org.glassfish.jersey;

import static org.junit.Assert.assertEquals;

import hudson.model.UnprotectedRootAction;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javax.servlet.ServletException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.xml.bind.annotation.XmlRootElement;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.TestExtension;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public class JerseyTest {

    @Rule public JenkinsRule j = new JenkinsRule();

    @Test
    public void xml() throws Exception {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        URI uri = new URI(j.getURL() + "jersey/employee");
        Employee employee = client.target(uri).request().get(Employee.class);
        assertEquals("Noah", employee.getFirstName());
    }

    @Test
    public void json() throws Exception {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        URI uri = new URI(j.getURL() + "jersey/employee?q=json");
        Employee employee = client.target(uri).request().get(Employee.class);
        assertEquals("Noah", employee.getFirstName());
    }

    @XmlRootElement
    public static class Employee {

        private int id;
        private String firstName;

        public Employee() {}

        public Employee(int id, String firstName) {
            this.id = id;
            this.firstName = firstName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }

    @TestExtension
    public static class JerseyResource implements UnprotectedRootAction {

        @Override
        public String getIconFileName() {
            return null;
        }

        @Override
        public String getDisplayName() {
            return null;
        }

        @Override
        public String getUrlName() {
            return "jersey";
        }

        public HttpResponse doEmployee(@QueryParameter String q) {
            String contentType;
            URL url;
            if ("json".equals(q)) {
                contentType = "application/json";
                url = getClass().getResource("1.json");
            } else {
                contentType = "text/xml";
                url = getClass().getResource("1.xml");
            }
            return new HttpResponse() {
                @Override
                public void generateResponse(StaplerRequest req, StaplerResponse rsp, Object node)
                        throws IOException, ServletException {
                    rsp.setContentType(contentType + ";charset=UTF-8");
                    rsp.serveFile(req, url, 0);
                }
            };
        }
    }
}
