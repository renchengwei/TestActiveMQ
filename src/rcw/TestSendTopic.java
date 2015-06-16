package rcw;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TestSendTopic {

	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://localhost:61616");
			connection = factory.createConnection();
			connection.start();
			
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createTopic("testtopic");
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			for(int i=0;i<5;i++) {
				TextMessage message = session.createTextMessage("ActiveMq 发送的消息" + i);
				producer.send(message);
			}
			session.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(session != null) {
					session.close();
				}
				
				if(connection != null) {
					connection.close();
				}
				
			} catch (JMSException e) {
			}
		}
	}
}
