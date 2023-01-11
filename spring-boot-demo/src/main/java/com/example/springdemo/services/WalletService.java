package com.example.springdemo.services;

import com.example.springdemo.dto.PostDTO;
import com.example.springdemo.dto.WalletDTO;
import com.example.springdemo.entities.UserSimple;
import com.example.springdemo.entities.Wallet;
import com.example.springdemo.errorhandler.PostNotFoundException;
import com.example.springdemo.repositories.UserSimpleRepository;
import com.example.springdemo.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final AuthService authService;
    private final UserSimpleRepository userSimpleRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository, AuthService authService, UserSimpleRepository userSimpleRepository) {
        this.walletRepository = walletRepository;
        this.authService = authService;
        this.userSimpleRepository = userSimpleRepository;
    }

    @Transactional
    public void createWallet(WalletDTO walletDTO) {
        Wallet wallet = mapFromDtoToWallet(walletDTO);
        walletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    public WalletDTO getWalletByUsername(String username) {
        Wallet wallet = walletRepository.findByUserSimple(username);
        return mapFromWalletToDto(wallet);

    }
    @Transactional
    public void updateWallet(Integer amount,String username) {
        walletRepository.updateAmount(amount,username);
    }

    @Transactional
    public WalletDTO readSingleWallet(Long id) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromWalletToDto(wallet);
    }

    private Wallet mapFromDtoToWallet(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();
        wallet.setId(walletDTO.getId());
        wallet.setDate_wallet(Instant.now());
        wallet.setUsername(walletDTO.getUsername());
        wallet.setAmount(walletDTO.getAmount());
        wallet.setCategory(walletDTO.getCategory());
        return wallet;
    }


    private WalletDTO mapFromWalletToDto(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setDate_wallet(Instant.now());
        walletDTO.setUsername(wallet.getUsername());
        walletDTO.setAmount(wallet.getAmount());
        walletDTO.setCategory(wallet.getCategory());
        return walletDTO;
    }

}
