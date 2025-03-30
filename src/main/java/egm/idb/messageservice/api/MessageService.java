package egm.idb.messageservice.api;


import egm.idb.messageservice.business.producer.MessagePublisher;
import egm.idb.messageservice.dtos.MessageRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageService {

    private final MessagePublisher publisher;

    public MessageService(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("send-message-q")
    public void sentMessageQueuded(@RequestBody MessageRequestDto req) {
        publisher.publisher(req);
    }

}
