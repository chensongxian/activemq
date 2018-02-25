package com.csx.activemq.pb;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: TODO
 * @Author: csx
 * @Date: 2018-02-25
 */
public class Publish {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public Publish() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER
                    , ActiveMQConnectionFactory.DEFAULT_PASSWORD
                    , "tcp://0.0.0.0:61616");
            this.connection = connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            this.messageProducer = this.session.createProducer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return this.session;
    }

    public void sendMessage() {
        try {
            Destination destination = this.session.createTopic("topic");
            TextMessage textMessage = this.session.createTextMessage("我是内容");
            this.messageProducer.send(destination, textMessage,DeliveryMode.NON_PERSISTENT,9,1000*60*10L);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Publish producer = new Publish();
        producer.sendMessage();
    }
}
