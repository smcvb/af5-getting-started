package io.axoniq.demo.university;

import org.axonframework.common.infra.ComponentDescriptor;
import org.axonframework.common.infra.FilesystemStyleComponentDescriptor;
import org.axonframework.configuration.AxonConfiguration;
import org.axonframework.eventsourcing.configuration.EventSourcingConfigurer;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

public class AxonUniversity {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static void main(String[] args) {
        AxonConfiguration axonConfig = mainConfigurer().build();
        describe(axonConfig);
    }

    public static EventSourcingConfigurer mainConfigurer() {
        return EventSourcingConfigurer.create();
    }

    private static void describe(AxonConfiguration axonConfig) {
        ComponentDescriptor componentDescriptor = new FilesystemStyleComponentDescriptor();
        axonConfig.describeTo(componentDescriptor);
        logger.info(componentDescriptor.toString());
    }
}
