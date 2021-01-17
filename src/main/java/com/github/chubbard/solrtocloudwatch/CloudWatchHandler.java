package com.github.chubbard.solrtocloudwatch;


import com.amazonaws.metrics.AwsSdkMetrics;
import org.apache.solr.handler.admin.CoreAdminHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudWatchHandler extends CoreAdminHandler {

    private static final Logger logger = LoggerFactory.getLogger( CloudWatchHandler.class );

    public CloudWatchHandler() {
        super();
        initializeCloudwatch();
    }

    private void initializeCloudwatch() {
        logger.info("CloudWatch initializing...");
        String hostName = System.getProperty("aws.hostMetricName");
        boolean metricsNamespace = Boolean.parseBoolean(System.getProperty("aws.singleMetricNamespace", "true"));
        boolean includeHost = Boolean.parseBoolean(System.getProperty("aws.perHostMetricsIncluded", "true"));

        if (hostName != null) {
            AwsSdkMetrics.setHostMetricName(hostName);
        }
        AwsSdkMetrics.setPerHostMetricsIncluded( includeHost );
        AwsSdkMetrics.setSingleMetricNamespace( metricsNamespace );
        boolean success = AwsSdkMetrics.enableDefaultMetrics();
        if( success ) {
            logger.info("CloudWatch successfully initialized");
        }
    }
}
