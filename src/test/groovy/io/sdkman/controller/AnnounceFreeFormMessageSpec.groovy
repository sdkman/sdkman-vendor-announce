/*
 * Copyright 2014 Marco Vermeulen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sdkman.controller

import io.sdkman.domain.Broadcast
import io.sdkman.repo.BroadcastRepository
import io.sdkman.request.FreeFormAnnounceRequest
import io.sdkman.response.Announcement
import io.sdkman.service.TextService
import io.sdkman.service.TwitterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class AnnounceFreeFormMessageSpec extends Specification {

    AnnounceController controller
    BroadcastRepository repository = Mock()
    TextService textService = Mock()
    TwitterService twitterService = Mock()

    void setup(){
        controller = new AnnounceController(
                repository: repository,
                textService: textService,
                twitterService: twitterService)
    }

    void "announce free form should save a free form message"() {
        given:
        def text = "message"
        def request = new FreeFormAnnounceRequest(text: text)
        def broadcast = new Broadcast(id: "1234", text: text)

        when:
        controller.freeForm(request)

        then:
        1 * repository.save({it.text == text}) >> broadcast
    }

    void "announce free form should return a broadcast id after saving"() {
        given:
        def text = "message"
        def request = new FreeFormAnnounceRequest(text: text)

        and:
        def broadcastId = "1234"
        def broadcast = new Broadcast(id: broadcastId, text: text, date: new Date())

        and:
        repository.save(_) >> broadcast

        when:
        ResponseEntity<Announcement> response = controller.freeForm(request)

        then:
        response.statusCode == HttpStatus.OK
        response.body.id == broadcastId
    }

    void "announce free form should post a free-form message to twitter"() {
        given:
        def status = "status"
        def request = new FreeFormAnnounceRequest(text: status)
        def broadcast = new Broadcast(id: "1234", text: status, date: new Date())

        and:
        repository.save(_) >> broadcast

        when:
        controller.freeForm(request)

        then:
        1 * twitterService.update(status)
    }

}
