package vn.udn.dut.cinema.system.runner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.system.service.ISysConfigService;

@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Service
public class FirebaseConfig {
	@Autowired
	private ISysConfigService configService;
	
	@PostConstruct
    public void initialize() {
        try {
        	String firebaseKey = configService.selectConfigByKey("sys.firebase.secret");
        	if (firebaseKey == null) throw new Exception("Firebase key null!");

			InputStream inputStream = new ByteArrayInputStream(firebaseKey.getBytes("UTF-8"));
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(inputStream))
					.setDatabaseUrl(configService.selectConfigByKey("sys.firebase.db")).build();

			FirebaseApp.initializeApp(options);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
}
