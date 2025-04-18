package org.rdutta.notificationservice.service;


import org.rdutta.notificationservice.dto.NotificationDto;
import org.rdutta.notificationservice.model.Notification;
import org.rdutta.notificationservice.util.GCPPubSub;
import org.rdutta.notificationservice.util.Keystore;
import org.springframework.stereotype.Service;
import java.io.IOException;

import static org.rdutta.notificationservice.mapper.NotificationDtoToNotificationMapper.mapOrderDto;
import static org.rdutta.notificationservice.util.ObjectToJSONString.objectToJSONString;

@Service
public class NotifyService {

    private final GCPPubSub gcpPubSub;
    private final Keystore keystore;

    public NotifyService(GCPPubSub gcpPubSub, Keystore keystore) {
        this.gcpPubSub = gcpPubSub;
        this.keystore = keystore;
    }

    public void notify(NotificationDto notificationDto) throws IOException, InterruptedException {
        Notification notification = mapOrderDto(notificationDto);
        gcpPubSub.publishToTopic(keystore.getTopicId(), keystore.getProjectId(), objectToJSONString(notification));
    }
}
