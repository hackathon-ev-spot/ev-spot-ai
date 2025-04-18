package org.rdutta.notificationservice.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Keystore {
    @Value("${spring.cloud.gcp.topic-id}")
    public String topicId;
    @Value("${spring.cloud.gcp.subscription-id}")
    public String subscriptionId;
    @Value("${spring.cloud.gcp.project-id}")
    public String projectId;

    public String getTopicId() {
        return topicId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getProjectId() {
        return projectId;
    }
}
