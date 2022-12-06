package com.araa.project.Service;

import com.araa.project.Entity.User;
import com.araa.project.Exception.EmailAlreadyRegisteredException;
import com.araa.project.Repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        try {
            userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new EmailAlreadyRegisteredException("Email "+user.getEmail()+" already registered");
        }

    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
