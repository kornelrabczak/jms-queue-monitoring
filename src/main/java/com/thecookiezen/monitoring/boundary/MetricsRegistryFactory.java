package com.thecookiezen.monitoring.boundary;

import com.codahale.metrics.MetricRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.lang.management.ManagementFactory;

public class MetricsRegistryFactory {

    @Produces
    @ApplicationScoped
    public MetricRegistry createMetricRegistry() {
        final MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.registerAll(new JmsMetricsSet(ManagementFactory.getPlatformMBeanServer()));
        return metricRegistry;
    }
}
