package com.neo.api.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class JwtClaimsForwardingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (Jwt) ctx.getAuthentication().getPrincipal())
                .map(jwt -> {

                    List<String> roles = new ArrayList<>();

                    // 1. Realm roles
                    Map<String, Object> realmAccess = jwt.getClaim("realm_access");
                    if (realmAccess != null && realmAccess.get("roles") instanceof List<?> realmRoles) {
                        realmRoles.forEach(r -> roles.add(String.valueOf(r)));
                    }

                    // 2. Client roles
                    Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
                    if (resourceAccess != null && resourceAccess.get(jwt.getClaimAsString("azp")) instanceof Map<?, ?> clientRolesMap) {
                        if (clientRolesMap.get("roles") instanceof List<?> clientRoles) {
                            clientRoles.forEach(r -> roles.add(String.valueOf(r)));
                        }
                    }

                    // build new request with forwarded headers
                    return exchange.mutate()
                            .request(r -> r
                                    .header("X-User-Id", jwt.getClaimAsString("sub"))
                                    .header("X-User-Roles", String.join(",", roles))
                                    .header("Authorization", "Bearer " + jwt.getTokenValue())
                            )
                            .build();
                })
                .flatMap(chain::filter);
    }
}
