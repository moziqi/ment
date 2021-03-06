package hemu.ment.communication.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by muu on 2015/6/2.
 */
@MessageDriven(mappedName = "queue/comm/in", name = "commInQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/comm/in"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "user", propertyValue = "admin"),
		@ActivationConfigProperty(propertyName = "password", propertyValue = "123456y!")})
public class CommBean implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage textMessages = (TextMessage) message;
		try {
			System.out.println("Received message " + textMessages.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}