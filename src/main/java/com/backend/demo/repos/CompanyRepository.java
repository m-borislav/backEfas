package com.backend.demo.repos;

import com.backend.demo.models.Company;
import com.backend.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByUser (Optional<User> user);
}
