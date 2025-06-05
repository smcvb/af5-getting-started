package io.axoniq.demo.university.faculty.write.subscribestudent;

import io.axoniq.demo.university.faculty.events.StudentSubscribedToCourseEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.gateway.EventAppender;
import org.axonframework.modelling.annotation.InjectEntity;

class SubscribeStudentToCourseCommandHandler {

    @CommandHandler
    public void decide(SubscribeStudentToCourseCommand command,
                       @InjectEntity SubscribeStudentState state,
                       EventAppender appender) {

        appender.append(new StudentSubscribedToCourseEvent(command.studentId(), command.courseId()));
    }
}
