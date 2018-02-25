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
    private final String SELECTOR_1="color = 'blue'";
    private final String SELECTOR_2="color = 'blue' AND sal > 2000";
    private final String SELECTOR_3="receiver = 'A'";

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
            this.connection=connectionFactory.createConnection();
            this.connection.start();
            this.session=this.connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            this.destination=this.session.createQueue("first");
            this.messageConsumer=this.session.createConsumer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiver(){
        try {
            this.messageConsumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    class Listener implements MessageListener{
        @Override
        public void onMessage(Message message) {

        }
    }


}
