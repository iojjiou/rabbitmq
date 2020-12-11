package pers.wang.stu.rabbitmq.consumers;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者模型
public class TestConsumer {
    @Test
    public void test(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接主机
        connectionFactory.setHost("192.168.1.110");
        //端口
        connectionFactory.setPort(5672);
        //设置rabbitMq的虚拟主机，（rabbitmq,一个项目对应一个主机。相当于一个数据库）
        connectionFactory.setVirtualHost("/testHost");
        //用户名密码
        connectionFactory.setUsername("wangao");
        connectionFactory.setPassword("3wwrrrrww");

        //创建连接
        //打开通道
        try {
            //连接
            Connection connection = connectionFactory.newConnection();
            //获取信道。信道连接队列
            Channel channel = connection.createChannel();
          //  依然绑定队列（这里必须和消费者队列参数保持一致，否则时找不到对应消费者队列的）
            channel.queueDeclare("one",false,false,false,null);

            /**
             * 消费消息
             * arg1:消息队列名称
             * arg2：消息自动东确认
             * arg3：消费回调接口
             */
            channel.basicConsume("one",true,new DefaultConsumer(channel){

                //参数1标签
                //参数2信封
                //参数3设置的参数
                //参数4，body,队列的消息
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //回调方法内容，（这里时异步的，如果关闭了，连接，可能无法执行到代码，就被关闭了）
                    System.out.println("acceptermes"+new String(body));
                }
            });


            //关闭信道，和连接。由于消费者，保持监听，所以不能不需要关闭。
//            channel.close();
//            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
