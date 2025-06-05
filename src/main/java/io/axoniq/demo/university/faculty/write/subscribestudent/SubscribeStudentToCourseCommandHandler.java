package io.axoniq.demo.university.faculty.write.subscribestudent;

import io.axoniq.demo.university.faculty.events.StudentSubscribedToCourseEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.gateway.EventAppender;
import org.axonframework.modelling.annotation.InjectEntity;

import java.util.UUID;

class SubscribeStudentToCourseCommandHandler {

    @CommandHandler
    public void decide(SubscribeStudentToCourseCommand command,
                       @InjectEntity SubscribeStudentState state,
                       EventAppender appender) {
//        assertStudentEnrolledInFaculty(state);
//        assertStudentAlreadySubscribed(state);
//        assertCourseStillHasCapacity(state);
//        assertStudentHasTimeInSchedule(state);

        appender.append(new StudentSubscribedToCourseEvent(command.studentId(), command.courseId()));
    }

    private void assertStudentEnrolledInFaculty(SubscribeStudentState state) {
        UUID studentId = state.studentId();
        if (studentId == null) {
            throw new IllegalArgumentException("Student with given id never enrolled the faculty");
        }
    }

    private void assertStudentAlreadySubscribed(SubscribeStudentState state) {
        if (state.alreadySubscribed()) {
            throw new IllegalArgumentException("Student already subscribed to this course");
        }
    }

    private void assertCourseStillHasCapacity(SubscribeStudentState state) {
        if (!state.courseHasSufficientCapacity()) {
            throw new IllegalArgumentException("Course is fully booked");
        }
    }

    private void assertStudentHasTimeInSchedule(SubscribeStudentState state) {
        if (state.studentHasNoMoreTime()) {
            throw new IllegalArgumentException("Student subscribed to too many courses");
        }
    }
}
