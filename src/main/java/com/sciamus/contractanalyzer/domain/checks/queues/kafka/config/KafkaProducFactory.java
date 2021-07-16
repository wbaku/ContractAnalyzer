package com.sciamus.contractanalyzer.domain.checks.queues.kafka.config;


import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import lombok.experimental.Accessors;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

public class KafkaProducFactory {

    private final KafkaProperties kafkaProperties;

    @Autowired
    public KafkaProducFactory(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    //2 more arguments (topic, partition) + method for getting position

    public KafkaTemplate<String,String> createProducer(String host, String port) {

        Map<String, Object> configProps = HashMap.empty();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                host+":"+port);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaProperties.getProduc().getKeySerializer());
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaProperties.getProduc().getValueSerializer());
//        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");


        ProducerFactory<String,String> factory = new DefaultKafkaProducerFactory<> (configProps.toJavaMap());
        final KafkaTemplate<String,String> template = new KafkaTemplate<>(factory);



        return template;


    }



}
