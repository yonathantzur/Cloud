package TwitterFeeder;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

public class SQSReader {
    public static void main(String[] args) {
        AmazonSQS client = AmazonSQSAsyncClientBuilder.defaultClient();

        String queueUrl = "https://sqs.us-east-1.amazonaws.com/135062767808/Yonathan";

        ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl);
        request.setWaitTimeSeconds(5);
        request.setVisibilityTimeout(1);

        while(true) {
            ReceiveMessageResult result = client.receiveMessage(request);

            for (Message message : result.getMessages()) {
                System.out.println(message.getBody());

                // Process message ...
                // Process message ...
                // Process message ...

                client.deleteMessage(queueUrl, message.getReceiptHandle());
            }
        }
    }
}
