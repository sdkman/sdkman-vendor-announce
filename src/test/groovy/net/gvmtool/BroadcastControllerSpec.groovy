package net.gvmtool

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.data.domain.Sort.Direction.*
import static org.springframework.http.HttpStatus.OK

class BroadcastControllerSpec extends Specification {

    BroadcastController controller
    BroadcastRepository repository = Mock()

    void setup() {
        controller = new BroadcastController(repository:repository)
    }

    void "should successfully return the current broadcast message"() {
        given:
        def message = "Welcome to GVM!"
        def broadcast = new Broadcast(text: message, date: new Date())
        def broadcastPage = Mock(Page)

        when:
        ResponseEntity<String> result = controller.get(1)

        then:
        broadcastPage.getContent() >> [ broadcast ]
        1 * repository.findAll(_) >> broadcastPage

        and:
        result.statusCode == OK
        result.body == message
    }

    void "should return a given number of latest number of broadcasts"() {
        given:
        def limit = 2
        def broadcast1 = new Broadcast(text: "broadcast 1", date: new Date())
        def broadcast2 = new Broadcast(text: "broadcast 2", date: new Date())
        def broadcast3 = new Broadcast(text: "broadcast 3", date: new Date())
        def broadcastPage = Mock(Page)

        when:
        ResponseEntity<String> result = controller.get(limit)
        def lines = result.body.readLines()

        then:
        broadcastPage.getContent() >> [ broadcast1, broadcast2 ]
        1 * repository.findAll({it.pageSize == limit}) >> broadcastPage

        and:
        lines[0] == broadcast1.text
        lines[1] == broadcast2.text
        ! lines.contains(broadcast3.text)
    }

}

