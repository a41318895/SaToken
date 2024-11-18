package com.akichou.satokentest.repository;

import com.akichou.satokentest.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional ;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsernameAndPassword(String username, String password) ;
}
