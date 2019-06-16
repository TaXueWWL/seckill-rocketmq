package org.apache.rocketmq.message.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author snowalker
 * @date 2018/9/16
 * @desc 基础协议类
 */
public abstract class BaseMsg {

    public Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**版本号，默认1.0*/
    private String version = "1.0";
    /**主题名*/
    private String topicName;

    public abstract String encode();

    public abstract void decode(String msg);

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "BaseMsg{" +
                "version='" + version + '\'' +
                ", topicName='" + topicName + '\'' +
                '}';
    }
}


