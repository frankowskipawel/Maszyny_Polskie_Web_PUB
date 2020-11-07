package pl.maszyny_polskie.service;

import pl.maszyny_polskie.entity.Role;

public interface RoleService {

    Role findByRole(String roleName);
}
