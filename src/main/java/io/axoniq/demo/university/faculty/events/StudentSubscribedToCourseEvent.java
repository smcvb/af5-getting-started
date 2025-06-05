package io.axoniq.demo.university.faculty.events;

import org.axonframework.eventsourcing.annotations.EventTag;

import java.util.UUID;

public record StudentSubscribedToCourseEvent(
        @EventTag
        UUID studentId,
        @EventTag
        UUID courseId
) {

}
