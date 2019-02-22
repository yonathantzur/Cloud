package il.ac.colman.cs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import il.ac.colman.cs.util.DataStorage;
import il.ac.colman.cs.util.LinkExtractor;
import twitter4j.JSONException;
import twitter4j.JSONObject;

import java.sql.SQLException;

public class LinkListener {
    public static void main(String[] args) throws SQLException, JSONException, ClassNotFoundException {
        // Connect to the database
        DataStorage dataStorage = new DataStorage();

        // Initiate our link extractor
        LinkExtractor linkExtractor = new LinkExtractor();

        AmazonSQS client = AmazonSQSAsyncClientBuilder.defaultClient();

        String queueUrl = System.getProperty("queue_url");

        ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl);
        //request.setWaitTimeSeconds(5);
        //request.setVisibilityTimeout(1);

        ExtractedLink linkObj;
        ReceiveMessageResult result;
        JSONObject dataJson;

        while (true) {
            result = client.receiveMessage(request);

            for (Message message : result.getMessages()) {
                dataJson = new JSONObject(message.getBody());

                linkObj = linkExtractor.extractContent(dataJson.get("link").toString());

                dataStorage.addLink(linkObj, dataJson.get("track").toString());

                client.deleteMessage(queueUrl, message.getReceiptHandle());
            }
        }
    }

}
