package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.SignUpRequest;
import com.example.demo.exception.ObjectExistedException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUserName(String username) {
         return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(SignUpRequest sign) throws ObjectExistedException {
        if(!ObjectUtils.isEmpty(userRepository.findByEmail(sign.getUsername()))){
            throw new ObjectExistedException("username existed!");
        }
        if(!ObjectUtils.isEmpty(userRepository.findByEmail(sign.getEmail()))){
            throw new ObjectExistedException("email existed!");
        }
        Set<String> roleSet = sign.getRoles();
        Set<Role> roles = new HashSet<>();
        if(ObjectUtils.isEmpty(roleSet)){
            roles.add(roleRepository.findByRoleName("ROLE_USER"));
        }
        roleSet.forEach(role ->{
            if(role.equals("admin"))
                roles.add(roleRepository.findByRoleName("ROLE_ADMIN"));
            else if (role.equals("user"))
                roles.add(roleRepository.findByRoleName("ROLE_USER"));
            else if(role.equals("editor"))
                roles.add(roleRepository.findByRoleName("ROLE_EDITOR"));
        });
        User user = new User();
        user.setUsername(sign.getUsername());
        user.setPassword(passwordEncoder.encode(sign.getPassword()));
        user.setEmail(sign.getEmail());
        user.setFirstName(sign.getFirstName());
        user.setLastName(sign.getLastName());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User update(SignUpRequest sign, int id) throws UserNotFoundException, ObjectExistedException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user have id + " + id));
        if(!ObjectUtils.isEmpty(userRepository.findByEmail(sign.getUsername()))){
            throw new ObjectExistedException("username existed!");
        }
        if(!ObjectUtils.isEmpty(userRepository.findByEmail(sign.getEmail()))){
            throw new ObjectExistedException("email existed!");
        }
        Set<String> roleSet = sign.getRoles();
        Set<Role> roles = new HashSet<>();
        roleSet.forEach(role ->{
            if(role.equals("admin"))
                roles.add(roleRepository.findByRoleName("ROLE_ADMIN"));
            else if (role.equals("user"))
                roles.add(roleRepository.findByRoleName("ROLE_USER"));
            else if(role.equals("editor"))
                roles.add(roleRepository.findByRoleName("ROLE_EDITOR"));
        });
        user.setUsername(sign.getUsername());
        user.setPassword(passwordEncoder.encode(sign.getPassword()));
        user.setEmail(sign.getEmail());
        user.setFirstName(sign.getFirstName());
        user.setLastName(sign.getLastName());
        user.setRoles(roles);
        return  userRepository.save(user);
    }

    @Override
    public User findById(int id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("no user have id " + id));
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("no user have id " + id));
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findUserBuyProduct() {
        return userRepository.findUserBuyProduct();
    }

}
