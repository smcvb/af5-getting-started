package io.axoniq.demo.university.faculty.subscribestudent;

import io.axoniq.demo.university.AxonUniversity;
import io.axoniq.demo.university.faculty.events.CourseCreatedEvent;
import io.axoniq.demo.university.faculty.events.StudentEnrolledIntoFacultyEvent;
import io.axoniq.demo.university.faculty.events.StudentSubscribedToCourseEvent;
import io.axoniq.demo.university.faculty.write.subscribestudent.SubscribeStudentToCourseCommand;
import org.axonframework.test.fixture.AxonTestFixture;
import org.junit.jupiter.api.*;

import java.util.UUID;

class SubscribeStudentToCourseTest {

    private AxonTestFixture fixture;

    @BeforeEach
    void beforeEach() {
        fixture = AxonTestFixture.with(AxonUniversity.mainConfigurer());
    }

    @Test
    void successfulSubscription() {
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        fixture.given()
               .event(new CourseCreatedEvent(courseId, "AF5 Demo Course", 10))
               .event(new StudentEnrolledIntoFacultyEvent(studentId, "Steven", "van Beelen"))
               .when()
               .command(new SubscribeStudentToCourseCommand(studentId, courseId))
               .then()
               .events(new StudentSubscribedToCourseEvent(studentId, courseId));
    }

    @Test
    void studentAlreadySubscribed() {
        UUID courseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        fixture.given()
               .event(new StudentEnrolledIntoFacultyEvent(studentId, "Allard", "Buijze"))
               .event(new CourseCreatedEvent(courseId, "AF5 Demo Course", 2))
               .event(new StudentSubscribedToCourseEvent(studentId, courseId))
               .when()
               .command(new SubscribeStudentToCourseCommand(studentId, courseId))
               .then()
               .exception(IllegalArgumentException.class, "Student already subscribed to this course");
    }

    @Test
    void courseFullyBooked() {
        UUID courseId = UUID.randomUUID();
        UUID student1Id = UUID.randomUUID();
        UUID student2Id = UUID.randomUUID();
        UUID student3Id = UUID.randomUUID();

        fixture.given()
               .event(new StudentEnrolledIntoFacultyEvent(student1Id, "Mateusz", "Nowak"))
               .event(new StudentEnrolledIntoFacultyEvent(student2Id, "Steven", "van Beelen"))
               .event(new StudentEnrolledIntoFacultyEvent(student3Id, "Mitchell", "Herrijgers"))
               .event(new CourseCreatedEvent(courseId, "AF5 Demo Course", 2))
               .event(new StudentSubscribedToCourseEvent(student1Id, courseId))
               .event(new StudentSubscribedToCourseEvent(student2Id, courseId))
               .when()
               .command(new SubscribeStudentToCourseCommand(student3Id, courseId))
               .then()
               .exception(IllegalArgumentException.class, "Course is fully booked");
    }

    @Test
    void studentSubscribedToTooManyCourses() {
        UUID course1Id = UUID.randomUUID();
        UUID course2Id = UUID.randomUUID();
        UUID course3Id = UUID.randomUUID();
        UUID targetCourseId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        fixture.given()
               .event(new StudentEnrolledIntoFacultyEvent(studentId, "Milan", "Savic"))
               .event(new CourseCreatedEvent(targetCourseId, "AF5 Demo Course", 10))
               .event(new CourseCreatedEvent(course1Id, "Course 1", 10))
               .event(new CourseCreatedEvent(course2Id, "Course 2", 10))
               .event(new CourseCreatedEvent(course3Id, "Course 3", 10))
               .event(new StudentSubscribedToCourseEvent(studentId, course1Id))
               .event(new StudentSubscribedToCourseEvent(studentId, course2Id))
               .event(new StudentSubscribedToCourseEvent(studentId, course3Id))
               .when()
               .command(new SubscribeStudentToCourseCommand(studentId, targetCourseId))
               .then()
               .noEvents()
               .exception(IllegalArgumentException.class, "Student subscribed to too many courses");
    }
}
