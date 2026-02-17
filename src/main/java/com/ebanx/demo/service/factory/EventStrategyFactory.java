package com.ebanx.demo.service.factory;

import com.ebanx.demo.service.strategy.interfaces.EventStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventStrategyFactory {

    private final Map<String, EventStrategy> strategies;

    public EventStrategyFactory(List<EventStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        EventStrategy::getName,
                        strategy -> strategy
                ));
    }

    public EventStrategy getStrategy(String eventType) {
        EventStrategy strategy = strategies.get(eventType);

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid event type");
        }

        return strategy;
    }

}
