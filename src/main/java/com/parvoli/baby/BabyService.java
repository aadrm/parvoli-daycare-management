package com.parvoli.baby;

import com.parvoli.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BabyService {

    private final BabyRepository babyRepository;

    @Autowired
    public BabyService(BabyRepository babyRepository) {
        this.babyRepository = babyRepository;
    }

    public List<Baby> findAllBabies() {
        return babyRepository.findAll();
    }


    public boolean babyExists(String firstName, String lastName, LocalDate dob) {
        return babyRepository.existsByFirstNameAndLastNameAndDob(firstName, lastName, dob);
    }

    public void addBaby(Baby baby) {
        boolean babyExists = babyRepository
                .existsByFirstNameAndLastNameAndDob(
                        baby.getFirstName(),
                        baby.getLastName(),
                        baby.getDob()
                );

        if (babyExists) {
            throw new BadRequestException(
                    "Baby: " + baby.getFirstName() + " born " + baby.getDob() + " exists in the database"
            );
        } else {
            babyRepository.save(baby);
        }

    }

}
