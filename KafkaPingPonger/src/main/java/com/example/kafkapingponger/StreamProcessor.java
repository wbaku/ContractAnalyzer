package com.example.kafkapingponger;


import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Function;

@Configuration
public class StreamProcessor {


    @Bean
    public Function<KStream<String, String>, KStream<String, String>[]> process() {


        //czy da się to zrobić wszystko w tej metodzie???
        Predicate<String, String> isCommand = (k, v) -> k.endsWith("compute");
        Predicate<String, String> isNotCommand = (k, v) -> !k.endsWith("compute");


        System.out.println("im here motherfucker");

        return input -> input
                .peek(((key, value) -> System.out.println("key value before grouping" +
                        key + " " + value)))
                .filter((k, v) -> Objects.nonNull(k) && k.contains("test"))
                .groupBy((k, v) -> k + v)
                .windowedBy(TimeWindows.of(Duration.ofMinutes(2)))
                .count(Materialized.as("count-store"))
                .toStream()
                .peek((k, v) -> System.out.println("from input to count topic: key " + k + " value " + v))
                .map((k, v) -> new KeyValue<>(k.key(), String.valueOf(v)))

                .branch(isCommand, isNotCommand);

    }
    //czy tym rzeczom można zmieniać sygnatury
    //Można zmienić na consumer


}
