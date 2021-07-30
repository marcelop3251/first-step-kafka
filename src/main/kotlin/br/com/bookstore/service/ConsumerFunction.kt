package br.com.bookstore.service

import org.apache.kafka.clients.consumer.ConsumerRecord

fun interface ConsumerFunction<T> {
    fun consumer(record: ConsumerRecord<String, T>)
}