package il.ac.colman.cs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import il.ac.colman.cs.util.DataStorage;
import il.ac.colman.cs.util.LinkExtractor;
import twitter4j.JSONObject;

public class LinkListener {
    public static void main(String[] args) throws ClassNotFoundException {
        // Connect to the database
        DataStorage dataStorage = new DataStorage();

        // Initiate our link extractor
        LinkExtractor linkExtractor = new LinkExtractor();

        // Connect to the SQS queue service.
        AmazonSQS client = AmazonSQSAsyncClientBuilder.defaultClient();

        String queueUrl = System.getProperty("queue_url");
        int queueWaitTime = Integer.parseInt(System.getProperty("tweets_read_delay")) / 1000;
        int visibilityTimeout = 1;

        // Set the queue dns.
        ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl);

        // Set wait time for dequeue.
        request.setWaitTimeSeconds(queueWaitTime);
        request.setVisibilityTimeout(visibilityTimeout);

        ExtractedLink linkObj;
        ReceiveMessageResult result;
        JSONObject dataJson;

        while (true) {
            try {
                // Get the messages from the queue
                result = client.receiveMessage(request);

                for (Message message : result.getMessages()) {
                    // Convert the message back to json object.
                    dataJson = new JSONObject(message.getBody());

                    // Extract link obj from the URL.
                    linkObj = linkExtractor.extractContent(dataJson.get("link").toString());

                    // Add link data to DB (RDS).
                    dataStorage.addLink(linkObj, dataJson.get("track").toString());

                    // Delete the message from the queue.
                    client.deleteMessage(queueUrl, message.getReceiptHandle());
                }
            }
            // Reconnect to queue in case of error.
            catch (Exception e) {
                System.out.println("LinkListener - " + e.getMessage());
                client = AmazonSQSAsyncClientBuilder.defaultClient();
                request = new ReceiveMessageRequest(queueUrl);
                request.setWaitTimeSeconds(queueWaitTime);
                request.setVisibilityTimeout(visibilityTimeout);
            }
        }
    }

}
