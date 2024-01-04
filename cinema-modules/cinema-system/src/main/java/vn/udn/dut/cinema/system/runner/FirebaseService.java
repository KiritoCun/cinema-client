package vn.udn.dut.cinema.system.runner;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service
public class FirebaseService {
	public void sendNotification(String title, String content, String component, String token)
			throws FirebaseMessagingException {
		Message message = Message.builder().putData("title", title).putData("body", content).putData("priority", "high")
				.putData("component", component).setToken(token).build();
		FirebaseMessaging.getInstance().send(message);
	}
}
