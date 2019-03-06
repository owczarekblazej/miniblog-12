package pl.sda.jp.miniblog12;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TimeMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return LocalTime.now().toString();
    }
}
