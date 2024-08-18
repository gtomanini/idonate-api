package com.br.idonate.repository;

import com.br.idonate.model.RoleUser;
import com.br.idonate.utils.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleUser, Long> {
    Optional<RoleUser> findByName(RoleName name);
}
