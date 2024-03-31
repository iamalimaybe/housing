package com.legit.housing.entity.main.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    TENANT_READ("tenant:read"),
    TENANT_UPDATE("tenant:update"),
    TENANT_CREATE("tenant:create"),
    TENANT_DELETE("tenant:delete"),
    LANDLORD_READ("landlord:read"),
    LANDLORD_UPDATE("landlord:update"),
    LANDLORD_CREATE("landlord:create"),
    LANDLORD_DELETE("landlord:delete"),
    AGENT_READ("agent:read"),
    AGENT_UPDATE("agent:update"),
    AGENT_CREATE("agent:create"),
    AGENT_DELETE("agent:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_DELETE("management:delete"),
    MANAGER_CREATE("management:create");

    private final String permission;
}
