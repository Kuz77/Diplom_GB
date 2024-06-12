package org.example.DiplomMot.Service;

import lombok.AllArgsConstructor;
import org.example.DiplomMot.Aspect.TrackUserAction;
import org.example.DiplomMot.Repository.Repository;
import org.example.DiplomMot.Model.Motorcycle;

import java.util.List;

// Сервис - методы бизнес-логики
@org.springframework.stereotype.Service
@AllArgsConstructor
public class Service {
    private final Repository repository;

    // Просмотр всех мотоциклов
    @TrackUserAction
    public List<Motorcycle> getAllMotorcycles() {
        return repository.findAll();
    }

    // Получение мотоцикла по бренду
    @TrackUserAction
    public Motorcycle getMotorcycleByBrand(String brand) {
        return repository.findById(brand).orElse(null);
    }

    // Добавление мотоцикла
    @TrackUserAction
    public Motorcycle createMotorcycle(Motorcycle motorcycle) {
        return repository.save(motorcycle);
    }

    // Продажа мотоцикла
    @TrackUserAction
    public void sellMotorcycle(String brand) {
        repository.deleteById(brand);
    }
}
