package com.example;

import static org.hamcrest.CoreMatchers.is;

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

    @Test
    @Repeating(repetition = 100)
    @Concurrent(count = 20)
    public void testResources() {
        List<WebResource> webResourceList = new ArrayList<WebResource>();
        WebResource webResource1 = resource().path("/test1/method1");
        webResourceList.add(webResource1);

        WebResource webResource2 = resource().path("/test1/method2");
//                .queryParam("param1", "value1")
//                .queryParam("param2", "value2");
        webResourceList.add(webResource2);
        for (WebResource resource : webResourceList) {
            call(resource);
        }
    }

    private void call(final WebResource webResource) {
        try {
            final String response = webResource.get(String.class);
            Assert.assertThat("path =" + webResource.getURI() + "response=" + response, !response.isEmpty(), is(true));
            System.out.println("threadId=" + Thread.currentThread().getName());
        } catch (UniformInterfaceException t) {
            Assert.fail(t.getMessage());
            System.out.println("erreur message=" + t.getMessage());
            System.out.println("erreur réponse=" + t.getResponse());
        }
    }
}
