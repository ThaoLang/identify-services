package com.example.identify_services.mapper;

import com.example.identify_services.dto.request.RoleRequest;
import com.example.identify_services.dto.response.RoleResponse;
import com.example.identify_services.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
