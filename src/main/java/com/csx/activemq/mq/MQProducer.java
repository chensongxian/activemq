
package com.csx.activemq.mq;

import com.alibaba.fastjson.JSONObject;
import com.csx.activemq.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service("mqProducer")
public class MQProducer {
	
	private JmsTemplate jmsTemplate;
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	
	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * <B>方法名称：</B>发送邮件信息对象<BR>
	 * <B>概要说明：</B>发送邮件信息对象<BR>
	 * @param mail
	 */
	public void sendMessage(final Mail mail) {
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(JSONObject.toJSONString(mail));
			}
		});
	}

}
