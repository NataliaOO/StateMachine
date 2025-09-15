package com.example.statemachine.spring;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class StateMachineAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public StateMachineService stateMachineService() {
        return new StateMachineService();
    }
}
