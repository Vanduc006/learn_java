package com.example.projectY.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.projectY.entity.Company;
import com.example.projectY.entity.User;
import com.example.projectY.repository.UserRepository;
import com.example.projectY.response.CompanyUserDTO;
import com.example.projectY.response.ResCreateUserDTO;

public interface UserService {
    public Page<User> getAllUser(Pageable pageable); // Read

    public User getUserById(Long id); // Read

    public User updateUser(Long id, User updateUser); // Update

    public User createUser(User newUser); // Create

    public String deleteUser(Long id);

    public User getUserByEmail(String email);

    public Page<User> handleFilterUser(Specification<User> userSpecification, Pageable pageable);

    public void updateUserToken(String token, String email);

    public User getUserRefreshToken(String refreshToken);

    public Boolean validUserRefreshTokenAndEmail(String refreshToken, String email);

    public void removeRefreshToken(String email);

    public CompanyUserDTO handleCompanyUser(Long id);

    public void deleteUserByCompany(Company company);
}
