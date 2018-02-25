package com.csx.activemq.p2p;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Primary;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: TODO
 * @Author: csx
 * @Date: 2018-02-25
 */
public class Producer {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public Producer() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER
                    , ActiveMQConnectionFactory.DEFAULT_PASSWORD
                    , "tcp://0.0.0.0:61616");
            this.connection=connectionFactory.createConnection();
            this.connection.start();
            this.session=this.connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            this.messageProducer=this.session.createProducer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Session getSession(){
        return this.session;
    }

    public void send1(){
        try {
            Destination destination=this.session.createQueue("first");
            MapMessage mapMessage1 = this.session.createMapMessage();
            mapMessage1.setString("name","张三");
            mapMessage1.setString("age","23");
            mapMessage1.setStringProperty("color","blue");
            mapMessage1.setIntProperty("sal",2200);
            int id=1;
            mapMessage1.setInt("id",id);
            String receiver=id%2==0?"A":"B";
            mapMessage1.setStringProperty("receiver",receiver);

            MapMessage mapMessage2=this.session.createMapMessage();
            mapMessage2.setString("name","李四");
            mapMessage2.setString("age","26");
            mapMessage2.setStringProperty("color","red");
            mapMessage2.setIntProperty("sal",1300);
            id=2;
            mapMessage2.setInt("id",id);
            receiver=id%2==0?"A":"B";
            mapMessage2.setStringProperty("receiver",receiver);

            MapMessage mapMessage3=this.session.createMapMessage();
            mapMessage3.setString("name","王五");
            mapMessage3.setString("age","23");
            mapMessage3.setStringProperty("color","red");
            mapMessage3.setIntProperty("sal",1600);
            id=3;
            mapMessage3.setInt("id",id);
            receiver=id%2==0?"A":"B";
            mapMessage3.setStringProperty("receiver",receiver);

            MapMessage mapMessage4=this.session.createMapMessage();
            mapMessage4.setString("name","李四一");
            mapMessage4.setString("age","21");
            mapMessage4.setStringProperty("color","red");
            mapMessage4.setIntProperty("sal",1700);
            id=4;
            mapMessage4.setInt("id",id);
            receiver=id%2==0?"A":"B";
            mapMessage4.setStringProperty("receiver",receiver);

            this.messageProducer.send(destination,mapMessage1,DeliveryMode.NON_PERSISTENT,2,1000*60*10L);
            this.messageProducer.send(destination,mapMessage2,DeliveryMode.NON_PERSISTENT,3,1000*60*10L);
            this.messageProducer.send(destination,mapMessage3,DeliveryMode.NON_PERSISTENT,6,1000*60*10L);
            this.messageProducer.send(destination,mapMessage4,DeliveryMode.NON_PERSISTENT,9,1000*60*10L);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void send2(){
        try {
            Destination destination=this.session.createQueue("first");
            TextMessage textMessage=this.session.createTextMessage("我是一个字符串内容");
            this.messageProducer.send(destination,textMessage,DeliveryMode.NON_PERSISTENT,9,1000*60*10L);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producer producer=new Producer();
        producer.send1();
    }
}
