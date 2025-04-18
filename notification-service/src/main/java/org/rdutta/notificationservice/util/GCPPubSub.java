package org.rdutta.notificationservice.util;

import com.google.api.core.ApiFuture;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.NotFoundException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class GCPPubSub {

    private final CredentialsProvider credentialsProvider;

    public GCPPubSub(@Value("${spring.cloud.gcp.pubsub.credentials.location}") String credentialsPath) throws IOException {
        byte[] serviceAccountBytes = Files.readAllBytes(Paths.get(URI.create(credentialsPath)));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serviceAccountBytes);
        this.credentialsProvider = FixedCredentialsProvider.create(
                ServiceAccountCredentials.fromStream(byteArrayInputStream)
        );
    }

    public String publishToTopic(String topicId, String projectId, String message)
            throws IOException, InterruptedException {

        if (!isTopicPresent(topicId, projectId)) {
            System.out.println("Topic does not exist: " + topicId);
            return null;
        }

        TopicName topicName = TopicName.of(projectId, topicId);

        Publisher publisher = null;
        try {
            publisher = Publisher.newBuilder(topicName)
                    .setCredentialsProvider(credentialsProvider)
                    .build();

            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            return messageIdFuture.get();

        } catch (ExecutionException e) {
            throw new RuntimeException("Publishing failed", e);
        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }

    private boolean isTopicPresent(String topicId, String projectId) {
        try {
            TopicAdminSettings settings = TopicAdminSettings.newBuilder()
                    .setCredentialsProvider(credentialsProvider)
                    .build();

            try (TopicAdminClient topicAdminClient = TopicAdminClient.create(settings)) {
                TopicName topicName = TopicName.of(projectId, topicId);
                topicAdminClient.getTopic(topicName);
                return true;
            }
        } catch (NotFoundException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}