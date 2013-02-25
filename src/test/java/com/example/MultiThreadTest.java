package com.example;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class MultiThreadTest extends WebRunner {

    private static Logger LOGGER = LoggerFactory.getLogger(MultiThreadTest.class);

    @Rule
    public ConcurrentRule concurrentRule = new ConcurrentRule();

    @Rule
    public RepeatingRule repeatingRule = new RepeatingRule();
    private WebResource webResource1 = resource().path("/test1/path1").queryParam("value", "4");

    @Test
    public void testResources_without_concurrent_annotation() {

        for (long size = 1; size < 10; size++) {
            assertThat(Long.parseLong(call(webResource1)), is(size));
        }
        WebResource webResource2 = resource().path("/test1/clear");
        call(webResource2);

    }

    @Test
//    @Repeating(repetition = 2)
    @Concurrent(count = 2)
    public void testResources_with_concurrent_annotation() {

        for (long size = 1; size < 10; size++) {
            assertThat(Long.parseLong(call(webResource1)), is(size));

            WebResource webResource2 = resource().path("/test1/clear");
            call(webResource2);
        }
    }

    @Test
    @Repeating(repetition = 2)
    @Concurrent(count = 2)
    public void testResources_with_concurrent_and_repeating_annotation() {

        for (long size = 1; size < 10; size++) {
            assertThat(Long.parseLong(call(webResource1)), is(size));

            WebResource webResource2 = resource().path("/test1/clear");
            call(webResource2);
        }
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
