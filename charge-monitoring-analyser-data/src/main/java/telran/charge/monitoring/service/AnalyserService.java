package telran.charge.monitoring.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.charge.monitoring.dto.Charge;
import telran.charge.monitoring.dto.ChargeStatus;
import telran.charge.monitoring.dto.Sensor;
import telran.charge.monitoring.entity.SensorValues;
import telran.charge.monitoring.repo.SensorRepository;

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

@Service
@Log4j2
public class AnalyserService {

    @Value("${app.charge.avg.threshold.count:5}")
    int avgThresholdCount;
    @Value("${app.charge.notifier.threshold.fault:1000}")
    int statusThresholdFault;

    SensorRepository service;
    StreamBridge streamBridge;

    public AnalyserService(SensorRepository service, StreamBridge streamBridge) {
        this.service = service;
        this.streamBridge = streamBridge;
    }


    @Bean
    Consumer<Sensor> chargeAnalyser(){
        return this::analyserProcessing;
    }

    private void analyserProcessing(Sensor sensor) {
        log.debug("receive sensor {}", sensor);
        SensorValues sensorValues = service.findById(sensor.getId()).orElse(null);
        if(sensorValues==null){
            sensorValues = new SensorValues(sensor.getId());
        }
        List<Integer> values = sensorValues.getValues();
        values.add(sensor.getPower());


        if(values.size() >= avgThresholdCount){
            int avg = getAverage(values);
            values.clear();
            Charge charge = Charge.builder()
                            .idSensor(sensor.getId())
                                    .power(avg)
                    .timestamp(sensor.getTimestamp())
                    .status(avg < statusThresholdFault ? ChargeStatus.EMPTY : ChargeStatus.CHARGE)
            .build();
            log.debug("Send message to topic status {}", charge);
            streamBridge.send("analyser-status-out-0", charge);
        }

        values.add(sensor.getPower());
        sensorValues.setTimestamp(Instant.now().getEpochSecond());
        service.save(sensorValues);
    }

    private int getAverage(List<Integer> values) {
        int sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i);
        }
        int avg = sum/ values.size();
        return avg;
    }

}
