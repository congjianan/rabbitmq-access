package com.littlersmall.rabbitmqaccess.example;

import com.littlersmall.rabbitmqaccess.MQAccessBuilder;
import com.littlersmall.rabbitmqaccess.MessageProcess;
import com.littlersmall.rabbitmqaccess.ThreadPoolConsumer;
import com.littlersmall.rabbitmqaccess.common.Constants;
import lombok.Data;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by littlersmall on 16/6/28.
 */
@Service
public class PoolExample {
    private static final String EXCHANGE = "example";
    private static final String ROUTING = "user-example";
    private static final String QUEUE = "user-example";

    @Autowired
    ConnectionFactory connectionFactory;

    private ThreadPoolConsumer<UserMessage> threadPoolConsumer;

    @PostConstruct
    public void init() {
        MQAccessBuilder mqAccessBuilder = new MQAccessBuilder(connectionFactory);
        MessageProcess<UserMessage> messageProcess = new UserMessageProcess();
        //构造执行线程数量等信息（这里开启5个线程）
        threadPoolConsumer = new ThreadPoolConsumer.ThreadPoolConsumerBuilder<UserMessage>()
                .setThreadCount(Constants.THREAD_COUNT).setIntervalMils(Constants.INTERVAL_MILS)
                .setExchange(EXCHANGE).setRoutingKey(ROUTING).setQueue(QUEUE)
                .setMQAccessBuilder(mqAccessBuilder).setMessageProcess(messageProcess)
                .build();
    }

    public void start() throws IOException {
        threadPoolConsumer.start();
    }

    public void stop() {
        threadPoolConsumer.stop();
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        PoolExample poolExample = ac.getBean(PoolExample.class);
        final SenderExample senderExample = ac.getBean(SenderExample.class);
        //消费者先启动
        System.out.println("构建消费线程start");
        //poolExample.start();
        System.out.println("构建消费线程finish");
        new Thread(new Runnable() {
            int id = 0;

            @Override
            public void run() {
//                while (true) {
                    senderExample.send(new UserMessage(id++, "" + System.nanoTime()));

                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//            }
        }).start();
        poolExample.start();
    }
    /***
     * 初始化(生产者)了UserMessage7|1032538561912320
		消费传来的未经转换的消息:(Body:'{"id":7,"name":"1032538561912320"}' MessageProperties [headers={spring_listener_return_correlation=df577f5f-0b8c-4307-a6e7-94fdb8890e37, __TypeId__=com.littlersmall.rabbitmqaccess.example.UserMessage}, contentType=application/json, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, redelivered=false, receivedExchange=example, receivedRoutingKey=user-example, deliveryTag=1])
		消费传来的消息序列化转换过为:UserMessage [id=7, name=1032538561912320]
		处理消费者消息:UserMessage [id=7, name=1032538561912320]
		消费者处理消息结果:DetailRes [isSuccess=true, errMsg=]
     * **/
}
