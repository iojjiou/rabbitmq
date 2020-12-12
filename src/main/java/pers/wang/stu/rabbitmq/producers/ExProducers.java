package pers.wang.stu.rabbitmq.producers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;
import pers.wang.stu.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * 消费者监听后启动，消费者接收订阅消息，不同于先向队列添加。
 */
public class ExProducers {

    public static void main(String[] args) {
        send();
    }

    public static   void send(){
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = null;
        try {
             channel = connection.createChannel();
            //交换机声明,参数1交换机名称，参数2，交换机类型，fanout(广播类型)
            channel.exchangeDeclare("fanoutEx","fanout");
            //发布,参数1交换机名，参数2，路由，广播模式无意义不填，参数3消息持久化特性，参数4,消息内容。
            channel.basicPublish("fanoutEx","",null,"Exfanout mess".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭
        ConnectionUtil.close(connection,channel);
    }

}
