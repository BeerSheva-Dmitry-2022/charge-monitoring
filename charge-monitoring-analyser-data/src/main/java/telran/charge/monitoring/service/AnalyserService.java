package telran.charge.monitoring.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.charge.monitoring.dto.Sensor;

import java.util.function.Consumer;

@Service
@Log4j2
public class AnalyserService {

    @Bean
    Consumer<Sensor> chargeAnalyser(){
        return this::analyserProcessing;
    }

    private void analyserProcessing(Sensor sensor) {
        log.debug("receive sensor {}", sensor);
    }
}
