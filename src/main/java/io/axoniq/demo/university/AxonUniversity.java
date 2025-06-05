package io.axoniq.demo.university;

import io.axoniq.demo.university.faculty.write.createcourse.CreateCourseCommand;
import io.axoniq.demo.university.faculty.write.createcourse.CreateCourseConfiguration;
import io.axoniq.demo.university.faculty.write.subscribestudent.SubscribeStudentToCourseCommand;
import io.axoniq.demo.university.faculty.write.subscribestudent.SubscribeStudentToCourseConfiguration;
import org.axonframework.axonserver.connector.AxonServerConnectionManager;
import org.axonframework.axonserver.connector.event.AxonServerEventStorageEngine;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.infra.ComponentDescriptor;
import org.axonframework.common.infra.FilesystemStyleComponentDescriptor;
import org.axonframework.configuration.AxonConfiguration;
import org.axonframework.eventsourcing.configuration.EventSourcingConfigurer;

import java.lang.invoke.MethodHandles;
import java.util.UUID;
import java.util.logging.Logger;

public class AxonUniversity {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static void main(String[] args) {
        AxonConfiguration axonConfig = mainConfigurer().build();
        describe(axonConfig);
        testRun(axonConfig);
    }

    public static EventSourcingConfigurer mainConfigurer() {
        return EventSourcingConfigurer.create()
                                      .registerEventStorageEngine(c -> new AxonServerEventStorageEngine(
                                              c.getComponent(AxonServerConnectionManager.class)
                                               .getConnection("university"),
                                              new TestConverter()
                                      ))
                                      .registerStatefulCommandHandlingModule(
                                              CreateCourseConfiguration.createCourseCommandModule()
                                      )
                                      .registerStatefulCommandHandlingModule(
                                              SubscribeStudentToCourseConfiguration.statefulCommandHandlingModule()
                                      );
    }

    private static void describe(AxonConfiguration axonConfig) {
        ComponentDescriptor componentDescriptor = new FilesystemStyleComponentDescriptor();
        axonConfig.describeTo(componentDescriptor);
        logger.info(componentDescriptor.describe());
    }

    private static void testRun(AxonConfiguration axonConfig) {
        axonConfig.start();
        CommandGateway commandGateway = axonConfig.getComponent(CommandGateway.class);

        UUID courseId = UUID.randomUUID();
        successfulCreateCourseRun(commandGateway, courseId);
        faultyCreateCourseRun(commandGateway, courseId);
        subscribeStudentToCourseRun(commandGateway, courseId);
    }

    private static void successfulCreateCourseRun(CommandGateway commandGateway, UUID courseId) {
        logger.info("Starting successful create course flow...");
        UUID resultingCourseId =
                commandGateway.send(new CreateCourseCommand(courseId, "AF5 Getting Started", 10), null, UUID.class)
                              .join();
        if (resultingCourseId.equals(courseId)) {
            logger.info("Same course id returned!");
        } else {
            logger.warning("Something went wrong in the create course flow...");
        }
    }

    private static void faultyCreateCourseRun(CommandGateway commandGateway, UUID courseId) {
        logger.info("Starting create course duplication flow...");
        try {
            commandGateway.send(new CreateCourseCommand(courseId, "AF5 Getting Started", 10), null, UUID.class)
                          .join();
            logger.warning("Something went wrong in the create course duplication flow...");
        } catch (Exception e) {
            logger.info("Could not create another course with the same identifier, as expected!");
        }
    }

    private static void subscribeStudentToCourseRun(CommandGateway commandGateway, UUID courseId) {
        logger.info("Starting subscribe student to course flow...");
        UUID studentId = UUID.randomUUID();
        commandGateway.send(new SubscribeStudentToCourseCommand(studentId, courseId), null, Void.class)
                      .join();
        logger.info("Check the event store!");
    }
}
