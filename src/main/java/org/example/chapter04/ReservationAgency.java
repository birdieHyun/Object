//package org.example.chapter04;
//
//import static org.example.chapter04.MovieType.AMOUNT_DISCOUNT;
//import static org.example.chapter04.MovieType.PERCENT_DISCOUNT;
//
//public class ReservationAgency {
//    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
//        Movie movie = screening.getMovie();
//
//        boolean discountable = false;
//        for (DiscountCondition condition : movie.getDiscountCondition()) {
//            if (condition.getType() == DiscountConditionType.PERIOD) {
//                discountable = screening.getLocalDateTime().getDayOfWeek().equals(condition.getDayOfWeek()) &&
//                        condition.getStartTime().compareTo(screening.getLocalDateTime().toLocalTime()) <= 0 &&
//                        condition.getEndTime().compareTo(screening.getLocalDateTime().toLocalTime()) >= 0;
//            } else {
//                discountable = condition.getSequence() == screening.getSequence();
//            }
//
//            if (discountable) {
//                break;
//            }
//        }
//
//        Money fee;
//        if (discountable) {
//            Money discountAmount = Money.ZERO;
//            switch (movie.getMovieType()) {
//                case AMOUNT_DISCOUNT:
//                    discountAmount = movie.getDiscountPercent();
//                    break;
//                case PERCENT_DISCOUNT:
//                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
//                    break;
//                case NONE.DISCOUNT:
//                    discountAmount = Money.ZERO;
//                    break;
//            }
//
//        }
//    }
