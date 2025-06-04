package io.axoniq.demo.university.faculty.events;

import org.axonframework.eventsourcing.annotations.EventTag;

import java.util.UUID;

public record CourseCreatedEvent(
        @EventTag
        UUID courseId,
        String name,
        int capacity
) {

}
