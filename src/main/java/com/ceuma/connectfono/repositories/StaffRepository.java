package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository <Staff, UUID> {
    @Query(value = "SELECT * from staff WHERE email = :email AND password = :password ", nativeQuery = true)
    Staff login(@Param("email")String email, @Param("password") String password);

    @Query(value = "SELECT * FROM staff WHERE cpf = :cpf", nativeQuery = true)
    Optional<Staff> findByCpf(@Param("cpf") String cpf);
}
