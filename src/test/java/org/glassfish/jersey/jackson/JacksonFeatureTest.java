package org.glassfish.jersey.jackson;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.RealJenkinsRule;

public class JacksonFeatureTest {

    @Rule public RealJenkinsRule rr = new RealJenkinsRule();

    @Test
    public void smokes() throws Throwable {
        rr.then(j -> new JacksonFeature());
    }
}
