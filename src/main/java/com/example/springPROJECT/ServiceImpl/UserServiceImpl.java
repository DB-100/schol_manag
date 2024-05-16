package com.example.springPROJECT.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springPROJECT.Entity.User;
import com.example.springPROJECT.Repository.UserRepository;
import com.example.springPROJECT.Service.UserService;
import com.example.springPROJECT.exceptions.ResourceNotFoundException;
import com.example.springPROJECT.payloads.UserDto;

public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepository userRepository;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    // @Override
    // public UserDto updateUser(UserDto userDto, Integer userId) {
    //   User user = this.userRepository.findById(userId).orElseThrow(e-> new ResourceNotFoundEsception("User","id", userId));
    // }
    
@Override
public UserDto updateUser(UserDto userDto, Integer userId) {
    User user = this.userRepository.findById(userId)
                                   .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

    return null;
}

    @Override
    public UserDto getUserById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(optionalUser.get(), userDto);
            return userDto;
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
    private User dtoToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        return user;
        }

    public UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;

    }
   
}
