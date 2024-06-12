package org.example.DiplomMot.Repository;

import org.example.DiplomMot.Model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

//репозиторий на основе Jpa-репозитория
@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Motorcycle, String> {}
