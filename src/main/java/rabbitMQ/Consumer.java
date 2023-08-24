package rabbitMQ;

import com.rabbitmq.client.*;

public class Consumer {
    private final static String QUEUE_NAME = "message_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // RabbitMQ服务器地址
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for messages. To exit press Ctrl+C");

            while (true) {
                GetResponse response = channel.basicGet(QUEUE_NAME, true); // true to auto-acknowledge
                if (response != null) {
                    String message = new String(response.getBody(), "UTF-8");
                    System.out.println("Received: " + message);
                } else {
                    System.out.println("No more messages in the queue. Exiting.");
                    break;
                }
            }
        }
    }
}
