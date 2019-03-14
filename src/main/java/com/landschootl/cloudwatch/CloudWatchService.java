package com.landschootl.cloudwatch;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class CloudWatchService {

    @Value("${cloudwatch.region}")
    private String amazonAWSRegion;

    @Value("${cloudwatch.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${cloudwatch.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${cloudwatch.namespace}")
    private String amazonAWSNamespace;

    private AmazonCloudWatch client;

    @PostConstruct
    public void init() {
        client();
    }

    private AmazonCloudWatch client() {
        if (client == null) {
            log.info("Creating CloucWatch client.");
            AWSCredentials credentials = new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
            AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
            client = AmazonCloudWatchClientBuilder.standard()
                    .withCredentials(credentialsProvider)
                    .withRegion(Regions.fromName(amazonAWSRegion))
                    .build();
        }
        return client;
    }

    public PutMetricDataResult putMetricData(MetricName metricName, double data){
        MetricDatum datum = new MetricDatum()
                .withMetricName(metricName.getValue())
                .withUnit(StandardUnit.None)
                .withValue(data);
        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace(amazonAWSNamespace)
                .withMetricData(datum);

        PutMetricDataResult response = client.putMetricData(request);
        System.out.printf("Successfully put data point %f", data);
        return response;
    }
}
