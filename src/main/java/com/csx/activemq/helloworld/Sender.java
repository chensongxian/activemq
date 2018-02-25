package com.csx.activemq.helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: TODO
 * @Author: csx
 * @Date: 2018-02-25
 */
public class Sender {
    public static void main(String[] args) throws JMSException {
        //1. 建立sessionFactory工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER
                                                                            ,ActiveMQConnectionFactory.DEFAULT_PASSWORD
                                                                            ,"tcp://0.0.0.0:61616");
        //2. 通过connectionFactory创建Connection连接,并用start方法开启连接
        Connection connection=connectionFactory.createConnection();
        connection.start();

        //3. 通过Connection创建session会话（上下文环境对象）,参数一是否开启配置，参数二签收模式
//        Session session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
        //使用CLIENT_ACKNOWLEDGE签收方式
        Session session=connection.createSession(Boolean.TRUE,Session.CLIENT_ACKNOWLEDGE);

        //4. session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象

        Destination destination=session.createQueue("firstQueue");
        //5. 通过session对象创建消息的发送和接受对象（生产者和消费者），MessageProducer和MessageConsumer
        MessageProducer producer=session.createProducer(destination);
        //6. 通过setDeliveryMode设置持久化特性和非持久化特性
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //7. 创建数据并发送
        for(int i=0;i<5;i++){
            TextMessage textMessage=session.createTextMessage();
            textMessage.setText("消息内容--"+i);
            System.out.println("生产者生产消息："+i);
            producer.send(textMessage);
        }

        //开启事务必须提交
        session.commit();

        if(connection!=null){
            connection.close();
        }


    }
}
