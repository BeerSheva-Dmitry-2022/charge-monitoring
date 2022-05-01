package telran.charge.monitoring;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.charge.monitoring.dto.Sensor;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@SpringBootApplication
@Log4j2
public class SupplierDataAppl {

    final int ID = 1;

    public static void main(String[] args) {
        SpringApplication.run(SupplierDataAppl.class, args);
    }

    @Bean
    Supplier<Sensor> chargeSupplier(){
        return this::supplierProcessing;
    }

    private Sensor supplierProcessing() {
        int id = getRandomNumber(1, 3);
        int value = getRandomNumber(0, 100) < 50 ? 0 : getRandomNumber(1, 50);
        Sensor res = new Sensor(ID, value, Instant.now().getEpochSecond());
        log.debug("Supplier data {} to topic", res);
        return res;

    }

    private int getRandomNumber(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }



}
