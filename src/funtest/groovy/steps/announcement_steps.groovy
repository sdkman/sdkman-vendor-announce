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
package steps

import static cucumber.api.groovy.EN.And

And(~'^a new message to be announced for "([^"]*)" version "([^"]*)" url "([^"]*)"$')
        { String msgCandidate, String msgVersion, String releaseUrl ->
    candidate = msgCandidate
    version = msgVersion
    url = releaseUrl
}

And(~'^a new message to be announced for "([^"]*)" version "([^"]*)"$')
        { String msgCandidate, String msgVersion ->
    candidate = msgCandidate
    version = msgVersion
    url = ""
}