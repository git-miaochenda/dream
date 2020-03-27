package com.test.mq;

public class SpringMqTest {
/*
    @Test
    public void testQueueConsumer()throws IOException{
        new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //系统读取
        System.in.read();
    }

    @Test
    public void testSpringQueueProdicer(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //从容其中取出jmsTemplate
        JmsTemplate jmsTemplate = ctx.getBean(JmsTemplate.class);
        //从容器中获取Destination(点对点 /一对多）
        Queue queue = ctx.getBean(Queue.class);
        //使用jmsTemplate进行发送--第一个发送的地点，第二是发送的内容
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //使用session创建一个TestMessage类型
                TextMessage textMessage = session.createTextMessage("这是使用Spring和ActiveMq整个发送的消息--queue");
                return textMessage;
            }
        });
    }*/
}
