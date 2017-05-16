package com.jcloud.b2c.platform.service.monitor;

import com.jcloud.b2c.platform.domain.vo.MqInfoBean;
import com.jcloud.b2c.platform.domain.vo.MqQueue;
import com.jcloud.b2c.platform.domain.vo.MqTopic;

import java.util.List;

/**
 * Created by panshizhao on 2017/4/10.
 */
public interface MonitorService {

    List<MqInfoBean> getAllMqInfo(String brokerUrl) throws Exception;

    List<MqQueue> getMqQueueByIp(String httpIp,String mqQueues) throws Exception;

    List<MqTopic> getMqTopicByIp(String httIp,String mqTopics) throws Exception;
}
