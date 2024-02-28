package com.act.school_xx.repository;

import com.act.school_xx.models.Role;
import com.act.school_xx.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);


    List<User> findByRoleAndFirstNameContainingIgnoreCaseOrRoleAndLastNameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCaseOrRoleAndMobileContainingIgnoreCase(Role role, String firstName, Role role1, String lastName, Role role2, String email, Role role3, String mobile);


    List<User> findByRole(Role role);


}
