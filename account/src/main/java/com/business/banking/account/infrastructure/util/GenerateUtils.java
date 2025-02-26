package com.business.banking.account.infrastructure.util;

import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class GenerateUtils {
    public String generator(){
        return IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf((int) (Math.random() * 100)))
                .collect(Collectors.joining(""));
    }
}
