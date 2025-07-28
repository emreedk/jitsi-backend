package com.jitsi.jitsi_backend.Dto;

import com.jitsi.jitsi_backend.Entity.Group;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupDto {
    private Long id;
    private String name;
    private Set<Long> memberIds;

    public GroupDto() {}

    public GroupDto(Long id, String name, Set<Long> memberIds) {
        this.id = id;
        this.name = name;
        this.memberIds = memberIds;
    }

    public static GroupDto fromEntity(Group group) {
        Set<Long> memberIds = group.getMembers().stream()
                .map(user -> user.getId())
                .collect(Collectors.toSet());
        return new GroupDto(group.getId(), group.getName(), memberIds);
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Long> getMemberIds() { return memberIds; }
    public void setMemberIds(Set<Long> memberIds) { this.memberIds = memberIds; }
}
