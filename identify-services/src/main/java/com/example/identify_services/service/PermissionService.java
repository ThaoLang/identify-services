package com.example.identify_services.service;

import com.example.identify_services.dto.request.PermissionRequest;
import com.example.identify_services.dto.response.PermissonResponse;
import com.example.identify_services.entity.Permission;
import com.example.identify_services.mapper.PermissionMapper;
import com.example.identify_services.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissonResponse create(PermissionRequest request){
        Permission permission=permissionMapper.toPermission(request);
        permission=permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissonResponse> getAll(){
        var permissions=permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
