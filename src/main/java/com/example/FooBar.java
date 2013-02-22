package com.example;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

@Path("/test1")
public class FooBar {

    private static Collection coll = Lists.newArrayList();
    private static Random random = new Random();


    @GET
    @Path("/method1")
    public Response test(@QueryParam("value") String value) throws InterruptedException {
        coll.add(value);
        Thread.sleep(random.nextInt(1000));
        int size = coll.size();
        return Response.ok(""+size).build();

    }

    @GET
    @Path("/clear")
    public Response test2(@QueryParam("value") String value){
        coll.clear();
        return Response.ok("ok").build();
    }
}
