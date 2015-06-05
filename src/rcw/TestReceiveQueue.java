package rcw;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TestReceiveQueue implements MessageListener {

	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://localhost:61616");
			connection = factory.createConnection();
			connection.start();
			
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("testqueue");
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new TestReceiveQueue());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onMessage(Message arg0) {
		TextMessage tm = (TextMessage) arg0;
		try {
			System.out.println(tm.getText().toString());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
