package com.auth.service.repository;

import com.auth.service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  RoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleId(int roleId);
}
