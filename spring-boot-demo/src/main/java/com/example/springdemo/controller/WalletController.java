package com.example.springdemo.controller;

import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.dto.WalletDTO;
import com.example.springdemo.entities.Wallet;
import com.example.springdemo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity createWallet(@RequestBody WalletDTO walletDTO) {
        walletService.createWallet(walletDTO);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/get/{username}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable("username") String username) {
        return new ResponseEntity<>(walletService.getWalletByUsername(username), HttpStatus.OK);
    }

    @PatchMapping("/update/{amount}/{username}")
    public ResponseEntity updateWallet(@PathVariable("amount")  @RequestBody Integer amount, @PathVariable("username")  @RequestBody String username) {
        walletService.updateWallet(amount, username);
        return new ResponseEntity(HttpStatus.OK);
    }
}
