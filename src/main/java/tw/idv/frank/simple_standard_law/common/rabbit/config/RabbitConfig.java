package tw.idv.frank.simple_standard_law.common.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static tw.idv.frank.simple_standard_law.common.rabbit.constant.RabbitConstants.*;

@Configuration
public class RabbitConfig {

    @Bean(name=BEAN_REPORT_QUEUE)
    public Queue reportQueue() {
        return new Queue(NAME_REPORT_QUEUE);
    }

    @Bean(name=BEAN_MAIL_QUEUE)
    public Queue mailQueue() {
        return new Queue(NAME_MAIL_QUEUE);
    }

    @Bean(name=BEAN_GENERATE_REPORT_EXCHANGE)
    public TopicExchange generateReportExchange() {
        return new TopicExchange(NAME_GENERATE_REPORT_EXCHANGE);
    }

    @Bean
    public Binding bindReportQueueToGenerateReportExchange(
            @Qualifier(BEAN_REPORT_QUEUE) Queue queue,
            @Qualifier(BEAN_GENERATE_REPORT_EXCHANGE) TopicExchange exchange
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("report.*");
    }

    @Bean
    public Binding bindMailQueueToGenerateReportExchange(
            @Qualifier(BEAN_MAIL_QUEUE) Queue queue,
            @Qualifier(BEAN_GENERATE_REPORT_EXCHANGE) TopicExchange exchange
    ) {
        return BindingBuilder.bind(queue).to(exchange).with("mail.*");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();

        // 設定信任的包名
        classMapper.setTrustedPackages("tw.idv.frank.simple_standard_law.common.rabbit.dto");
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }
//
//
//
//    @Bean
//    public Queue testQueue() {
//        return new Queue("Test_Queue");
//    }
//
//    @Bean(name = BEAN_NOTIFY_QUEUE)
//    public Queue notifyQueue() {
//        return new Queue(NAME_NOTIFY_QUEUE);
//    }
//
//    @Bean(name = BEAN_EMAIL_QUEUE)
//    public Queue emailQueue() {
//        return new Queue(NAME_EMAIL_QUEUE);
//    }
//
//    /**
//     * Publish/Subscribe模式
//     */
//    @Bean(name = BEAN_FANOUT_EXCHANGE)
//    public FanoutExchange FanoutExchange() {
//        return new FanoutExchange(NAME_FANOUT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding bindNotifyQueueToFanoutExchange(
//            @Qualifier(BEAN_NOTIFY_QUEUE) Queue queue,
//            @Qualifier(BEAN_FANOUT_EXCHANGE) FanoutExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange);
//    }
//
//    @Bean
//    public Binding bindEmailQueueToFanoutExchange(
//            @Qualifier(BEAN_EMAIL_QUEUE) Queue queue,
//            @Qualifier(BEAN_FANOUT_EXCHANGE) FanoutExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange);
//    }
//    /**
//     * =============================================================
//     */
//
//    /**
//     * Routing模式
//     */
//    @Bean(name = BEAN_ROUTING_EXCHANGE)
//    public DirectExchange routingExchange() {
//        return new DirectExchange(NAME_ROUTING_EXCHANGE);
//    }
//
//    @Bean
//    public Binding bindNotifyQueueToRoutingExchangeForPostComment(
//            @Qualifier(BEAN_NOTIFY_QUEUE) Queue queue,
//            @Qualifier(BEAN_ROUTING_EXCHANGE) DirectExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange).with("routing.notify");
//    }
//
//    @Bean
//    public Binding bindNotifyQueueToRoutingExchangeForProductComment(
//            @Qualifier(BEAN_NOTIFY_QUEUE) Queue queue,
//            @Qualifier(BEAN_ROUTING_EXCHANGE) DirectExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange).with("routing.email");
//    }
//
//    @Bean
//    public Binding bindEmailQueueToRoutingExchangeForProductComment(
//            @Qualifier(BEAN_EMAIL_QUEUE) Queue queue,
//            @Qualifier(BEAN_ROUTING_EXCHANGE) DirectExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange).with("routing.email");
//    }
//    /**
//     * =============================================================
//     */
//
//    /**
//     * Topic模式
//     */
//    @Bean(name = BEAN_TOPIC_EXCHANGE)
//    public TopicExchange topicExchange() {
//        return new TopicExchange(NAME_TOPIC_EXCHANGE);
//    }
//
//    @Bean
//    public Binding bindNotifyQueueToTopicExchangeForPostComment(
//            @Qualifier(BEAN_NOTIFY_QUEUE) Queue queue,
//            @Qualifier(BEAN_TOPIC_EXCHANGE) TopicExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange).with("topic.notify.#");
//    }
//
//    @Bean
//    public Binding bindNotifyQueueToTopicExchangeForProductComment(
//            @Qualifier(BEAN_NOTIFY_QUEUE) Queue queue,
//            @Qualifier(BEAN_TOPIC_EXCHANGE) TopicExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange).with("topic.email.*");
//    }
//
//    @Bean
//    public Binding bindEmailQueueToTopicExchangeForProductComment(
//            @Qualifier(BEAN_EMAIL_QUEUE) Queue queue,
//            @Qualifier(BEAN_TOPIC_EXCHANGE) TopicExchange exchange
//    ) {
//        return BindingBuilder.bind(queue).to(exchange).with("topic.email.*.#");
//    }
//    /**
//     * =============================================================
//     */
}
