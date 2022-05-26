package com.aca;

import com.sun.glass.events.MouseEvent;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Root resource (exposed at "myresource" path)
 */

@Path("myresource")
public class myresource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        LocalDate currentDateTime = LocalDate.from(LocalDateTime.now());

        return "Today's date: " + String.valueOf(currentDateTime);

    }
}
