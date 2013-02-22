package com.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;



public class MultiThreadTest extends WebRunner {

    @Rule
    public ConcurrentRule concurrentRule = new ConcurrentRule();

    @Rule
    public RepeatingRule repeatingRule = new RepeatingRule();
    private WebResource webResource1 =resource().path("/test1/method1").queryParam("value", "4");

    @Test
    @Repeating(repetition = 2)
    public void testResources_without_concurrent_annotation() {



        for (long size=1;size<10;size++) {
            assertThat(Long.parseLong(call(webResource1)), is(size));
        }
        WebResource webResource2 = resource().path("/test1/clear");
        call(webResource2);
    }

    @Test
    @Repeating(repetition = 2)
    @Concurrent(count = 2)
    public void testResources_with_concurrent_annotation() {



        for (long size=1;size<10;size++) {
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
            System.out.println("response="+response);
        } catch (UniformInterfaceException t) {
            Assert.fail(t.getMessage());
            System.out.println("erreur message=" + t.getMessage());
            System.out.println("erreur réponse=" + t.getResponse());
        }

        return response;
    }
}
