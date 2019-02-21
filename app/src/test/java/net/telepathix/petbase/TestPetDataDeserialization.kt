package net.telepathix.petbase

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.telepathix.petbase.model.Pet
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TestPetDataDeserialization {

    private val testData = "[\n" +
            "\t{\n" +
            "\t\t\"image_url\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Cat_poster_1.jpg/1200px-Cat_poster_1.jpg\",\n" +
            "\t\t\"title\": \"Cat\",\n" +
            "\t\t\"content_url\": \"https://en.wikipedia.org/wiki/Cat\",\n" +
            "\t\t\"date_added\": \"2018-06-02T03:27:38.027Z\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"image_url\": \"https://upload.wikimedia.org/wikipedia/commons/3/30/RabbitMilwaukee.jpg\",\n" +
            "\t\t\"title\": \"Rabbit\",\n" +
            "\t\t\"content_url\": \"https://en.wikipedia.org/wiki/Rabbit\",\n" +
            "\t\t\"date_added\": \"2018-06-06T08:36:16.027Z\"\n" +
            "\t}\n" +
            "]"

    @Test
    fun testPetDataDeserialize() {
        val petList =  jacksonObjectMapper().readValue<List<Pet>>(testData)
        assertEquals(petList.size, 2)
        assertEquals(petList[0].title, "Cat")
        assertEquals(petList[0].contentUrl, "https://en.wikipedia.org/wiki/Cat")
        assertEquals(petList[1].title, "Rabbit")
        assertEquals(petList[1].contentUrl, "https://en.wikipedia.org/wiki/Rabbit")
    }
}
