package com.thecookiezen.monitoring.boundary;

import com.codahale.metrics.JmxAttributeGauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.codahale.metrics.MetricRegistry.name;
import static java.util.Collections.EMPTY_MAP;

public class JmsMetricsSet implements MetricSet {

    private static final String[] ATTRIBUTES = {"messageCount", "messagesAdded"};
    private static final String JBOSS_MESSAGING_RUNTIME_QUEUE_PATTERN = "jboss.as:subsystem=messaging,hornetq-server=default,runtime-queue=jms.queue.*";

    private final MBeanServer mBeanServer;

    public JmsMetricsSet(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        final Set<ObjectName> queues;
        try {
            queues = findQueues();
        } catch (MalformedObjectNameException e) {
            return EMPTY_MAP;
        }

        Map<String, Metric> gauges = new HashMap<>();
        for (ObjectName queue : queues) {
            String queueName = queue.getKeyProperty("runtime-queue");
            for (final String attribute : ATTRIBUTES) {
                gauges.put(name(queueName, attribute), new JmxAttributeGauge(mBeanServer, queue, attribute));
            }
        }
        return Collections.unmodifiableMap(gauges);
    }

    private Set<ObjectName> findQueues() throws MalformedObjectNameException {
        return mBeanServer.queryNames(new ObjectName(JBOSS_MESSAGING_RUNTIME_QUEUE_PATTERN), null);
    }
}