package org.example.DiplomMot;

import org.example.DiplomMot.Model.Motorcycle;
import org.example.DiplomMot.Repository.Repository;
import org.example.DiplomMot.Service.Service;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private Service service;

    @Mock
    private Repository repository;

    @Test
    public void testGetAllMotorcycles() {
        Motorcycle motorcycle = new Motorcycle("Honda", "CBR", 1990000.0, "Sport", "image.png");
        List<Motorcycle> motorcycles = Arrays.asList(motorcycle);

        when(repository.findAll()).thenReturn(motorcycles);

        List<Motorcycle> result = service.getAllMotorcycles();
        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getBrand());
    }

    @Test
    public void testCreateMotorcycle() {
        Motorcycle motorcycle = new Motorcycle("Yamaha", "R1", 2000000.0, "Sport", "image.png");
        when(repository.save(motorcycle)).thenReturn(motorcycle);

        Motorcycle result = service.createMotorcycle(motorcycle);
        assertNotNull(result);
        assertEquals("Yamaha", result.getBrand());
    }

    @Test
    public void testGetMotorcycleByBrand() {
        Motorcycle motorcycle = new Motorcycle("Ducati", "Panigale", 2500000.0, "Sport", "image.png");
        when(repository.findById("Ducati")).thenReturn(Optional.of(motorcycle));

        Motorcycle result = service.getMotorcycleByBrand("Ducati");
        assertNotNull(result);
        assertEquals("Ducati", result.getBrand());
    }

    @Test
    public void testSellMotorcycle() {
        String brand = "Honda";
        doNothing().when(repository).deleteById(brand);
        service.sellMotorcycle(brand);
        verify(repository, times(1)).deleteById(brand);
    }
}

