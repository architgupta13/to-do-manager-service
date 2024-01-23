package org.archit.todomanagerservice.repository;

import java.util.Optional;
import org.archit.todomanagerservice.entity.Role;
import org.archit.todomanagerservice.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);

}