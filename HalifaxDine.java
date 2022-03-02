import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import java.util.Random;
import java.util.ArrayList;

public class HalifaxDine {
    public static void main(String[] args) {
        String access_key_id = "ASIAUVORRQZ55L3CQJWH";
        String secret_key_id = "kKgmFI0qJLN8zpBzUWLuoDcQQ/mHThRQETrCFz7a";
        String session_token = "FwoGZXIvYXdzEPv//////////wEaDJZc0zEIS2QXWtYMsSK/AQ3g+Zx7wq+vQdSc6f6kBXqkPCSaSy5O8ltq/1Sip20lBI/zCb7lZ6hEDdVaYrp6tI8m09yMmNcN7T9jodyYAECdnnQfwNILHGrQEv3CHNV/f1tFwu/WX6zkwGY1MUHl9LMRkpf9L/2XA29+GWnrebaexmziTTQEJepOk7vvhiOY9GghFF5toJ0nvlbGGPFDUu3NQbYk+ApO8w9PbAIS8w2uASxOELCohtNW6i6KkUhVgcrVe7qHQ6KBI4zwiV1YKIiti40GMi1xhafOf8byM5D9rA8TYjXfpp62zuNH87wZ6zh8cGbNc7sUehXci04tzizdNIs=";
        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
                access_key_id, secret_key_id, session_token);
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
                .build();
        createQueue(sqs);
    }

    private static void createQueue( AmazonSQS sqs) {
//        https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-sqs-message-queues.html
        String QUEUE_NAME = "b00881511";
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
        ArrayList<String> quantity = new ArrayList<>();
        quantity.add("Small");
        quantity.add("Medium");
        quantity.add("Large");
        ArrayList<String> food_items = new ArrayList<>();
        food_items.add("Pizza");
        food_items.add("Salad");
        food_items.add("Rice");
        Random rand = new Random();
        for(int i =0;i<2;i++) {
            int index = rand.nextInt(3);
            String item = food_items.get(index);
            String size = quantity.get(index);
            String body = item + " : " + size;
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(body)
                    .withDelaySeconds(300);
            sqs.sendMessage(send_msg_request);
        }
    }
}
