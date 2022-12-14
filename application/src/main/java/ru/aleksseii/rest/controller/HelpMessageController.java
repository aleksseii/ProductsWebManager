package ru.aleksseii.rest.controller;

import org.jetbrains.annotations.NotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.InputStream;

@Path("/")
public final class HelpMessageController {

    @GET
    public @NotNull InputStream getHelpMessage() {

        final InputStream resourceStream = HelpMessageController.class.getResourceAsStream("/static/help.html");
        assert resourceStream != null;
        return resourceStream;
    }
}
