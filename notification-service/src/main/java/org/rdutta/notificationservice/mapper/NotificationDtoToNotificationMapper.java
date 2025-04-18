package org.rdutta.notificationservice.mapper;

import org.rdutta.notificationservice.dto.NotificationDto;
import org.rdutta.notificationservice.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationDtoToNotificationMapper {
    public static Notification mapOrderDto(NotificationDto dto) {
        return new Notification.Builder()
                .object(dto.getObject())
                .build();
    }
}