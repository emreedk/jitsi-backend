package com.jitsi.jitsi_backend.Service;

import com.jitsi.jitsi_backend.Dto.UserDto;
import com.jitsi.jitsi_backend.Entity.User;
import com.jitsi.jitsi_backend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::fromEntity);
    }

    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(userDto.toEntity());
        return UserDto.fromEntity(savedUser);
    }

    public UserDto updateUser(Long id, UserDto updatedUserDto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUserDto.getUsername());
                    user.setPhoneNumber(updatedUserDto.getPhoneNumber());
                    user.setProfileImageUrl(updatedUserDto.getProfileImageUrl());
                    user.setFcmToken(updatedUserDto.getFcmToken());
                    user.setIsOnline(updatedUserDto.isOnline());
                    user.setLastSeen(LocalDateTime.now());
                    return UserDto.fromEntity(userRepository.save(user));
                })
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
