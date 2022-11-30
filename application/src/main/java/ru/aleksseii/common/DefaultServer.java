package ru.aleksseii.common;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.jetbrains.annotations.NotNull;

public final class DefaultServer {

    private static final @NotNull String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 3466;

    public static @NotNull Server build() {
        return build(DEFAULT_HOST, DEFAULT_PORT);
    }

    public static @NotNull Server build(String host, int port) {

        final @NotNull Server server = new Server();

        //noinspection resource
        final ServerConnector serverConnector = new ServerConnector(server, new HttpConnectionFactory());
        serverConnector.setHost(host);
        serverConnector.setPort(port);
        server.setConnectors(new Connector[]{ serverConnector });

        return server;
    }
}
