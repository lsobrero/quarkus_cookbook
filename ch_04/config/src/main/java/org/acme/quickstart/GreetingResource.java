package org.acme.quickstart;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

    // tag::config[]
    @Inject // <1>
    Config config;
    // end::config[]

    // tag::list[]
    @ConfigProperty(name = "greeting.suffix")
    List<String> suffixes;
    // end::list[]

    // tag::optional[]
    @ConfigProperty(name = "greeting.upper-case", 
                    defaultValue = "true") // <1>
    boolean upperCase;
    // end::optional[]

    // tag::validation[]
    @Min(1) // <1>
    @Max(3) // <2>
    @ConfigProperty(name = "greeting.repeat", defaultValue = "1")
    int numberOfRepetitions;
    // end::validation[]
    
    // tag::message[]
    @ConfigProperty(name = "greeting.message") // <1>
    String message; // <2>

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return message; // <3>
    }
    // end::message[]

    // tag::validation[]
    @GET
    @Path("/validation")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloValidation() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<numberOfRepetitions; i++) {
            sb.append(message);
        }
        return sb.toString();
    }
    // end::validation[]

    // tag::optional[]
    @GET
    @Path("/optional")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloOptional() {
        return upperCase ? message.toUpperCase() : message;
    }
    // end::optional[]

    // tag::list[]
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloList() {
        return message + suffixes.get(1);
    }
    // end::list[]

    // tag::config[]
    @GET
    @Path("/config")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloConfig() {
        config.getPropertyNames().forEach( p -> System.out.println(p)); // <2>

        return config.getValue("greeting.message", String.class); // <3>
    }
    // end::config[]

    // tag::configurations[]
    @Inject // <1>
    GreetingConfiguration greetingConfiguration;

    @GET
    @Path("/configurations")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloConfigurations() {
        return greetingConfiguration.message + greetingConfiguration.suffix;
    }
    // end::configurations[]

}