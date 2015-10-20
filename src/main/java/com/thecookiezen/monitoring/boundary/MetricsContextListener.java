package com.thecookiezen.monitoring.boundary;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

import javax.inject.Inject;

public class MetricsContextListener extends MetricsServlet.ContextListener {

    @Inject
    private MetricRegistry metricRegistry;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }
}