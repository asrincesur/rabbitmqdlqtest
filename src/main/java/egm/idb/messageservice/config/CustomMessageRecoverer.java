package egm.idb.messageservice.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.beans.factory.annotation.Value;

public class CustomMessageRecoverer implements MessageRecoverer {


    @Value("${rabbit.DLQ_EXCHANGE_NAME}")
    String DLQ_EXCHANGE_NAME;
    @Value("${rabbit.DLQ_ROUTING_KEY}")
    String DLQ_ROUTING_KEY;


    private final RabbitTemplate rabbitTemplate;

    public CustomMessageRecoverer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void recover(Message originalMessage, Throwable cause) {
        String errorMessage = String.valueOf(cause.getCause());

        MessageProperties props = new MessageProperties();
        props.setContentType("application/json");
        props.getHeaders().putAll(originalMessage.getMessageProperties().getHeaders());
        props.getHeaders().put("x-error-reason", errorMessage);
        Message newMessage = new Message(originalMessage.getBody(), props);
        rabbitTemplate.send(DLQ_EXCHANGE_NAME, DLQ_ROUTING_KEY, newMessage);
    }

    private Throwable extractRootCause(Throwable throwable) {
        Throwable result = throwable;
        while (result.getCause() != null) {
            result = result.getCause() ;
        }
        return result;
    }
}
