package com.pichincha.business.banking.services.service.impl;

import com.pichincha.business.banking.services.repository.impl.AccountRepository;
import com.pichincha.business.banking.services.service.entity.Account;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final AccountRepository accountRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        Account account = new Account();
        account.setBalance(0.0);
        account.setType("Ahorro");
        account.setNumber(generator());
        account.setState(true);
        account.setIdClient(Integer.parseInt(message.toString()));
        accountRepository.save(account);
        System.out.println("Received Message: " + message.toString());
    }

    public String generator(){
        return IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf((int) (Math.random() * 100)))
                .collect(Collectors.joining(""));
    }
}
