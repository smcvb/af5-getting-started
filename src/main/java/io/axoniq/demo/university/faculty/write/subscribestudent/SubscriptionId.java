package io.axoniq.demo.university.faculty.write.subscribestudent;

import java.util.UUID;

public record SubscriptionId(
        UUID studentId,
        UUID courseId
) {

}
