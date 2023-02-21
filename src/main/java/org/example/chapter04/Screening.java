package org.example.chapter04;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Screening {

    private Movie movie;
    private int sequence;
    private LocalDateTime localDateTime;

}
