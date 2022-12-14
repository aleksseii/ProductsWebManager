package ru.aleksseii.security;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.util.security.Constraint;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("SameParameterValue")
public final class SecurityHandlerBuilder {

    private static final @NotNull String ROLE_MANAGER = "manager";
    private static final @NotNull String ROLE_GUEST = "guest";

    private static final @NotNull List<@NotNull String> MANAGER_WEB_PAGES = List.of(
            "/product/all",
            "/product/all/*",
            "/product/add",
            "/product/delete/*"
    );
    private static final @NotNull List<@NotNull String> GUEST_WEB_PAGES = List.of(
            "/product/all",
            "/product/all/*"
    );

    public @NotNull ConstraintSecurityHandler build(@NotNull LoginService loginService) {

        final ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();

        securityHandler.setLoginService(loginService);

        Constraint managerConstraint = buildConstraint(ROLE_MANAGER);
        Constraint guestConstraint = buildConstraint(ROLE_GUEST, ROLE_MANAGER);

        List<ConstraintMapping> constraintMappings = Stream.concat(
                constraintAllMapping(managerConstraint, MANAGER_WEB_PAGES).stream(),
                constraintGetMapping(guestConstraint, GUEST_WEB_PAGES).stream()
        ).toList();

        securityHandler.setConstraintMappings(constraintMappings);
        securityHandler.setAuthenticator(new BasicAuthenticator());
        securityHandler.setDenyUncoveredHttpMethods(true);

        return securityHandler;
    }

    private static @NotNull Constraint buildConstraint(@NotNull String @NotNull ... roles) {

        final Constraint constraint = new Constraint();

        constraint.setName(Constraint.__BASIC_AUTH);
        constraint.setRoles(roles);
        constraint.setAuthenticate(true);

        return constraint;
    }

    private static @NotNull Collection<@NotNull ConstraintMapping> constraintGetMapping(
            @NotNull Constraint constraint,
            @NotNull Collection<@NotNull String> pathSpecs) {

        return constraintMapping(constraint, pathSpecs, "GET");
    }

    private static @NotNull Collection<@NotNull ConstraintMapping> constraintAllMapping(
            @NotNull Constraint constraint,
            @NotNull Collection<@NotNull String> pathSpecs) {

        return constraintMapping(constraint, pathSpecs, "*");
    }

    private static @NotNull Collection<@NotNull ConstraintMapping> constraintMapping(
            @NotNull Constraint constraint,
            @NotNull Collection<@NotNull String> pathSpecs,
            @NotNull String method) {

        return pathSpecs.stream()
                .map(pathSpec -> {
                    ConstraintMapping mapping = new ConstraintMapping();

                    mapping.setConstraint(constraint);
                    mapping.setPathSpec(pathSpec);
                    mapping.setMethod(method);

                    return mapping;
                })
                .toList();
    }
}
