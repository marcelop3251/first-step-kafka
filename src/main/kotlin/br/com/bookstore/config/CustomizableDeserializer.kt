package br.com.bookstore.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.lang.RuntimeException
import org.apache.kafka.common.serialization.Deserializer

class CustomizableDeserializer<T> : Deserializer<T> {

    private lateinit var type: Class<T>

    override fun deserialize(topic: String?, data: ByteArray): T {
        return jacksonObjectMapper().readValue(data, type)
    }

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        type = configs?.get(TYPE_CONFIG)?.let {
            Class.forName(it.toString()) as Class<T>
        } ?: throw RuntimeException("Type for deserialization does not exist in the class path")
    }

    companion object {
        const val TYPE_CONFIG = "br.com.bookstore"
    }
}