package io.axoniq.demo.university.faculty.write.createcourse;

import io.axoniq.demo.university.faculty.events.CourseCreatedEvent;
import org.axonframework.commandhandling.GenericCommandResultMessage;
import org.axonframework.configuration.Configuration;
import org.axonframework.eventhandling.gateway.EventAppender;
import org.axonframework.eventsourcing.configuration.EventSourcedEntityBuilder;
import org.axonframework.eventsourcing.eventstore.EventCriteria;
import org.axonframework.eventsourcing.eventstore.Tag;
import org.axonframework.messaging.MessageStream;
import org.axonframework.messaging.MessageType;
import org.axonframework.messaging.QualifiedName;
import org.axonframework.modelling.command.StatefulCommandHandler;
import org.axonframework.modelling.configuration.StatefulCommandHandlingModule;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CreateCourseConfiguration {

    public static StatefulCommandHandlingModule createCourseCommandModule() {
        CreateCourseCommandHandler commandHandler = new CreateCourseCommandHandler();
        return StatefulCommandHandlingModule.named("create-course")
                                            .commandHandlers()
                                            .commandHandler(
                                                    new QualifiedName(CreateCourseCommand.class),
                                                    config -> statefulCommandHandler(config, commandHandler)
                                            )
                                            .entities()
                                            .entity(createCourseEntity())
                                            .build();
    }

    private static StatefulCommandHandler statefulCommandHandler(
            Configuration config,
            CreateCourseCommandHandler commandHandler
    ) {
        return (command, state, context) -> {
            CreateCourseCommand commandPayload = (CreateCourseCommand) command.getPayload();
            EventAppender appender = EventAppender.forContext(context, config);

            CreateCourseState createCourseState;
            try {
                createCourseState = state.loadEntity(CreateCourseState.class,
                                                     commandPayload.courseId(),
                                                     context)
                                         .get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                return MessageStream.failed(e);
            }

            try {
                UUID courseId = commandHandler.handle(commandPayload, appender, createCourseState);
                return MessageStream.just(new GenericCommandResultMessage<>(new MessageType("id"), courseId));
            } catch (Exception e) {
                return MessageStream.failed(e);
            }
        };
    }

    private static EventSourcedEntityBuilder<UUID, CreateCourseState> createCourseEntity() {
        return EventSourcedEntityBuilder.entity(UUID.class, CreateCourseState.class)
                                        .entityFactory(c -> (entityType, id) -> new CreateCourseState())
                                        .criteriaResolver(c -> id -> EventCriteria.havingTags(
                                                new Tag("courseId", id.toString())
                                        ))
                                        .entityEvolver(c -> (entity, event, context) -> {
                                            if (event.getPayload() instanceof CourseCreatedEvent courseCreated) {
                                                entity.setId(courseCreated.courseId());
                                            }
                                            return entity;
                                        });
    }
}
