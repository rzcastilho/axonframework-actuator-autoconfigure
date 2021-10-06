package com.github.rzcastilho.axonframework.actuate.autoconfigure;

import com.github.rzcastilho.axonframework.actuate.AxonServerHealthIndicator;
import io.axoniq.axonserver.connector.AxonServerConnection;
import java.util.Map;
import org.axonframework.springboot.autoconfig.AxonServerAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-Configuration} for {@link AxonServerHealthIndicator}
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(AxonServerConnection.class)
@ConditionalOnBean(AxonServerConnection.class)
@ConditionalOnEnabledHealthIndicator("axonserver")
@AutoConfigureAfter({AxonServerAutoConfiguration.class})
public class AxonServerHealthContributorAutoConfiguration extends CompositeHealthContributorConfiguration<AxonServerHealthIndicator, AxonServerConnection> {

    @Bean
    @ConditionalOnMissingBean(name = {"axonServerHealthIndicator", "axonServerHealthContributor"})
    public HealthContributor axonServerHealthContributor(Map<String, AxonServerConnection> axonServerConnections) {
        return createContributor(axonServerConnections);
    }

}
