package com.example;

import com.google.common.collect.Lists;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/test1")
public class FooBar {

    private static Collection coll = Lists.newArrayList();


    @GET
    @Path("/path1")
    public Response test(@QueryParam("value") String value) throws InterruptedException {
        coll.add(value);
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
