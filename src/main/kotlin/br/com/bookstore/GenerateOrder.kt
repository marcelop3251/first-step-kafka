package br.com.bookstore

import br.com.bookstore.dto.Order
import br.com.bookstore.service.Producer
import io.azam.ulidj.ULID
import java.util.*


fun main() {

    Producer<Order>().use {
        for(i in 1..10) {
            var value = Order("$i", "novo livro")
            val key = ULID.random()
            it.send("BOOK_NEW_ORDER", key, value)
        }
    }
}


