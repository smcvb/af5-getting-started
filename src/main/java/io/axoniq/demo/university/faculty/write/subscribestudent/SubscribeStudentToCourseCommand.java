package io.axoniq.demo.university.faculty.write.subscribestudent;

import org.axonframework.modelling.annotation.TargetEntityId;

import java.util.UUID;

public record SubscribeStudentToCourseCommand(
        UUID studentId,
        UUID courseId
) {

    @TargetEntityId
    public SubscriptionId subscriptionId() {
        return new SubscriptionId(studentId, courseId);
    }
}
