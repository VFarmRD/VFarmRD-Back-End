package com.example.vfarmrdbackend.repository.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query(value = "select * from notifications n where n.user_id = :user_id order by n.created_date desc limit 7;", nativeQuery = true)
    List<Notification> getAllNotificationByUser_id(@Param("user_id") int user_id);

    @Query(value = "select * from notifications n where n.notification_id = :notification_id", nativeQuery = true)
    Notification getNotificationById(@Param("notification_id") int notification_id);
}
