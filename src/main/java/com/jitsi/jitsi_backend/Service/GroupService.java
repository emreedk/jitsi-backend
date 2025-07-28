package com.jitsi.jitsi_backend.Service;

import com.jitsi.jitsi_backend.Dto.GroupDto;
import com.jitsi.jitsi_backend.Entity.Group;
import com.jitsi.jitsi_backend.Entity.User;
import com.jitsi.jitsi_backend.Repository.GroupRepository;
import com.jitsi.jitsi_backend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public GroupDto createGroup(GroupDto groupDto) {
        Group group = new Group(groupDto.getName());
        group.setMembers(
                groupDto.getMemberIds().stream()
                        .map(id -> userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + id)))
                        .collect(Collectors.toSet())
        );
        Group savedGroup = groupRepository.save(group);
        return GroupDto.fromEntity(savedGroup);
    }

    public List<GroupDto> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(GroupDto::fromEntity)
                .collect(Collectors.toList());
    }

    public GroupDto getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grup bulunamadı: " + id));
        return GroupDto.fromEntity(group);
    }
}
