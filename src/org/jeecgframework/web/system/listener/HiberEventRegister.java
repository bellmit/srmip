package org.jeecgframework.web.system.listener;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 注册对新增、修改、删除的事件监听
 * 
 * @author admin
 * 
 */
@Service("hiberEventRegister")
public class HiberEventRegister {

    @Autowired
    private LogEventListener logEventListener;

    @Autowired
    private SessionFactory sessionFactory;

    @PostConstruct
    public void registerListeners() {
        EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
                EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(logEventListener);
        registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(logEventListener);
        registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(logEventListener);
    }

}
