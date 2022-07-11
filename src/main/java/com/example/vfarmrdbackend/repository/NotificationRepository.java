package com.example.vfarmrdbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query(value = "select * from notifications n where n.to_user_id = :to_user_id", nativeQuery = true)
    List<Notification> getAllNotificationByTo_user_id(@Param("to_user_id") int to_user_id);

    @Query(value = "select * from notifications n where n.notification_id = :notification_id", nativeQuery = true)
    Notification getNotificationById(@Param("notification_id") int notification_id);
}
