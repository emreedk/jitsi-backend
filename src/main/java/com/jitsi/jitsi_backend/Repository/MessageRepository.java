package com.jitsi.jitsi_backend.Repository;

import com.jitsi.jitsi_backend.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.sender.id = :userId OR m.receiver.id = :userId")
    List<Message> findAllByUserId(@Param("userId") Long userId);

    List<Message> findByReceiverId(Long receiverId);
    List<Message> findByGroupId(Long groupId);
    List<Message> findByReceiverIdAndReadFalse(Long receiverId);
    List<Message> findByReceiverIdAndDeliveredFalse(Long receiverId);
}
