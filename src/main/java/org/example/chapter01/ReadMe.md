# chapter1 

## 01. 패러다임의 시대 

언어들도 프로그래밍 패러다임을 따라서 만들었다.  
자바가 객체지향 패러다임을 채택한 것 처럼  
LISP 는 함수형 프로그래밍  
C 는 절차지향 패러다임  

## Chapter 01 객체, 설계 
이론이 먼저일까, 실무가 먼저일까?  
대부분의 사람은 이론이 먼저라고 하지만, 로버트 글래스는 실무가 먼저라고 주장했다.  
글래스의 주장을 한마디로 정의하자면,  
**실무가 어느정도 발전하고 난 이후에, 실무를 관찰한 결과를 바탕으로 이론이 발전한다.** 고 하였다.  
> 코딩도 마찬가지라고 생각한다. 아무리 책과 강의를 보면서 공부를 해도, 내가 직접 프로그래밍을 하지 않으면 실력이 늘지 않는것 처럼  

추상적인 개념과 이론은 훌륭한 코드를 작성하는데 필요한 도구일 뿐이다.  

마틴 - 클린 소프트웨어 : 애자일 원칙과 패턴, 그리고 실천 방법에서 소프트웨어 모듈이 가져야 하는 세 가지 기능에 관해 설명한다.  
1. 모듈은 제대로 실행되어야 한다.  
2. 모듈은 변경이 용이해야 한다.  
3. 모듈은 이해하기 쉬워야 한다.   
  
지금까지 구현한 코드는 제대로 실행되기는 하지만, 변경에 용이하지 못하고, 이해하기 쉬운 코드는 아니다. 

Theater 클래스의 enter 메서드가 수행하는 일을 말로 풀어보면 
```java
class Theater {
    public void enter(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        }else {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```
소극장은 관람객의 가방을 열어 그 안에 초대장이 들어 있는지 살표본다. 가방 안에 초대장이 들어 있으면 판매원은 매표소에 보관대 있는 티켓을 관람객의 가방 안으로 옮긴다.  
가방 안에 초대장이 들어 있지 않다면 관람객의 가방에서 티켓 금액만큼의 현금을 꺼내 매표소에 적립한 후, 매표소에 보관돼 있는 티켓을 관람객의 가방 안으로 옮긴다.  

> 관람객과 판매원이 주체적으로 행동하지 못하고, 소극장에 의존하는 점이 문제!   

> 처음 봤을 땐, Theater 클래스 안에 객체들을 불렀기 때문에, MVC의 컨트롤러가 하는 것 처럼 동작한다고 생각했는데...
  
문제점 : **관람객과 판매원이 소극장의 통제를 받는 수동적인 존재**가 되었다.  
- 관람객 : 소극장이라는 제 3자가 허락도 없이 자신의 가방을 본다면? 돈을 확인하고 마음대로 티켓을 넣어버린다면? 
- 판매원 : 내가 판매원이라면 내가 관람객에게 돈을 받고 티켓을 주는 것이 아닌, 난 가만히 앉아있고, 소극장이 마음대로 돈과 티켓을 관리하게 된다. 
  
이해가능한 코드 : **그 동작이 우리의 예상을 크게 벗어나지 않는 코드**  
하지만 위의 코드는 소극장이 마음대로 관람객과 판매원을 통제하고 있으므로 이해가능한 코드라고 보기 힘들다. 우리의 예상과 다르기 때문이다.  
또한 코드를 한번에 이해하기 힘든 이유는, enter 메서드를 이해하기 위해, 계속해서 객체들의 동작에 몇번씩 들어가야 하기 때문이다.  
가장 심각한 문제는 **Audience 와 TicketSeller 를 변경할 때, Theater 도 함께 변경해야 한다.**  

