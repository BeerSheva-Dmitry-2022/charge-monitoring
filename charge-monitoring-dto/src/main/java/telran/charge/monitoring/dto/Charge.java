package telran.charge.monitoring.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder @ToString
public class Charge {
    int idSensor;
    ChargeStatus status;
    int power;
    long timestamp;
}
