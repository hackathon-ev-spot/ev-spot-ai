package org.rdutta.notificationservice.traffic;

import org.rdutta.notificationservice.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationRequest {
    private final NotificationDto notificationDto;

    public NotificationRequest(NotificationDto notificationDto) {
        this.notificationDto = notificationDto;
    }

    public NotificationDto getNotificationDto() {
        return notificationDto;
    }
}
