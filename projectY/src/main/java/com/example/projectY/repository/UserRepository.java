package com.example.projectY.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.projectY.entity.Company;
import com.example.projectY.entity.Role;
import com.example.projectY.entity.User;
// import java.util.List;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    // @Autowired
    // @Override
    public Optional<User> findById(Long id);
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
    public Optional<User> findByRefreshToken(String refreshToken);
    public Optional<User> findByRefreshTokenAndEmail(String refreshToken, String email);
    public List<User> findByCompany(Company company);
    public List<User> findByRole(Role role);
}
