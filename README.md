# jms-queue-monitoring

Exposing WildFly's JMS queue statistics through REST/JSON for monitoring.

## blog post 

http://thecookiezen.com/blog/2015/10/20/exposing-wildfly-jms-queue-statistics-through-rest-slash-json-for-monitoring/

## about 

I want to show you how easily you can monitor your JMS queues on WildFly 8 application server. JMS provides some statistics data like total added messages, messages pending in queue or number of consumers connected to queue. We can gather and analyze those data in our monitoring system.

We could also create triggers for the purpose of alerting us when there are no consumers for the queue. This could easily end up with queue swelling thousands of messages. We will use Dropwizard Metrics library which is very nice and easy for gathering and measuring data in our application. We will expose this data through REST as JSON. We won’t rely on JMX protocol because protocol used for providing data for monitoring should be technology agnostic. While providing data for monitoring system, we should use standard protocol for every technology, in our case, it will be HTTP.