#### 변경에 취약한 코드  
가장 큰 문제는 변경에 취약하다는 점이다.  
만약 손님이 가방을 들고있지 않다면? 현금이 아닌 신용카드로 결제한다면?  
이처럼 다양한 상황에 대비하고 있지 않다.  
다른 클래스가 Audience 에 대해 많이 알고 있을수록 더 많은 코드를 변경해야 한다.  
이것이 객체 사이의 **의존성** 에 관련된 문제다.  
그렇다고 객체들의 의존성을 제거한다? -> 객체지향 설계는 객체들간의 협력이 중요함, 객체들의 의존성은 필요  

### 설계 개선하기  
현재까지 설계한 코드는 기능은 수행하지만, 이해하기 어렵고 변경에 취약하다.  
이 문제를 해결하기 위해서는 Theater 이 Audience 와 TicketSeller 의 정보를 제한하면 된다.  
다시말해 관람객과 판매원을 **자율적인 존재**로 만들면 되는 것이다. 

### 자율성을 높이자  
Audience 와 TicketSeller 가 자율적인 존재가 되도록 설계를 변경해야 한다. 

첫 번째 단계는, Theater 의 enter 메서드에서 TicketOffice 에 접근하는 모든 코드를 TicketSeller 내부로 숨기는 것이다. 

TicketSeller 에서 getTicketOffice 메서드가 제거되었다.  

개념적이나 물리적으로 객체 내부의 세부적인 사항을 감추는 것을 **캡슐화**라고 한다.  
캡슐화의 목적은 변경하기 쉬운 객체를 만드는 것이다. 

이제 Theater 클래스는 sellTo 를 호출하는 간단한 코드로 바뀌었다. 
```java
public class Theater {
    private TicketSeller ticketSeller;
    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }
    public void enter(Audience audience) {
        ticketSeller.sellTo(audience);
    }
}
```
이제 Theater 클래스에서 ticketOffice 로 접근하지 못한다.  
즉 Theater 는 ticketOffice 에 ticketSeller 가 있는지 모르게 된다.  
Theater 는 단지 ticketSeller 가 sellTo 메시지를 이해하고 응답할 수 있다는 사실만 알게 되었다.

```java
public class TicketSeller {
    private TicketOffice ticketOffice;
    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }
    public void sellTo(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().setTicket(ticket);
        }else {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketOffice.plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```
TicketSeller 에서 getTicketOffice 메서드가 제거되었다!  
필드에 있는 ticketOffice 가 private 이고, get 메서드가 제거되었기 때문에, 이제 외부에서 ticketOffice 로 접근할 수 없게 되었다.  
드디어 TicketSeller 가 주체적으로 티켓을 꺼내거나 판매하고 요금을 관리할 수 있게 되었다. 

이제 Audience 의 캡슐화를 개선해보자.  
TicketSeller 는 Audience 의 getBag 메서드를 호출해서 Audience 내부의 Bag 인스턴스에 직접 접근한다.  
Bag 에 접근하는 객체가 Theater 에서 TicketSeller 로 바뀐것 뿐이지, Audience 는 여전히 자율적인 존재가 아니다.  
  
Bag 에 접근하는 모든 로직을 Audience 내부로 감추기 위해 Audience 에 buy 메서드를 추가하고 TicketSeller 의 sellTo 메서드에서  getBag 메서드에 접근하는 부분을 buy 메서드로 옮긴다.  

```java
public class Audience {
    private Bag bag;
    public Audience(Bag bag) {
        this.bag = bag;
    }
    public Long buy(Ticket ticket) {
        if (bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        } else {
            bag.setTicket(ticket);
            bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        } 
    }
}
```
변경된 코드에서 Audience 는 자신의 가방 안에 초대장이 들어있는지 스스로 확인한다.  
외부의 제 3자가 가방을 열어보지 못하도록 getBag 메서드를 삭제했다.  
결과적으로 Bag 의 존재를 내부로 캡슐화 할 수 있게 되었다.  
  
