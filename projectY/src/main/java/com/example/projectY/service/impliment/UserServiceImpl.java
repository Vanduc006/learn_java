package com.example.projectY.service.impliment;

import java.io.IOException;
import java.lang.foreign.Linker.Option;
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

import com.example.projectY.entity.Company;
import com.example.projectY.entity.User;
import com.example.projectY.repository.CompanyRepository;
import com.example.projectY.repository.UserRepository;
import com.example.projectY.response.CompanyUserDTO;
import com.example.projectY.response.ResCreateUserDTO;
import com.example.projectY.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public User getUserByEmail(String email) {
        User currentUser = this.userRepository.findByEmail(email);
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

    @Override
    public User updateUser(Long id, User updateUser) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
        }
        User currentUser = optionalUser.get();
        // if (updateUser.getUsername() != null) {
        //     currentUser.setUsername(updateUser.getUsername());
        // }

        // if (updateUser.getEmail() != null) {
        //     currentUser.setEmail(updateUser.getEmail());
        // }

        // if (updateUser.getPassword() != null) {
        //     currentUser.setPassword(updateUser.getPassword());
        // }

        // if (updateUser.getAge() != null) {
        //     currentUser.setAge(updateUser.getAge());
        // }

        // if (updateUser.getAddress() != null) {
        //     currentUser.setAddress(updateUser.getAddress());
        // }
        // if (updateUser.getRefreshToken() != null) {
        //     currentUser.setRefreshToken(updateUser.getRefreshToken());
        // }
        // if (updateUser.getCreatedAt() != null) {
        //     currentUser.setCreatedAt(updateUser.getCreatedAt());
        // }
        // if (updateUser.getUpdatedAt() != null) {
        //     currentUser.setUpdatedAt(updateUser.getUpdatedAt());
        // }
        // if (updateUser.getCreatedBy() != null) {
        //     currentUser.setCreatedBy(updateUser.getCreatedBy());
        // }
        // if (updateUser.getUpdatedBy() != null) {
        //     currentUser.setUpdatedBy(updateUser.getUpdatedBy());
        // }

        BeanUtils.copyProperties(updateUser, currentUser, "id");
        return this.userRepository.save(currentUser);
    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found");
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
}
