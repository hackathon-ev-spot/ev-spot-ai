package org.rdutta.notificationservice.controller;


import org.rdutta.notificationservice.service.NotifyService;
import org.rdutta.notificationservice.traffic.NotificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/notify")
public class NotificationController {
    private final NotifyService notifyService;
    public NotificationController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @PostMapping(value = "/mail")
    public ResponseEntity<String> notifyMessage(@RequestBody NotificationRequest notificationRequest) throws IOException, InterruptedException {
        notifyService.notify(notificationRequest.getNotificationDto());
        return new ResponseEntity<>(" Order placed successfully!", HttpStatus.OK);
    }
}
