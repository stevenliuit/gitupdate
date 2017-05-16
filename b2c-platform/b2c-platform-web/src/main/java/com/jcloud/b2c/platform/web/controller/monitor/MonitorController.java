package com.jcloud.b2c.platform.web.controller.monitor;

import com.jcloud.b2c.platform.domain.vo.MqInfoBean;
import com.jcloud.b2c.platform.domain.vo.MqQueue;
import com.jcloud.b2c.platform.domain.vo.MqTopic;
import com.jcloud.b2c.platform.service.monitor.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dugang3 on 2017/4/10.
 */
@Controller
@RequestMapping(value="/monitor")
public class MonitorController {
    private static final Logger log = LoggerFactory.getLogger(MonitorController.class);

    @Resource
    private MonitorService monitorService;

    @Value("${mq.monitor.urls}")
    private String mqUrls;

    @Value("${mq.queue.names}")
    private String mqQueues;

    @Value("${mq.topic.names}")
    private String mqTopics;
    @RequestMapping("/index")
    public ModelAndView index(Map<String, Object> model) throws Exception {
        ModelAndView listView = new ModelAndView();
        try{
            List<MqInfoBean> mqInfoList = monitorService.getAllMqInfo(mqUrls);
            listView.addObject("mqInfo",mqInfoList);
            listView.setViewName("/monitor/list");
        }catch (Exception e){
            log.error("获取MQ信息失败"+e.getMessage());
        }
        return listView;
    }

    @RequestMapping("/detail")
    public ModelAndView detail(String httpIP) throws Exception {
        ModelAndView detailView=new ModelAndView();
        try{
            List<MqInfoBean> mqList = monitorService.getAllMqInfo(mqUrls);
            List<MqQueue> queueList = monitorService.getMqQueueByIp(httpIP,mqQueues);
            //查询主题信息
            List<MqTopic>topicList = monitorService.getMqTopicByIp(httpIP,mqTopics);
            MqInfoBean mqInfoBean = new MqInfoBean();
            for(MqInfoBean mib : mqList){
                if(mib.getIp().equals(httpIP)){
                    mqInfoBean = mib;
                }
            }
            detailView.addObject("mqInfoBean",mqInfoBean);
            detailView.addObject("queueList",queueList);
            detailView.addObject("topicList",topicList);
            detailView.setViewName("/monitor/detail");
        }catch (Exception e){
            log.error("获取MQ信息失败"+e.getMessage());
        }
        return detailView;
    }
}
