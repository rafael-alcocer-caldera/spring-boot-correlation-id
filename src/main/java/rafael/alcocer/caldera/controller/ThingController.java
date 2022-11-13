/**
 * Copyright [2022] [RAFAEL ALCOCER CALDERA]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rafael.alcocer.caldera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import rafael.alcocer.caldera.model.Thing;
import rafael.alcocer.caldera.service.ThingService;

@RequiredArgsConstructor
@RestController
public class ThingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThingController.class);

    private final ThingService thingService;

    @RequestMapping("/thing")
    public Thing showThing() {
        LOGGER.info("ThingController... showThing()... ");
        return thingService.generateThingData();
    }
}
