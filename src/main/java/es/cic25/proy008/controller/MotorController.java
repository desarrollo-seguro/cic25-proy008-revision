package es.cic25.proy008.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic25.proy008.exception.MotorException;
import es.cic25.proy008.model.Motor;
import es.cic25.proy008.service.MotorService;

@RestController
@RequestMapping("/motores")
public class MotorController {

    @Autowired
    private MotorService motorService;

    @PostMapping
    public long create(@RequestBody Motor motor) {
        return motorService.create(motor);
    }

    @GetMapping("/{id}")
    public Motor get(@PathVariable long id) {
        Motor motor = motorService.get(id);
        if (motor == null) {
            throw new MotorException(id);
        }
        return motor;
    }

    @GetMapping
    public List<Motor> get() {
        return motorService.get();
    }

    @PutMapping
    public void update(@RequestBody Motor motor) {
        motorService.update(motor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        motorService.delete(id);
    }
}
