package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test1")
public class FooBar {

    @GET
    @Path("/method1")
    public Response test(){

        return Response.ok("good response").build();

    }

    @GET
    @Path("/method2")
    public Response test2(){

        return Response.ok("good response").build();

    }
}
