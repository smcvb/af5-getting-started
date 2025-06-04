package io.axoniq.demo.university.faculty.createcourse;

import io.axoniq.demo.university.AxonUniversity;
import io.axoniq.demo.university.faculty.events.CourseCreatedEvent;
import io.axoniq.demo.university.faculty.write.createcourse.CreateCourseCommand;
import org.axonframework.test.fixture.AxonTestFixture;
import org.junit.jupiter.api.*;

import java.util.UUID;

class CreateCourseTest {

    private static final UUID COURSE_ID = UUID.randomUUID();
    private static final String NAME = "AF5 Demo Course";
    private static final int CAPACITY = 10;

    private AxonTestFixture fixture;

    @BeforeEach
    void setUp() {
        fixture = AxonTestFixture.with(AxonUniversity.mainConfigurer());
    }

    @Test
    void givenNoEventsWhenCreateCourseThenSuccess() {
        fixture.given()
               .noPriorActivity()
               .when()
               .command(new CreateCourseCommand(COURSE_ID, NAME, CAPACITY))
               .then()
               .events(new CourseCreatedEvent(COURSE_ID, NAME, CAPACITY));
    }

    @Test
    void givenCourseCreatedWhenCreateCourseForSameIdThenNoEvents() {
        fixture.given()
               .events(new CourseCreatedEvent(COURSE_ID, NAME, CAPACITY))
               .when()
               .command(new CreateCourseCommand(COURSE_ID, NAME, CAPACITY))
               .then()
               .noEvents();
    }
}
