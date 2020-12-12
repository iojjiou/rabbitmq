package pers.wang.stu.rabbitmq.consumers;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import pers.wang.stu.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * fanoutEx
 */
public class ExConsumer2 {
    public static void main(String[] args) {
        consumer();
    }

    public  static  void consumer(){
        Connection connection = ConnectionUtil.getConnection();
        try {
            Channel channel  =connection.createChannel();
            //通道绑定交换机,1交换机名，2类型
            channel.exchangeDeclare("fanoutEx","fanout");
            //临时队列，广播模式，队列持久没有意义，需要的时候来个临时队列
            String queueName = channel.queueDeclare().getQueue();
            //绑定交换机队列,第三个参数路由
            channel.queueBind(queueName,"fanoutEx","");
            //消费消息
            channel.basicConsume(queueName,true,new DefaultConsumer(channel){

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("a2222222222");
                    System.out.println("消费消息："+new String(body));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
