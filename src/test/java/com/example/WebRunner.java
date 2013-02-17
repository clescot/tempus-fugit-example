package com.example;

import java.util.Random;

import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly2.web.GrizzlyWebTestContainerFactory;
import org.junit.Before;

public abstract class WebRunner extends JerseyTest {

    private static final String PACKAGE_OWNING_RESOURCES_TO_SERIALIZE = "com.example";

    private static final int BEGIN_PORT_RANGE_WE_GUESS_UNUSED = 5000;

    private static final int PORT_SIZE_TO_USE = 1000;

    private static Random random = new Random();

    private static final String DUMMY_CONTEXT = "test";

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder("com.example").contextPath(DUMMY_CONTEXT)
                .initParam("com.sun.jersey.config.property.resourceConfigClass",
                        "com.sun.jersey.api.core.PackagesResourceConfig")
                .initParam("com.sun.jersey.config.property.packages", PACKAGE_OWNING_RESOURCES_TO_SERIALIZE).build();
    }

    protected int getPort(int defaultPort) {
        return BEGIN_PORT_RANGE_WE_GUESS_UNUSED + random.nextInt(PORT_SIZE_TO_USE);
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }



}


