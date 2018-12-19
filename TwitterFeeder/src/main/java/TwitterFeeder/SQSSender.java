package TwitterFeeder;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import java.util.Date;

public class SQSSender {
    public static void main(String[] args) {
        AmazonSQS client = AmazonSQSAsyncClientBuilder.defaultClient();

        client.sendMessage("https://sqs.us-east-1.amazonaws.com/135062767808/Yonathan", "Vani - " + new Date().toString());
    }
}
