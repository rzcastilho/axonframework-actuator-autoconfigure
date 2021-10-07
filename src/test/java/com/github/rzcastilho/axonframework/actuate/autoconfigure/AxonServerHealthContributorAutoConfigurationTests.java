package com.github.rzcastilho.axonframework.actuate.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.rzcastilho.axonframework.actuate.AxonServerHealthIndicator;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration;
import org.axonframework.springboot.autoconfig.EventProcessingAutoConfiguration;
import org.axonframework.springboot.autoconfig.InfraConfiguration;
import org.axonframework.springboot.autoconfig.JdbcAutoConfiguration;
import org.axonframework.springboot.autoconfig.JpaEventStoreAutoConfiguration;
import org.axonframework.springboot.autoconfig.NoOpTransactionAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

public class AxonServerHealthContributorAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(
            AutoConfigurations.of(
                EventProcessingAutoConfiguration.class,
                AxonAutoConfiguration.class,
                JpaEventStoreAutoConfiguration.class,
                JdbcAutoConfiguration.class,
                NoOpTransactionAutoConfiguration.class,
                InfraConfiguration.class,
                AxonServerAutoConfiguration.class,
                AxonServerHealthContributorAutoConfiguration.class,
                HealthContributorAutoConfiguration.class
            )
        );

    @Test
    void runShouldCreateIndicator() {
        this.contextRunner.run((context -> assertThat(context).hasSingleBean(AxonServerHealthIndicator.class)));
    }

    @Test
    void runWhenDisabledShouldNotCreateIndicator() {
        this.contextRunner.withPropertyValues("management.health.axonserver.enabled:false")
            .run((context) -> assertThat(context).doesNotHaveBean(AxonServerHealthIndicator.class));
    }

}
