package telran.charge.monitoring.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Sensor {
    int id;
    int power;
    long timestamp;
}
