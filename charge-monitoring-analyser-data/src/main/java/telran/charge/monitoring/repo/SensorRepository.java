package telran.charge.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.charge.monitoring.entity.SensorValues;

public interface SensorRepository extends CrudRepository<SensorValues, Integer> {
}
