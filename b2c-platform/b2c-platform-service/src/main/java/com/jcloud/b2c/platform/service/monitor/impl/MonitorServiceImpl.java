package com.jcloud.b2c.platform.service.monitor.impl;

import com.jcloud.b2c.platform.common.constant.MqStatus;
import com.jcloud.b2c.platform.common.util.HttpUtil;
import com.jcloud.b2c.platform.domain.vo.MqInfoBean;
import com.jcloud.b2c.platform.domain.vo.MqQueue;
import com.jcloud.b2c.platform.domain.vo.MqTopic;
import com.jcloud.b2c.platform.service.monitor.MonitorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panshizhao on 2017/4/10.
 */
@Component
public class MonitorServiceImpl implements MonitorService {

    private static Logger log = Logger.getLogger(MonitorServiceImpl.class);

    /**
     * 异常输出标志
     */
    private final static String ERROR_FLAG = "-1";
    /**
     * MQ基本信息url
     */
    private final static String FIRSTPAGE = "/admin/index.jsp";
    /**
     * QUERY queue URL
     */
    private final static String QUEUEPAGE = "/admin/queues.jsp";
    /**
     * QUERY TOPIC URL
     */
    private final static String TOPICPAGE = "/admin/topics.jsp";


    @Override
    public List<MqInfoBean> getAllMqInfo(String brokerUrl) throws Exception {
        List<MqInfoBean> mqList = new ArrayList<MqInfoBean>();
        String content = "";
        try {
            for (String httpIp : brokerUrl.split(";")) {
                MqInfoBean mqInfo = new MqInfoBean();
                //设置该MQip地址
                mqInfo.setIp(httpIp);

                content = HttpUtil.sendHttp(httpIp+FIRSTPAGE);
                //请求超时，超时时间在mq properties里面设置  超时表示MQ不存活
                if (content == null || "".equals(content)) {
                    mqInfo.setMemoryUsed(ERROR_FLAG);
                    mqInfo.setStatus(MqStatus.ABNORMAL.getValue());
                    mqInfo.setStoreUsed(ERROR_FLAG);
                    mqInfo.setTempUsed(ERROR_FLAG);
                    mqInfo.setVersion(ERROR_FLAG);
                    mqInfo.setName(ERROR_FLAG);
                    mqList.add(mqInfo);
                    continue;
                }

                String tmpFlag = HttpUtil.STARTTDHTML + "Version" + HttpUtil.ENDTDHTML;
                String flag = HttpUtil.ENDBHTML + HttpUtil.ENDTDHTML;
                if (content.indexOf(tmpFlag) != -1) {
                    //设置MQ版本号
                    String versionTemp = content.substring(content.indexOf(tmpFlag), content.indexOf(flag, content.indexOf(tmpFlag)));
                    mqInfo.setVersion(versionTemp.substring(versionTemp.lastIndexOf(HttpUtil.STARTBHTML) + 3));
                }
                tmpFlag = HttpUtil.STARTTDHTML + "Name" + HttpUtil.ENDTDHTML;
                if (content.indexOf(tmpFlag) != -1) {
                    //设置MQ名称
                    String nameTemp = content.substring(content.indexOf(tmpFlag), content.indexOf(flag, content.indexOf(tmpFlag)));
                    mqInfo.setName(nameTemp.substring(nameTemp.lastIndexOf(HttpUtil.STARTBHTML) + 3));
                }
                tmpFlag = HttpUtil.STARTTDHTML + "Store percent used" + HttpUtil.ENDTDHTML;
                if (content.indexOf(tmpFlag) != -1) {
                    //设置MQ存储空间占用
                    String useedTemp = content.substring(content.indexOf(tmpFlag), content.indexOf(flag, content.indexOf(tmpFlag)));
                    mqInfo.setStoreUsed(useedTemp.substring(useedTemp.lastIndexOf(HttpUtil.STARTBHTML) + 3));
                }
                tmpFlag = HttpUtil.STARTTDHTML + "Memory percent used" + HttpUtil.ENDTDHTML;
                if (content.indexOf(tmpFlag) != -1) {
                    //设置MQ内存空间占用
                    String memoryTemp = content.substring(content.indexOf(tmpFlag), content.indexOf(flag, content.indexOf(tmpFlag)));
                    mqInfo.setMemoryUsed(memoryTemp.substring(memoryTemp.lastIndexOf(HttpUtil.STARTBHTML) + 3));
                }
                tmpFlag = HttpUtil.STARTTDHTML + "Temp percent used" + HttpUtil.ENDTDHTML;
                if (content.indexOf(tmpFlag) != -1) {
                    //设置MQ临时空间占用
                    String tempTemp = content.substring(content.indexOf(tmpFlag), content.indexOf(flag, content.indexOf(tmpFlag)));
                    mqInfo.setTempUsed(tempTemp.substring(tempTemp.lastIndexOf(HttpUtil.STARTBHTML) + 3));
                }
                //设置MQ状态
                mqInfo.setStatus(MqStatus.NORMAL.getValue());
                mqList.add(mqInfo);
            }
        } catch (Exception e) {
            log.error("[UMP前端#MQ监控#" + this.getClass().getName() + "]查询MQ信息异常", e);
            throw e;
        }

        return mqList;
    }

