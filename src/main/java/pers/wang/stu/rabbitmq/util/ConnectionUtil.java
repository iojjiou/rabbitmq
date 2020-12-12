package pers.wang.stu.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 连接冗余代码
 */
public class ConnectionUtil {
    //Connectionn
    public static ConnectionFactory connectionFactory;

    //初始化connecction
    static{
        ConnectionFactory connectionFactory1 =  new ConnectionFactory();
        //连接地址
        connectionFactory1.setHost("192.168.1.110");
        //连接端口
        connectionFactory1.setPort(5672);
        //rabbit虚拟主机
        connectionFactory1.setVirtualHost("/testHost");
        //登录账户
        connectionFactory1.setUsername("wangao");
        connectionFactory1.setPassword("3wwrrrrww");

        connectionFactory = connectionFactory1;
    }

    public static Connection getConnection(){
        Connection result = null;
        try {
            result= connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return  result;
    }

    //关闭
    public static void close(Connection connection, Channel channel){
        try {
            if(channel!=null) channel.close();
            if(connection!=null)connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
