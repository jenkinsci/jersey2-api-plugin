package org.glassfish.jersey.jackson;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.RealJenkinsRule;

public class JacksonFeatureTest {

    @Rule
    public RealJenkinsRule rr = new RealJenkinsRule();

    @Test
    public void smokes() throws Throwable {
        rr.then(JacksonFeatureTest::_smokes);
    }

    private static void _smokes(JenkinsRule r) {
        JerseyClientBuilder builder = new JerseyClientBuilder();
        builder.register(JacksonFeature.class);
        JerseyClient client = builder.build();
        client.preInitialize();
    }
}
