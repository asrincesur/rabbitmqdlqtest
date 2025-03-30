package egm.idb.messageservice.business.producer;

import egm.idb.messageservice.dtos.MessageRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbit.QUEUE_NAME}")
    String QUEUE_NAME;
    @Value("${rabbit.EXCHANGE_NAME}")
    String EXCHANGE_NAME;
    @Value("${rabbit.ROUTING_KEY}")
    String ROUTING_KEY;


    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final Logger log = LoggerFactory.getLogger(MessagePublisher.class);

    public void publisher(MessageRequestDto req) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, req);
        System.out.println("🟢 Message sent to queue: ");
    }

}
