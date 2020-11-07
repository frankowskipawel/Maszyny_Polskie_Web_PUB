package pl.maszyny_polskie.service;

import pl.maszyny_polskie.entity.Role;
import pl.maszyny_polskie.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    public Role findByRole(String roleName){
        return roleRepository.findByRole(roleName);
    }
}