    @Override
    public List<MqQueue> getMqQueueByIp(String httpIp,String mqQueues) throws Exception {
        List<MqQueue> queueList = new ArrayList<MqQueue>();

        try {
            //设置queue请求url
            String content = HttpUtil.sendHttp( httpIp + QUEUEPAGE);
            content = content.replaceAll("\r|\n", "").trim();
            if (content != null && !"".equals(content)) {
                for (String queue : mqQueues.split(";")) {
                    MqQueue queueBean = new MqQueue();
                    //设置过滤参数
                    String first = HttpUtil.ENDINDEXHTML + queue + HttpUtil.ENDHREFHTML + HttpUtil.ENDTDHTML + HttpUtil.STARTTDHTML;
                    int index;
                    //检查该queue是否在该MQ中存在
                    if ((index = content.indexOf(first)) == -1) {
                        continue;
                    }
                    String temp = content.substring(index, content.indexOf(HttpUtil.STARTHREFHTML, index));
                    temp = temp.replace(first, "");
                    String[] list = temp.split(HttpUtil.ENDTDHTML + HttpUtil.STARTTDHTML);
                    //设置队列相关参数
                    if (list.length > 3) {
                        queueBean.setNumOfPending(list[0]);
                        queueBean.setNunOfCusmor(list[1]);
                        queueBean.setMessageEnqueued(list[2]);
                        queueBean.setMessageDequeued(list[3]);
                        queueBean.setName(queue);
                        queueList.add(queueBean);
                    }
                }
            }
        } catch (Exception e) {
            log.error("[UMP前端#MQ监控#" + this.getClass().getName() + "]查询MQ详情异常", e);
            throw e;
        }

        return queueList;
    }

    @Override
    public List<MqTopic> getMqTopicByIp(String httpIp,String mqTopics) throws Exception {
        List<MqTopic> topicList = new ArrayList<MqTopic>();

        try {
            //设置topic请求url
            String content = HttpUtil.sendHttp(httpIp + TOPICPAGE);
            content = content.replaceAll("\r|\n", "").trim();
            if (content != null && !"".equals(content)) {
                for (String topic : mqTopics.split(";")) {
                    MqTopic topicBean = new MqTopic();
                    String first = HttpUtil.ENDINDEXHTML + topic + HttpUtil.ENDHREFHTML;
                    int index;
                    //检查该topic是否在该MQ中存在
                    if ((index = content.indexOf(first)) == -1) {
                        continue;
                    }
                    String temp = content.substring(index, content.indexOf(HttpUtil.STARTHREFHTML, index));
                    temp = temp.replace(first, "");
                    String[] list = temp.split(HttpUtil.ENDTDHTML + HttpUtil.STARTTDHTML);
                    //设置主题相关参数
                    if (list.length > 3) {
                        topicBean.setNunOfCusmor(list[1]);
                        topicBean.setMessageEnqueued(list[2]);
                        topicBean.setMessageDequeued(list[3]);
                        topicBean.setName(topic);
                        topicList.add(topicBean);
                    }
                }
            }
        } catch (Exception e) {
            log.error("[UMP前端#MQ监控#" + this.getClass().getName() + "]查询topic在MQ中信息异常", e);
            throw e;
        }

        return topicList;
    }
}
