package io.axoniq.demo.university.faculty.write.createcourse;

import java.util.UUID;

class CreateCourseState {

    private UUID id;

    public UUID identifier() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
