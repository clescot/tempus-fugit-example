package com.example;

import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(ConcurrentTestRunner.class)
public class ConcurrentTest extends WebRunner{




        private static Logger LOGGER = LoggerFactory.getLogger(MultiThreadTest.class);

        private WebResource webResource1 = resource().path("/test1/path1").queryParam("value", "4");

        @Test
        public void first_test() {

            for (long size = 1; size < 10; size++) {
                assertThat(Long.parseLong(call(webResource1)), is(size));
            }
            WebResource webResource2 = resource().path("/test1/clear");
            call(webResource2);

        }

        @Test
        public void second_test() {

            for (long size = 1; size < 10; size++) {
                assertThat(Long.parseLong(call(webResource1)), is(size));
            }
            WebResource webResource2 = resource().path("/test1/clear");
            call(webResource2);

        }

        @Test
        public void third_test() {

            for (long size = 1; size < 10; size++) {
                assertThat(Long.parseLong(call(webResource1)), is(size));
            }
            WebResource webResource2 = resource().path("/test1/clear");
            call(webResource2);

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
