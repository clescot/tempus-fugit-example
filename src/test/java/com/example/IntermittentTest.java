package com.example;

import com.google.code.tempusfugit.concurrency.IntermittentTestRunner;
import com.google.code.tempusfugit.concurrency.annotations.Intermittent;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(IntermittentTestRunner.class)
public class IntermittentTest extends WebRunner{

    private static Logger LOGGER = LoggerFactory.getLogger(MultiThreadTest.class);
    private WebResource webResource1 = resource().path("/test1/path1").queryParam("value", "4");


    @Test
    @Intermittent(repetition = 30)
    public void test_method1_with_intermittent_annotation() {
        call(webResource1);
        LOGGER.info("method1 executed");
    }

    @Test
    @Intermittent(repetition = 10)
    public void test_method2_with_intermittent_annotation() {
        call(webResource1);
        LOGGER.info("method2 executed");
    }

    private String call(final WebResource webResource) {

        String response = null;
        try {
            response = webResource.get(String.class);
            assertThat("path =" + webResource.getURI() + "response=" + response, !response.isEmpty(), is(true));
            LOGGER.info("response=" + response);
        } catch (UniformInterfaceException t) {
            Assert.fail(t.getMessage());
            LOGGER.error("erreur message=" + t.getMessage());
            LOGGER.error("erreur réponse=" + t.getResponse());
        }

        return response;

    }
}
