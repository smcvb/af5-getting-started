package io.axoniq.demo.university.faculty.write.subscribestudent;

import org.axonframework.eventsourcing.configuration.EventSourcedEntityBuilder;
import org.axonframework.modelling.configuration.StatefulCommandHandlingModule;

public class SubscribeStudentToCourseConfiguration {

    public static StatefulCommandHandlingModule statefulCommandHandlingModule() {
        return StatefulCommandHandlingModule.named("subscribe-student")
                                            .commandHandlers()
                                            .annotatedCommandHandlingComponent(
                                                    c -> new SubscribeStudentToCourseCommandHandler()
                                            )
                                            .entities()
                                            .entity(EventSourcedEntityBuilder.annotatedEntity(
                                                    SubscriptionId.class, SubscribeStudentState.class
                                            ))
                                            .build();
    }
}
