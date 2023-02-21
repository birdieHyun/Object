//package org.example.chapter02.movie;
//
//import org.example.chapter04.Customer;
//
//import java.time.LocalDateTime;
//
//public class Screening {
//    private Movie movie;
//    private int sequence;
//    private LocalDateTime whenScreened;
//
//    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
//        this.movie = movie;
//        this.sequence = sequence;
//        this.whenScreened = whenScreened;
//    }
//
//    public LocalDateTime getStartTime() {
//        return whenScreened;
//    }
//
//    public boolean isSequence(int sequence) {
//        return this.sequence == sequence;
//    }
//
//    public Money getMovieFee() {
//        return movie.getFee();
//    }
//
//    public Reservation reserve(Customer customer, int audiencCount) {
//        return new Reservation(customer, this, calculateFee(audiencCount), audiencCount);
//    }
//
//    private Money calculateFee(int audienceCount) {
//        return movie.calculateMovieFee(this).times(audienceCount);
//    }
//}
