package io.axoniq.demo.university.faculty.write.createcourse;

import io.axoniq.demo.university.faculty.events.CourseCreatedEvent;
import org.axonframework.eventhandling.gateway.EventAppender;

public class CreateCourseCommandHandler {

    public void handle(CreateCourseCommand command,
                       EventAppender appender,
                       CreateCourseState state) {
        if (state.identifier() == null) {
            appender.append(new CourseCreatedEvent(command.courseId(), command.name(), command.capacity()));
        }
    }
}
