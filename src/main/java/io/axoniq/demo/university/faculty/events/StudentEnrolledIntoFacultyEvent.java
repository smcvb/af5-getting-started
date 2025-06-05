package io.axoniq.demo.university.faculty.events;

import org.axonframework.eventsourcing.annotations.EventTag;

import java.util.UUID;

public record StudentEnrolledIntoFacultyEvent(
        @EventTag UUID studentId,
        String firstName,
        String lastName
) {

}
