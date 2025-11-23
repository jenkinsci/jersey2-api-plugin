package org.glassfish.jersey.jackson;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.RealJenkinsExtension;

class JacksonFeatureTest {

    @RegisterExtension
    private final RealJenkinsExtension extension = new RealJenkinsExtension();

    @Test
    void smokes() throws Throwable {
        extension.then(JacksonFeatureTest::_smokes);
    }

    private static void _smokes(JenkinsRule r) {
        JerseyClientBuilder builder = new JerseyClientBuilder();
        builder.register(JacksonFeature.class);
        JerseyClient client = builder.build();
        client.preInitialize();
    }
}
