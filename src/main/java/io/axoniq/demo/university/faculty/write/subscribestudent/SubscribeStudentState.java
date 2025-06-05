package io.axoniq.demo.university.faculty.write.subscribestudent;

import io.axoniq.demo.university.faculty.events.CourseCreatedEvent;
import io.axoniq.demo.university.faculty.events.StudentEnrolledIntoFacultyEvent;
import io.axoniq.demo.university.faculty.events.StudentSubscribedToCourseEvent;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.annotation.EventCriteriaBuilder;
import org.axonframework.eventsourcing.annotation.EventSourcedEntity;
import org.axonframework.eventsourcing.eventstore.EventCriteria;
import org.axonframework.eventsourcing.eventstore.Tag;
import org.axonframework.messaging.QualifiedName;

import java.util.UUID;

@EventSourcedEntity
public class SubscribeStudentState {

    private UUID courseId;
    private int capacity;
    private int subscribedStudentsCount;

    private UUID studentId;
    private int subscribedToCoursesCount;
    private boolean alreadySubscribedToThisCourse;

    @EventSourcingHandler
    public void on(StudentEnrolledIntoFacultyEvent event) {
        this.studentId = event.studentId();
        this.subscribedToCoursesCount = 0;
        this.alreadySubscribedToThisCourse = false;
    }

    public UUID studentId() {
        return studentId;
    }

    @EventSourcingHandler
    public void on(CourseCreatedEvent event) {
        this.courseId = event.courseId();
        this.capacity = event.capacity();
        this.subscribedStudentsCount = 0;
    }

    @EventSourcingHandler
    public void on(StudentSubscribedToCourseEvent event) {
        UUID subscribedStudentId = event.studentId();
        UUID subscribedCourseId = event.courseId();

        if (subscribedCourseId.equals(courseId)) {
            this.subscribedStudentsCount++;
        }

        if (subscribedStudentId.equals(studentId())) {
            this.subscribedToCoursesCount++;
        }

        if (subscribedStudentId.equals(studentId) && subscribedCourseId.equals(courseId)) {
            this.alreadySubscribedToThisCourse = true;
        }
    }

    public boolean alreadySubscribed() {
        return this.alreadySubscribedToThisCourse;
    }

    public boolean courseHasSufficientCapacity() {
        return subscribedStudentsCount < capacity;
    }

    public boolean studentHasNoMoreTime() {
        return subscribedToCoursesCount >= 3;
    }

    @EventCriteriaBuilder
    public static EventCriteria criteriaBuilder(SubscriptionId subscriptionId) {
        return EventCriteria.havingTags(new Tag("courseId", subscriptionId.courseId().toString()))
                            .andBeingOneOfTypes(
                                    new QualifiedName(CourseCreatedEvent.class),
                                    new QualifiedName(StudentSubscribedToCourseEvent.class)
                            )
                            .or()
                            .havingTags(new Tag("studentId", subscriptionId.studentId().toString()))
                            .andBeingOneOfTypes(
                                    new QualifiedName(StudentEnrolledIntoFacultyEvent.class),
                                    new QualifiedName(StudentSubscribedToCourseEvent.class)
                            );
    }
}
