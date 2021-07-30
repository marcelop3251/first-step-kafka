package br.com.bookstore

import br.com.bookstore.service.Consumer
import java.lang.RuntimeException
import org.apache.kafka.clients.consumer.ConsumerRecord

class FraudService {

    fun parse(record: ConsumerRecord<String, Any>) {
        println("Processing new order, checking fraud")
        println(record.key())
        println(record.value())
        println(record.partition())
        println(record.offset())
        println("Fraud available with success")
    }
}


fun main() {
    val service = FraudService()
    Consumer("BOOK_NEW_ORDER", service::parse, FraudService::class.java.simpleName).use {
        it.run()
    }

}

