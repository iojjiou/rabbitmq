package pers.wang.stu.rabbitmq.producers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//生产者模型
public class TestDemo {

    @Test
    public void test(){
        //创建mq工厂
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
        try {
        //连接
        Connection connection = connectionFactory.newConnection();
        //获取信道。信道连接队列
        Channel channel = connection.createChannel();
        //绑定消息列队
        //参数1:定义队列名称，如果没有，就创建这个
        //参数2:是否持久化。
        //参数3：是否独占队列，只允许一个
        //参数4：是否消费完，就删除。
        //参数5:额外参数
        channel.queueDeclare("one",false,false,false,null);

        //发布消息：
        //参数1：交换机名称，这里直接连接队列没有
        //参数2：队列名
        //参数3；传递消息额外参数
        //参数4，消息具体内容,字节
        channel.basicPublish("","one",null,"hello,rabbit".getBytes());
        //关闭信道，和连接
        channel.close();
        connection.close();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (TimeoutException e) {
        e.printStackTrace();
    }
}
}
