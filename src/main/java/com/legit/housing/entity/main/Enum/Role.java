package com.legit.housing.entity.main.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.legit.housing.entity.main.Enum.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE,
                    TENANT_READ,
                    TENANT_UPDATE,
                    TENANT_DELETE,
                    TENANT_CREATE,
                    LANDLORD_READ,
                    LANDLORD_UPDATE,
                    LANDLORD_DELETE,
                    LANDLORD_CREATE,
                    AGENT_READ,
                    AGENT_UPDATE,
                    AGENT_DELETE,
                    AGENT_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE,
                    TENANT_READ,
                    TENANT_UPDATE,
                    TENANT_DELETE,
                    TENANT_CREATE,
                    LANDLORD_READ,
                    LANDLORD_UPDATE,
                    LANDLORD_DELETE,
                    LANDLORD_CREATE,
                    AGENT_READ,
                    AGENT_UPDATE,
                    AGENT_DELETE,
                    AGENT_CREATE
            )
    ),
    TENANT(
            Set.of(
                    TENANT_READ,
                    TENANT_UPDATE,
                    TENANT_CREATE
            )
    ),
    LANDLORD(
            Set.of(
                    LANDLORD_READ,
                    LANDLORD_UPDATE,
                    LANDLORD_CREATE
            )
    ),
    AGENT(
            Set.of(
                    AGENT_READ,
                    AGENT_UPDATE,
                    AGENT_CREATE
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
