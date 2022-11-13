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
package rafael.alcocer.caldera.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import rafael.alcocer.caldera.data.ThingData;
import rafael.alcocer.caldera.model.Thing;

@Service
public class ThingServiceImpl implements ThingService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ThingServiceImpl.class);

    private final ThingData thingData;
    
    public ThingServiceImpl(ThingData thingData) {
        this.thingData = thingData;
    }
    
    @Override
    public Thing generateThingData() {
        LOGGER.info("ThingServiceImpl... generateThingData()... ");
        return thingData.generateThingData();
    }
}
