package com.example.identify_services.mapper;

import com.example.identify_services.dto.request.PermissionRequest;
import com.example.identify_services.dto.response.PermissonResponse;
import com.example.identify_services.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);


    PermissonResponse toPermissionResponse(Permission permission);
}
