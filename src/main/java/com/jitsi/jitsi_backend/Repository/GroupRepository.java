package com.jitsi.jitsi_backend.Repository;

import com.jitsi.jitsi_backend.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
