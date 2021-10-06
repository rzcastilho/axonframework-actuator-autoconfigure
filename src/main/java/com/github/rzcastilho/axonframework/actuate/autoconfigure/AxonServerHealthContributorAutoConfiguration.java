package com.github.rzcastilho.axonframework.actuate.autoconfigure;

import com.github.rzcastilho.axonframework.actuate.AxonServerHealthIndicator;
import java.util.Map;
import org.axonframework.axonserver.connector.AxonServerConnectionManager;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
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
@ConditionalOnClass(AxonServerConnectionManager.class)
@ConditionalOnBean(AxonServerConnectionManager.class)
@ConditionalOnEnabledHealthIndicator("axonserver")
@AutoConfigureAfter({AxonAutoConfiguration.class, AxonServerAutoConfiguration.class})
public class AxonServerHealthContributorAutoConfiguration extends CompositeHealthContributorConfiguration<AxonServerHealthIndicator, AxonServerConnectionManager> {

    @Bean
    @ConditionalOnMissingBean(name = {"axonServerHealthIndicator", "axonServerHealthContributor"})
    public HealthContributor axonServerHealthContributor(Map<String, AxonServerConnectionManager> axonServerConnectionManagers) {
        return createContributor(axonServerConnectionManagers);
    }

}
