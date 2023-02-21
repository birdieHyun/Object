package org.example.chapter04;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie {
    private String title;
    private Duration RunningTime;
    private Money fee;
    private List<DiscountCondition> discountCondition;

    private MovieType movieType;
    private Money money;
    private double discountPercent;

}
