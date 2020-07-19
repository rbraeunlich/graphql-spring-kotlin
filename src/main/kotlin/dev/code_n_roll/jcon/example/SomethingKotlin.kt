package dev.code_n_roll.jcon.example

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.client.getForEntity

class SomethingKotlin {

    fun makeSomethingRequest(req: RequestObjectKotlin): MyResponseKotlin {
        val restTemplate = RestTemplateBuilder().build()
        val port = "8080"
        val responseEntity = restTemplate.getForEntity<MyResponseKotlin>("http://localhost:$port")
        val body: MyResponseKotlin? = responseEntity.body
        return body ?: DEFAULT_RESPONSE
    }

    companion object {
        private val DEFAULT_RESPONSE = MyResponseKotlin("", 0.0)
    }

}

class RequestObjectKotlin {

}
