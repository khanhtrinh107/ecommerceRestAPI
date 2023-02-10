package com.example.demo.service;

import com.example.demo.entity.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
}
