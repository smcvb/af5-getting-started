package io.axoniq.demo.university.faculty.write.createcourse;

import java.util.UUID;

public record CreateCourseCommand(
        UUID courseId,
        String name,
        int capacity
) {

}
