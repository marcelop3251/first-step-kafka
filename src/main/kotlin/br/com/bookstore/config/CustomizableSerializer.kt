package br.com.bookstore.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.common.serialization.Serializer

class CustomizableSerializer<T> : Serializer<T> {

    override fun serialize(topic: String?, data: T): ByteArray {
        return jacksonObjectMapper().writeValueAsBytes(data)
    }

}

