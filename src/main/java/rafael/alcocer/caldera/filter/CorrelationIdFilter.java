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
package rafael.alcocer.caldera.filter;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID = "RacCorrelationId";

    private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationIdFilter.class);

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
        String correlationId = request.getHeader(CORRELATION_ID);
        LOGGER.info("request.getHeader(" + CORRELATION_ID + "): " + correlationId);

        if (StringUtils.isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();
            LOGGER.info("correlationId created: " + correlationId);
        }

        try {
            MDC.put(CORRELATION_ID, correlationId);
            filterChain.doFilter(request, response);
        } finally {
            LOGGER.info("Removing correlationId: " + MDC.get(CORRELATION_ID));
            MDC.remove(CORRELATION_ID);
        }
    }
}
