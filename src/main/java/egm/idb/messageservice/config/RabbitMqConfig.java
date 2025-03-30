package egm.idb.messageservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;


@Configuration
public class RabbitMqConfig {

    @Value("${rabbit.QUEUE_NAME}")
    String QUEUE_NAME;
    @Value("${rabbit.EXCHANGE_NAME}")
    String EXCHANGE_NAME;
    @Value("${rabbit.ROUTING_KEY}")
    String ROUTING_KEY;

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /*-------------------------- DEAD LETTER Q BEANS --------------------------*/


    @Value("${rabbit.DLQ_QUEUE_NAME}")
    String DLQ_QUEUE_NAME;
    @Value("${rabbit.DLQ_EXCHANGE_NAME}")
    String DLQ_EXCHANGE_NAME;
    @Value("${rabbit.DLQ_ROUTING_KEY}")
    String DLQ_ROUTING_KEY;

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ_QUEUE_NAME).build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLQ_EXCHANGE_NAME);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLQ_ROUTING_KEY);
    }


    ///  DEAD LETTER Q CUSTOMIZATION  BEANS FOR HEADER ERROR LOG AND RETRY MECHANISM /////

    @Bean
    public RetryOperationsInterceptor rabbitRetryInterceptor(CustomMessageRecoverer recoverer) {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(5) // try for 5 times
                .backOffOptions(2000L, 2.0, 10000L) // wait 2*2 sec  plus every each try
                .recoverer(recoverer)
                .build();
    }

    @Bean
    public CustomMessageRecoverer customMessageRecoverer(RabbitTemplate rabbitTemplate) {
        return new CustomMessageRecoverer(rabbitTemplate);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter,
            RetryOperationsInterceptor rabbitRetryInterceptor) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAdviceChain(rabbitRetryInterceptor);
        return factory;
    }


}
