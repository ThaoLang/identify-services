package com.example.identify_services.repository;

import com.example.identify_services.entity.Permission;
import com.example.identify_services.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
}
