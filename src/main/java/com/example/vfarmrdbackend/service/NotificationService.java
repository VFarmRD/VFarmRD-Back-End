package com.example.vfarmrdbackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.Notification;
import com.example.vfarmrdbackend.payload.response.NotificationGetResponse;
import com.example.vfarmrdbackend.repository.NotificationRepository;
import com.example.vfarmrdbackend.repository.UserRepository;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    public List<NotificationGetResponse> getAllNotifications() {
        List<NotificationGetResponse> listResponse = new ArrayList<>();
        List<Notification> listNotifications = notificationRepository.findAll();
        for (int i = 0; i < listNotifications.size(); i++) {
            Notification notification = listNotifications.get(i);
            NotificationGetResponse response = new NotificationGetResponse();
            response.setNotification_id(notification.getNotification_id());
            response.setUser_id(notification.getUser_id());
            response.setUser_fullname(userRepository.getUserByUser_id(notification.getUser_id()).getFullname());
            response.setTitle(notification.getTitle());
            response.setMessage(notification.getMessage());
            listResponse.add(response);
        }
        return listResponse;
    }

    public List<NotificationGetResponse> getAllNotificationsOfUser(int user_id) {
        List<NotificationGetResponse> listResponse = new ArrayList<>();
        List<Notification> listNotifications = notificationRepository.getAllNotificationByUser_id(user_id);
        for (int i = 0; i < listNotifications.size(); i++) {
            Notification notification = listNotifications.get(i);
            NotificationGetResponse response = new NotificationGetResponse();
            response.setNotification_id(notification.getNotification_id());
            response.setUser_id(notification.getUser_id());
            response.setUser_fullname(userRepository.getUserByUser_id(notification.getUser_id()).getFullname());
            response.setTitle(notification.getTitle());
            response.setMessage(notification.getMessage());
            listResponse.add(response);
        }
        return listResponse;
    }

    public NotificationGetResponse getNotification(int notification_id) {
        Notification notification = notificationRepository.getNotificationById(notification_id);
        NotificationGetResponse response = new NotificationGetResponse();
        response.setNotification_id(notification.getNotification_id());
        response.setUser_id(notification.getUser_id());
        response.setUser_fullname(userRepository.getUserByUser_id(notification.getUser_id()).getFullname());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        return response;
    }

    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public boolean deleteNotification(int notification_id) {
        Notification notification = notificationRepository.getNotificationById(notification_id);
        if (notification != null) {
            notificationRepository.delete(notification);
            return true;
        }
        return false;
    }
}
