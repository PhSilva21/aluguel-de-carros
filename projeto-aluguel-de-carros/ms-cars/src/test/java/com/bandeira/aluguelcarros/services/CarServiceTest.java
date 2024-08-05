package com.bandeira.aluguelcarros.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import com.bandeira.aluguelcarros.dto.UpdateCarDTO;
import com.bandeira.aluguelcarros.exceptions.CarNotFoundException;
import com.bandeira.aluguelcarros.model.CarCategory;
import com.bandeira.aluguelcarros.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bandeira.aluguelcarros.dto.CarRequest;
import com.bandeira.aluguelcarros.model.Car;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    CarService carService;

    @Mock
    CarRepository carRepository;


    @Captor
    ArgumentCaptor<Car> carArgumentCapture;


    Car car = new Car(
            "Palio",
            new BigDecimal("120.00"),
            true,
            CarCategory.SEDAN
    );


    CarRequest carRequest = new CarRequest(
            "Civic",
            new BigDecimal("120.00"),
            true,
            CarCategory.HATCH);


    UpdateCarDTO updateCarDTO = new UpdateCarDTO(
            123L,
            "Civic random",
            new BigDecimal("165.00"),
            false,
            CarCategory.MINIVAN

    );

    @Nested
    class createCar{

        @Test
        @DisplayName("Successfully creating car")
        void successfullyCreatingCar() {
            doReturn(car)
                    .when(carRepository)
                    .save(carArgumentCapture.capture());

            var response = carService.createCar(carRequest);

            var carCaptured = carArgumentCapture.getValue();

            assertEquals(carRequest.name(), carCaptured.getName());
            assertEquals(carRequest.dailyPrice(), carCaptured.getDailyPrice());
            assertEquals(carRequest.available(), carCaptured.getAvailable());
            assertEquals(carRequest.category(), carCaptured.getCategory());
        }
    }

    @Nested
    class findAll {

        @Test@DisplayName("returning list of users successfully")
        void returningListOfUsersSuccessfully() {
            var carList = List.of(car);
            doReturn(carList).when(carRepository).findAll();


            var response = carService.findAll();

            assertEquals(carList.size(), response.size());
        }
    }

    @Nested
    class findById{

        @Test
        @DisplayName("Returning car by id successfully")
        void ReturningCarByIdSuccessfully() {
            doReturn(Optional.of(car))
                    .when(carRepository)
                    .findById(car.getId());

            var response = carService.findById(car.getId());

            assertNotNull(response);

        }

        @Test
        @DisplayName("Return exception car not found")
        void returnExceptionCarNotFound() {
            doReturn(Optional.empty())
                    .when(carRepository)
                    .findById(car.getId());

            assertThrows(CarNotFoundException.class,
                    () -> carService.findById(car.getId()));
        }
    }

    @Nested
    class updateSuccessfully {

        @Test
        @DisplayName("Updating car successfully")
        void updatingCarSuccessfully() {
            doReturn(Optional.of(car))
                    .when(carRepository)
                    .findById(updateCarDTO.id());
            doReturn(car)
                    .when(carRepository)
                    .save(carArgumentCapture.capture());

            carService.update(updateCarDTO);

            var carCaptured = carArgumentCapture.getValue();

            assertEquals(car.getId(), carCaptured.getId());

            assertEquals(updateCarDTO.name(), carCaptured.getName());
            assertEquals(updateCarDTO.dailyPrice(), carCaptured.getDailyPrice());
            assertEquals(updateCarDTO.available(), carCaptured.getAvailable());
            assertEquals(updateCarDTO.category(), carCaptured.getCategory());

            verify(carRepository, times(1))
                    .findById(updateCarDTO.id());
            verify(carRepository, times(1))
                    .save(car);
        }

        @Test
        @DisplayName("Returning car not found error")
        void returningCarNotFoundError() {
            doReturn(Optional.empty())
                    .when(carRepository)
                    .findById(updateCarDTO.id());

            assertThrows(CarNotFoundException.class,
                    () -> carService.update(updateCarDTO));
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Deleting car successfully")
        void deletingCarSuccessfully() {
            doReturn(Optional.of(car))
                    .when(carRepository)
                    .findById(car.getId());
            doNothing()
                    .when(carRepository)
                    .deleteById(car.getId());

            carService.deleteById(car.getId());

            verify(carRepository, times(1))
                    .findById(car.getId());
            verify(carRepository, times(1))
                    .deleteById(car.getId());
        }

        @Test
        @DisplayName("Returning car not found error")
        void returningCarNotFoundError() {
            doReturn(Optional.empty())
                    .when(carRepository)
                    .findById(car.getId());

            assertThrows(CarNotFoundException.class,
                    () -> carService.deleteById(car.getId()));

            verify(carRepository, times(1))
                    .findById(car.getId());
            verify(carRepository, times(0))
                    .deleteById(car.getId());
        }
    }

}



