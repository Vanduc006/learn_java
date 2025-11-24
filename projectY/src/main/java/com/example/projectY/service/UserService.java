package com.example.projectY.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.projectY.entity.Company;
import com.example.projectY.entity.Role;
import com.example.projectY.entity.User;
import com.example.projectY.response.user.CompanyUserDTO;
import com.example.projectY.response.user.RoleUserDTO;

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

    public Boolean handleValidUser(Long id);

    public List<User> handleValidUsers(List<User> list);

    public RoleUserDTO handleRoleUserDTO(Long id); // role id

    public void deleteUserByRole(Role role);
}
