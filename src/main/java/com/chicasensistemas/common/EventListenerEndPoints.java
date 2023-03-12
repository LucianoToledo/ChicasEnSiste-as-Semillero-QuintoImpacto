package com.chicasensistemas.common;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
@Slf4j
public class EventListenerEndPoints extends ApplicationContextEvent {


  public EventListenerEndPoints(ApplicationContext source) {
    super(source);
  }

  @org.springframework.context.event.EventListener
  public void onApplicationEvent(ContextRefreshedEvent event){
    ApplicationContext applicationContext = event.getApplicationContext();
    RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
        .getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class);
    Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
        .getHandlerMethods();
    map.forEach((key, value) -> log.info("{} {}", key, value));
  }

}
