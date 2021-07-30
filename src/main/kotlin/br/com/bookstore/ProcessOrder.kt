package br.com.bookstore

import br.com.bookstore.service.Consumer
import java.lang.RuntimeException
import org.apache.kafka.clients.consumer.ConsumerRecord

class ProcessOrder {

    fun parse(record: ConsumerRecord<String, Any>) {
        println("Processing new order")
        println(record.key())
        println(record.value())
        println(record.partition())
        println(record.offset())
        println("order processed")
       // throw IllegalArgumentException("Error at process")
    }
}


fun main() {
    val service = ProcessOrder()
    Consumer("BOOK_NEW_ORDER", service::parse, ProcessOrder::class.java.simpleName).use {
        it.run()
    }
}

