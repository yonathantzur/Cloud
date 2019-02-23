package il.ac.colman.cs.util;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

public class CloudWatch {
    final AmazonCloudWatch cw;
    Dimension dimension;

    public CloudWatch() {
        this.cw = AmazonCloudWatchClientBuilder.defaultClient();
        this.dimension = new Dimension().withName("yonof-dimention").withValue("yonof-dimention");

    }

    public PutMetricDataResult SendMetric(String metricName, Double metricData) {
        try {
            MetricDatum datum = new MetricDatum()
                    .withMetricName(metricName)
                    .withUnit(StandardUnit.None)
                    .withValue(metricData)
                    .withDimensions(dimension);

            PutMetricDataRequest request = new PutMetricDataRequest()
                    .withNamespace(metricName)
                    .withMetricData(datum);

            return cw.putMetricData(request);
        } catch (Exception e) {
            return null;
        }
    }

}
