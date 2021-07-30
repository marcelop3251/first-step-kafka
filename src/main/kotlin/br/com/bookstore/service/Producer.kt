package br.com.bookstore.service

import br.com.bookstore.config.CustomizableSerializer
import java.io.Closeable
import java.lang.Exception
import java.util.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata


class Producer<T> : Closeable {

    private val kafkaProducer = KafkaProducer<String, T>(properties())

    fun send(topic: String, key: String, value: T) {
        val record =  ProducerRecord(topic, key, value)
        val callback = { data: RecordMetadata, ex: Exception? ->
            ex?.printStackTrace()
            println("Sucesso: enviando ${data.topic()} partition ${data.partition()}")
        }
        kafkaProducer.send(record, callback).get()
    }

    private fun properties(): Properties {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, CustomizableSerializer::class.java.name)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomizableSerializer::class.java.name)
        return properties
    }

    override fun close() {
        kafkaProducer.close()
    }
}