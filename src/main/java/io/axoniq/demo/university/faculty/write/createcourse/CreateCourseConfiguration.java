package io.axoniq.demo.university.faculty.write.createcourse;

import org.axonframework.configuration.Configuration;
import org.axonframework.eventhandling.gateway.EventAppender;
import org.axonframework.messaging.MessageStream;
import org.axonframework.messaging.QualifiedName;
import org.axonframework.modelling.command.StatefulCommandHandler;
import org.axonframework.modelling.configuration.StatefulCommandHandlingModule;

public class CreateCourseConfiguration {

    public static StatefulCommandHandlingModule createCourseCommandModule() {
        CreateCourseCommandHandler commandHandler = new CreateCourseCommandHandler();
        return StatefulCommandHandlingModule.named("create-course")
                                            .commandHandlers()
                                            .commandHandler(
                                                    new QualifiedName(CreateCourseCommand.class),
                                                    config -> statefulCommandHandler(config, commandHandler)
                                            )
                                            .build();
    }

    private static StatefulCommandHandler statefulCommandHandler(
            Configuration config,
            CreateCourseCommandHandler commandHandler
    ) {
        return (command, state, context) -> {
            EventAppender appender = EventAppender.forContext(context, config);

            commandHandler.handle((CreateCourseCommand) command.getPayload(), appender);

            return MessageStream.just(null);
        };
    }
}
