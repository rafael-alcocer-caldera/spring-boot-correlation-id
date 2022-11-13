# SPRING BOOT CORRELATION ID EXAMPLE

## Synopsis

The project is a Spring Boot Application.

## Motivation

I wanted to do a Spring Boot Application, that shows how to implement the correlation id to track the request.

## Pre Requirements

You only need to add this to the application.yml:

```
logging:
  pattern: 
    console: "%-4relative [%thread] %-5level %logger{35} %X{RacCorrelationId} --- %msg %n"
```

Or this if you have application.properties:

```
logging.pattern.console=%-4relative [%thread] %-5level %logger{35} %X{RacCorrelationId} --- %msg %n
```

You also need to add the filter:

```java
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID = "RacCorrelationId";

    private static final Logger LOGGER = LoggerFactory.getLogger(CorrelationIdFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
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
```

## Notes

I replaced the following code:

```java
@RequiredArgsConstructor
@RestController
public class ThingController {
    
    private final ThingService thingService;
    
    public ThingController(ThingServiceImpl thingService) {
        this.thingService = thingService;
    }
```

With this code using lombok annotation **@RequiredArgsConstructor**.

Will create constructor receiving non-static final fields.

The annotation will not generate a constructor for the following fields:
- Initialized non-null fields
- Initialized final fields
- Static fields
- Non-final fields

```java
@RequiredArgsConstructor
@RestController
public class ThingController {
    
    private final ThingService thingService;
```

USING POSTMAN:
--------------
GET
http://localhost:8050/thing


Response:
---------
```json
{
    "id": 13,
    "name": "NEW THING NAME",
    "description": "is the pattern of narrative development that aims to make vivid a place, object, character, or group"
}
```


ECLIPSE CONSOLE:
----------------
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.5)

1422 [main] INFO  r.a.c.CorrelationIdApplication  --- Starting CorrelationIdApplication using Java 1.8.0_261 on DESKTOP-M92QTH6 with PID 7448 (C:\RAC\GitHub\spring-boot-correlation-id\target\classes started by RAC in C:\RAC\GitHub\spring-boot-correlation-id) 
1427 [main] INFO  r.a.c.CorrelationIdApplication  --- No active profile set, falling back to 1 default profile: "default" 
3614 [main] INFO  o.s.b.w.e.tomcat.TomcatWebServer  --- Tomcat initialized with port(s): 8050 (http) 
3633 [main] INFO  o.a.catalina.core.StandardService  --- Starting service [Tomcat] 
3634 [main] INFO  o.a.catalina.core.StandardEngine  --- Starting Servlet engine: [Apache Tomcat/9.0.68] 
3878 [main] INFO  o.a.c.c.C.[Tomcat].[localhost].[/]  --- Initializing Spring embedded WebApplicationContext 
3879 [main] INFO  o.s.b.w.s.c.ServletWebServerApplicationContext  --- Root WebApplicationContext: initialization completed in 2329 ms 
4719 [main] INFO  o.s.b.w.e.tomcat.TomcatWebServer  --- Tomcat started on port(s): 8050 (http) with context path '' 
4736 [main] INFO  r.a.c.CorrelationIdApplication  --- Started CorrelationIdApplication in 4.324 seconds (JVM running for 5.037) 
16392 [http-nio-8050-exec-2] INFO  o.a.c.c.C.[Tomcat].[localhost].[/]  --- Initializing Spring DispatcherServlet 'dispatcherServlet' 
16392 [http-nio-8050-exec-2] INFO  o.s.web.servlet.DispatcherServlet  --- Initializing Servlet 'dispatcherServlet' 
16394 [http-nio-8050-exec-2] INFO  o.s.web.servlet.DispatcherServlet  --- Completed initialization in 2 ms 
16408 [http-nio-8050-exec-2] INFO  r.a.c.filter.CorrelationIdFilter  --- request.getHeader(RacCorrelationId): null 
16413 [http-nio-8050-exec-2] INFO  r.a.c.filter.CorrelationIdFilter  --- correlationId created: 427b9a9d-5836-4a79-b248-35ebf4ae5121 
16459 [http-nio-8050-exec-2] INFO  r.a.c.controller.ThingController 427b9a9d-5836-4a79-b248-35ebf4ae5121 --- ThingController... showThing()...  
16459 [http-nio-8050-exec-2] INFO  r.a.c.service.ThingServiceImpl 427b9a9d-5836-4a79-b248-35ebf4ae5121 --- ThingServiceImpl... generateThingData()...  
16653 [http-nio-8050-exec-2] INFO  r.a.c.filter.CorrelationIdFilter 427b9a9d-5836-4a79-b248-35ebf4ae5121 --- Removing correlationId: 427b9a9d-5836-4a79-b248-35ebf4ae5121 
```

You can see that **427b9a9d-5836-4a79-b248-35ebf4ae5121** is added in every LOGGER automatically.


## License

All work is under Apache 2.0 license