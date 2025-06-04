package io.axoniq.demo.university.faculty.write.createcourse;

import io.axoniq.demo.university.faculty.events.CourseCreatedEvent;
import org.axonframework.eventhandling.gateway.EventAppender;

import java.util.UUID;

class CreateCourseCommandHandler {

    public UUID handle(CreateCourseCommand command,
                       EventAppender appender,
                       CreateCourseState state) {
        if (state.identifier() == null) {
            appender.append(new CourseCreatedEvent(command.courseId(), command.name(), command.capacity()));
            return state.identifier();
        }
        throw new IllegalArgumentException("Course identifier already in use!");
    }
}
