package com.example;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.junit.Assert;
import org.junit.Test;



public class MultiThreadTest extends WebRunner {

    @Test
    @Repeating(repetition = 100)
    @Concurrent(count = 20)
    public void testResources() {
        List<WebResource> webResourceList = new ArrayList<WebResource>();
        WebResource iAgencies = resource().path("/path1");
        webResourceList.add(iAgencies);
        WebResource iAgency = resource().path("/path2").queryParam("id", "123456");
        webResourceList.add(iAgency);
        WebResource classifieds = resource().path("/path3");
        webResourceList.add(classifieds);
        WebResource iClassified = resource().path("/path4").queryParam("id", "9876543");
        webResourceList.add(iClassified);
        WebResource iCount = resource().path("/path5");
        webResourceList.add(iCount);
        WebResource iNotification = resource().path("/path6");
        webResourceList.add(iNotification);
        WebResource iClassifieds = resource().path("/path7");
        webResourceList.add(iClassifieds);
        WebResource localisationsDeposit = resource().path("/path8")
                .queryParam("param1", "value1")
                .queryParam("param2", "value2");
        webResourceList.add(localisationsDeposit);
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
