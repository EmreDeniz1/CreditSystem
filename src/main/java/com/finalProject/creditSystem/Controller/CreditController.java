package com.finalProject.creditSystem.Controller;

import com.finalProject.creditSystem.Entities.Credit;
import com.finalProject.creditSystem.Exception.CustomJwtException;
import com.finalProject.creditSystem.Service.CreditService;
import com.finalProject.creditSystem.Service.UserService;
import com.finalProject.creditSystem.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    private CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }


    // When user requests a credit, does calculation and saves request to database.
    @GetMapping(path = "/request-credit")
    public ResponseEntity<?> requestCredit(@RequestParam String username){
        if (!username.equals(getPrincipal().getUsername()))
        {
            throw new CustomJwtException("Users can delete only their own accounts", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(creditService.requestCreditLimit(username));
    }

    // User can check credit limit amount after request.
    @GetMapping(path = "/check-credit-request")
    public List<Credit> getApprovedCreditLimit(@RequestParam String username){
        if (!username.equals(getPrincipal().getUsername()))
        {
            throw new CustomJwtException("Users can delete only their own accounts", HttpStatus.NOT_ACCEPTABLE);
        }
        return creditService.getApprovedCreditLimits(username);
    }

    private UserPrincipal getPrincipal () {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) auth.getPrincipal();
    }
}
