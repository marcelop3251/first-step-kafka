package br.com.bookstore.service


import br.com.bookstore.ProcessOrder
import br.com.bookstore.config.CustomizableDeserializer
import br.com.bookstore.dto.Order
import io.azam.ulidj.ULID
import java.io.Closeable
import java.time.Duration
import java.util.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

class Consumer<T>(
    private val topic: String,
    private val parse: ConsumerFunction<T>,
    private val groupId: String
) : Closeable {
    private val kafkaConsumer =  KafkaConsumer<String, T>(properties(groupId))
    init {
        kafkaConsumer.subscribe(Collections.singletonList(topic))
    }

    private fun properties(groupId: String): Properties {
        val properties = Properties()
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomizableDeserializer::class.java.name)
        // garante que haja uma distribuição das mensagens e também que todas as mensagens serão enviadas para esse group
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, ULID.random())
        properties.setProperty(CustomizableDeserializer.TYPE_CONFIG, Order::class.java.name)
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
        // earliest inicia a partir da última mensagem não lida
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
        return properties
    }

    fun run() {
        while (true) {

            val records = kafkaConsumer.poll(Duration.ofMillis(100))

            if (!records.isEmpty) {
                println("Encontrei registros ${records.count()} registros")
                for (record in records) {
                    parse.consumer(record)
                    kafkaConsumer.commitSync()
                }

            }
        }
    }

    override fun close() {
        kafkaConsumer.close()
    }
}