package com.example.projectY.service.impliment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projectY.entity.Company;
import com.example.projectY.entity.Resume;
import com.example.projectY.entity.Role;
import com.example.projectY.entity.User;
import com.example.projectY.repository.CompanyRepository;
import com.example.projectY.repository.ResumeRepository;
import com.example.projectY.repository.RoleRepository;
import com.example.projectY.repository.UserRepository;
import com.example.projectY.response.user.CompanyUserDTO;
import com.example.projectY.response.user.RoleUserDTO;
import com.example.projectY.service.ResumeService;
import com.example.projectY.service.RoleService;
import com.example.projectY.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    // @Autowired ResumeService resumeService;
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired 
    private RoleRepository roleRepository;

    @Override
    public User getUserByEmail(String email) {
        User currentUser = this.userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            throw new UsernameNotFoundException("Bad crendetials");
        }
        return currentUser;
    }

    @Override
    public Page<User> getAllUser(Pageable pageable) { // Read
        return this.userRepository.findAll(pageable);   
    }

    @Override
    public User createUser(User newUser) { // Create
        boolean exists = this.userRepository.existsByEmail(newUser.getEmail());
        if (exists) {
            throw new DuplicateKeyException("Invalid email");
        }
        if (newUser.getCompany() != null) {
            
        }
        if (newUser.getRole() != null) {
            newUser.setRole(
                this.roleRepository.findById(newUser.getRole().getId()).orElse(null)
            );
        }

        return this.userRepository.save(newUser);
    }
    
    @Override
    public User getUserById(Long id) { // Read
        // List<User>
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
        }
        return optionalUser.get();
        // return optionalUser.map(user -> user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User updateUser) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
        }

        if (updateUser.getCompany() != null) {
            updateUser.setCompany(this.companyRepository.findById(updateUser.getCompany().getId()).orElse(null));
        }

        if (updateUser.getRole() != null) {
            updateUser.setRole(
                this.roleRepository.findById(updateUser.getRole().getId()).orElse(null)
            );
        }

        User currentUser = optionalUser.get();
        BeanUtils.copyProperties(updateUser, currentUser, "id", "createdAt", "createdBy");
        return this.userRepository.save(currentUser);
    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
        }
        if (optionalUser.isPresent()) {
            // this.resumeService.deleteResumeByUser(optionalUser.get());
            List<Resume> currentList = this.resumeRepository.findByUser(optionalUser.get());
            this.resumeRepository.deleteAll(currentList);

            // optionalUser.get().getRole()
        }

        User currentUser = optionalUser.get();
        this.userRepository.deleteById(currentUser.getId());
        return "Delete user with Id" + currentUser.getId();
        // return this.userRepository.deleteById(id);
    }

    @Override
    public Page<User> handleFilterUser(Specification<User> userSpecification, Pageable pageable) {
        return this.userRepository.findAll(userSpecification, pageable);
    }

    @Override
    public void updateUserToken(String token, String email) {
        User currentUser = this.getUserByEmail(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    @Override
    public User getUserRefreshToken(String refreshToken) {
        Optional<User> optionalUser = this.userRepository.findByRefreshToken(refreshToken);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("Refresh token not found");
        }
        User currentUser = optionalUser.get();
        return currentUser;
    }

    @Override
    public Boolean validUserRefreshTokenAndEmail(String refreshToken, String email) {
        Optional<User> optionalUser = this.userRepository.findByRefreshTokenAndEmail(refreshToken, email);
        return optionalUser.isPresent();
    }

    @Override
    public void removeRefreshToken(String email) {
        User currentUser = this.getUserByEmail(email);
        if (currentUser != null) {
            // currentUser.setRefreshToken(token);
            currentUser.setRefreshToken(null);
            this.userRepository.save(currentUser);
        }
    }

    @Override
    public CompanyUserDTO handleCompanyUser(Long id) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            throw new NoSuchElementException("Company not found");
        }
        CompanyUserDTO companyUser = new CompanyUserDTO();
        // companyUser
        BeanUtils.copyProperties(optionalCompany.get(), companyUser);
        return companyUser;
    }

    @Override
    public void deleteUserByCompany(Company company) {
        List<User> userList = this.userRepository.findByCompany(company);
        this.userRepository.deleteAll(userList);
    }

    public Boolean handleValidUser(Long id) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        return optionalCompany.isPresent();
    }

    public List<User> handleValidUsers(List<User> list) {
        List<User> validList = list.stream()
        .map(user -> this.userRepository.findById(user.getId()))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();

        return validList;
    }

    public RoleUserDTO handleRoleUserDTO(Long id) {
        Optional<Role> optionalRole= this.roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            throw new NoSuchElementException("Role not found");
        }
        RoleUserDTO roleUserDTO = new RoleUserDTO();
        BeanUtils.copyProperties(optionalRole.get(), roleUserDTO);
        return roleUserDTO;
    }

    public void deleteUserByRole(Role role) {
        this.userRepository.deleteAll(this.userRepository.findByRole(role));
    }
}
