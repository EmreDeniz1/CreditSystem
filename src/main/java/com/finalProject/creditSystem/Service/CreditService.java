package com.finalProject.creditSystem.Service;

import com.finalProject.creditSystem.Entities.Credit;
import com.finalProject.creditSystem.Entities.User;
import com.finalProject.creditSystem.Repository.CreditRepository;
import com.finalProject.creditSystem.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Slf4j
@Service
public class CreditService {

    private UserRepository userRepository;

    private static final int CREDIT_MULTIPLIER_FACTOR = 4;

    private final CreditRepository creditLimitRepository;

    private final CreditLimitCalculationService creditLimitCalculationService;

    private final UserService userService;

    public CreditService(UserRepository userRepository, CreditRepository creditLimitRepository, CreditLimitCalculationService creditLimitCalculationService, UserService userService) {
        this.userRepository = userRepository;
        this.creditLimitRepository = creditLimitRepository;
        this.creditLimitCalculationService = creditLimitCalculationService;
        this.userService = userService;
    }

    double calculateCreditLimit(Double income, Integer creditScore){
        double creditLimit = 0.;
        if (creditScore>= 1000) {
            creditLimit = income * CREDIT_MULTIPLIER_FACTOR;
        }else if (creditScore < 1000 && creditScore > 500 && income> 5000){
            creditLimit = 20000;
        }else if (creditScore < 1000 && creditScore > 500 & income < 5000){
            creditLimit = 10000;
        }
        return creditLimit;
    }

    public boolean requestCreditLimit(String username){
        List<Credit> existingCreditLimits = creditLimitRepository.findAllByUsername(username);
        if (!CollectionUtils.isEmpty(existingCreditLimits)){
            log.info("Denied because existing requests.");
            return false;
        }
        User user = userService.getUserByUsername(username);
        Integer creditScore = creditLimitCalculationService.getRandomCreditScore();
        // calculate credit limit
        double calculateCreditLimit = calculateCreditLimit(user.getIncome(), creditScore);
        if (calculateCreditLimit <= 0){
            log.info("Credit request is rejected due to credit score:" + creditScore);
            Credit credit = new Credit(username, creditScore, calculateCreditLimit, false);
            creditLimitRepository.save(credit);
            return false;
        }
        // creates credit limit object
        Credit credit = new Credit(username, creditScore, calculateCreditLimit, true);

        // save credit limit to DB if its approved
        creditLimitRepository.save(credit);
        log.info("Credit request is approved! Credit Limit is :" + calculateCreditLimit +
                "\nIncome is: " + user.getIncome() + "\nCredit score is: " + creditScore);
        // send message to RabbitMQ on approval


        return true;
    }

    public List<Credit> getApprovedCreditLimits(String username) {
        return creditLimitRepository.findAllByUsername(username);

    }
}
