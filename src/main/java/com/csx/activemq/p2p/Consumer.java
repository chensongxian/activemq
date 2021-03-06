package com.csx.activemq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: TODO
 * @Author: csx
 * @Date: 2018-02-25
 */
public class Consumer {
    private final String SELECTOR_1 = "color = 'blue'";
    private final String SELECTOR_2 = "color = 'blue' AND sal > 2000";
    private final String SELECTOR_3 = "receiver = 'A'";

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;
    private Destination destination;

    public Consumer() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER
                    , ActiveMQConnectionFactory.DEFAULT_PASSWORD
                    , "tcp://0.0.0.0:61616");
            this.connection = connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            this.destination = this.session.createQueue("first");
            this.messageConsumer = this.session.createConsumer(destination,SELECTOR_1);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiver() {
        try {
            this.messageConsumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    class Listener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            try {
                if (message instanceof TextMessage) {
                    TextMessage textMessage= (TextMessage) message;
                    System.out.println(textMessage.getText());
                }

                if (message instanceof MapMessage) {
                    MapMessage ret = (MapMessage) message;
                    System.out.println(ret.toString());
                    System.out.println(ret.getString("name"));
                    System.out.println(ret.getString("age"));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Consumer consumer=new Consumer();
        consumer.receiver();
    }


}
