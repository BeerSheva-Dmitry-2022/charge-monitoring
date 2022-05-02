package telran.charge.monitoring.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import telran.charge.monitoring.dto.Charge;

import java.util.ArrayList;
import java.util.List;

@RedisHash
@NoArgsConstructor
@Getter @Setter
@ToString
public class SensorValues {
    @Id
    int id;
    List<Integer> values;
    long timestamp;

    public SensorValues(int id) {
        this.id = id;
        values = new ArrayList<>();
    }
}
