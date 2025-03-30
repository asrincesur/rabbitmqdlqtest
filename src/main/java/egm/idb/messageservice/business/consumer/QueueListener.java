package egm.idb.messageservice.business.consumer;


import egm.idb.messageservice.dtos.MessageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class QueueListener {


    @RabbitListener(queues = "my.queue")
    public void receiveMessage(Message q, MessageRequestDto message) {
        log.info("Received message from queue: {}", new Date());
        log.error("an error occurred");
        throw new ArithmeticException("dlq test randomly");
    }

}