이제 TicketSeller 가 Audience 의 인터페이스에만 의존하도록 수정하자. TicketSeller 가 buy 메서드를 호출하도록 코드를 변경하면 된다.  
  
```java
public class TicketSeller {

    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }
}
```  
코드를 수정한 결과 TicketSeller 와 Audience 사이의 결합도가 낮아졌다.  
또한 내부 구현이 캡슐화 되었으므로, Audience 의 구현을 수정해도 TicketSeller 에 영향을 주지 않는다.  

모든 수정이 끝나고 개선된 점은 Audience 와 TicketSeller 가 주체적으로 행동한다는 것이다.  
즉 자율적인 존재가 된것이다. 

### 무엇이 개선됐는가? 
기존 코드는 관람객을 입장시키는 기능을 오류없이 작동하기만 했다.  
수정 후 코드는 객체가 주체적으로 행동하고 있다. 

즉 이제는 Audience 나 TicketSeller 의 코드를 수정해도 Theater 의 코드를 수정할 필요가 없어졌다.  

### 어떻게 한 것인가? 
간단하다!  
판매자가 티켓을 판매하기 위해 TicketOffice 를 사용하는 모든 부분을 TicketSeller 내부로 옮기고,  
관람객이 티켓을 구매하기 위해 Bag를 사용하는 모든 부분을 Audience 내부로 옮긴 것이다.  
다시 말해, 자기 자신의 문제를 스스로 해결할 수 있도록 코드를 변경한 것이다.   
  
외부의 객체가 자신에게 접근해서 로직을 침범하지 않도록 해야한다. 즉 캡슐화를 해야한다. 

### 캡슐화와 응집도 
핵심은 객체 내부의 상태를 캡슐화 하고, 객체 간에 오직 메시지를 통해서만 상호작용 하도록 만드는 것이다.  
> JK 수업 중 필드를 줄이고, 필드를 사용해야 할 경우, 필드를 가지고 있는 객체 내에서 로직을 처리하도록 한다. 생각하기
  
객체들 간에는 내부를 알지 못하고, 메시지를 통해서 상호작용 하도록 해야한다.  
  
밀접하게 연관된 작업만을 수행하고, 연관성 없는 작업은 다른 객체에게 위임하는 객체를 가리켜 **응집도(cohension)**가 높다고 말한다.  
자신의 데이터를 스스로 처리하는 자율적인 객체를 만들면 결합도를 낮출 수 있을 뿐더러 응집도를 높일 수 있다.  
  
객체의 응집도를 높이기 위해서는 객체 스스로 자신의 데이터를 책임져야 한다.  
객체는 자신의 데이터를 스스로 처리하도록 해야만, 객체의 응집도를 높일 수 있다.  
외부 간섭을 최대한 배제하고, 메시지를 통해서만 협력하는 자율적인 객체들의 공동체를 만드는 것이 훌륭한 객체지향 설계를 얻을 수 있다.
  
### 절차지향과 객체지향 
기존의 코드는, Theater 의 enter 메서드 안에서 Audience 와 TicketSeller 로 부터 Bag 와 TicketOffice 를 가져와 관람객을 입장시키는 절차를 구현했다.  
이 관점에서 Theater 의 enter 메서드는 **프로세스(Process)**이며, Audience, TicketSeller, Bag, TicketOffice 는 **데이터(Data)**다.  
이처럼 프로세스와 데이터를 별도의 모듈에 위치시키는 방식을 **절차적 프로그래밍(Procedural Programming)**이라고 부른다.   
기존의 코드는 절차적 프로그래밍 방식으로 작성된 코드의 전형적인 의존성 구조를 보여준다.  
  
절차적 프로그래밍의 큰 문제는, 변경에 취약하다는 점이다. 즉 데이터 변경으로 인한 영향을 지역적으로 고립시키기 어렵다.  
  
객체지향 프로그래밍은 데이터와 프로세스가 동일한 모듈 내부에 위치하도록 프로그래밍을 한다.   
   
### 책임의 이동 