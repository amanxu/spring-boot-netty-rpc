package com.elegant.rpc.core.client;

import com.elegant.rpc.core.annotation.RpcHandler;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

/**
 * @description:
 * @author: xiaoxu.nie
 * @date: 2019/12/12 14:35
 */
@Slf4j
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private Map<String, Object> currentJobMaps = Maps.newConcurrentMap();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if (event.getApplicationContext().getParent() == null) {
            currentJobMaps = event.getApplicationContext().getBeansWithAnnotation(RpcHandler.class);
            log.info("Client init jobs success...");
        }
    }

    /**
     * 根据
     * @param beanName
     * @return
     */
    public Object getBeanByName(String beanName) {
        if (StringUtils.isBlank(beanName)) {
            return null;
        }
        return currentJobMaps.get(beanName);
    }
}
