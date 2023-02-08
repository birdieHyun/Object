# Chapter02 객체지향 프로그래밍 
  
기술 서적에서 가장 어려운 점이 적당한 수준의 난이도와 복잡도를 유지하면서도 이해하기 쉬운 예제를 선택하는 것이다.  
지금 나오는 예제가 쉽더라도 충분히 복잡하고 난이도 있는 예제라고 생각하면서 읽어보기!  
  
## 01. 영화 예매 시스템   
### 요구사항 살펴보기  
이번 예제는 온라인 영화 예매 시스템이다.  
앞으로의 설명을 위해 '영화'와 '상영'을 구분한다.  
영화 : 영화에 대한 기본 정보 (제목, 상영시간, 가격정보)를 가리킨다.  
상영 : 실제로 관객들이 영화를 관람하는 사건을 표현한다.  
상영 일자, 시간, 순번 등을 가리키기 위해 '상영'이라는 용어를 사용할 것이다.   
  
두 용어의 차이가 중요한 이유는, 사용자가 실제로 예매하는 대상은 영화가 아니라 상영이기 때문이다.  
사람들은 영화를 예매한다고 하지만, 실제로는 특정 시간에 상영되는 영화를 관람할 수 있는 권리를 구매하는 것이다.  
  
특정 조건에서 영화 할인 가능  
- 할인 조건 
- 할인 정책
  
두 가지가 중요하다.   
  
할인을 적용하기 위해서는 할인 조건과 할인 정책을 함께 조합해서 사용한다.    
  
  
## 02. 객체지향 프로그래밍을 향해   
### 협력 객체 클래스  
대부분의 사람들은 어떤 **클래스**가 필요한지 결정하고, 클래스에 어떤 속성과 메서드가 필요한지 고민한다.  
  
안타깝지만 이것은 객체지향의 본질과는 거리가 멀다. 객체지향이란 말 그대로 객체를 지향하는 것이다.  
  
진정한 객체지향 패러다임으로의 전환은 클래스가 아닌 객체에 초점을 맞출 때에만 얻을 수 있다. 다음 두 가지에 집중해보자.  
> 1. 어떤 클래스가 필요한지를 고민하기 전에 어떤 객체들이 필요한지 고민하라.  
> 클래스는 공통적인 상태와 행동을 공유하는 객체들을 추상화한 것이다.  
> 따라서 클래스의 윤곽을 잡기 위해서는 어떤 객체들이 어떤 상태와 행동을 가지는지를 먼저 결정해야 한다.  
> 객체를 중심에 두는 접근 방법은 설계를 단순하고 깔끔하게 만든다.  
  
> 2. 객체를 독립적인 존재가 아니라, 기능을 구현하기 위해 협력하는 공동체의 일원으로 봐야 한다.  
> 객체는 홀로 존재하는것이 아니다. 다른 객체에게 도움을 주거나, 의존하면서 살아가는 협력적인 존재다.  
> 객체를 협력하는 공동체의 일원으로 바라보는 것은 설계를 유연하고 확장 가능하게 만든다.  
> 객체지향적으로 생각하고 싶다면, 객체를 고립된 존재로 바라보지 말고, 협력에 참여하는 협력자로 바라봐야 한다.  
> 객체들의 모양과 윤곽이 잡히면 공통된 특성과 상태를 가진 객체들을 타입으로 분류하고 이 타입을 기반으로 클래스를 구현해야 한다.  
  
  
### 도메인의 구조를 따르는 프로그램 구조  
이 시점에 **도메인(domain)** 이라는 용어를 살펴보는 것이 도움이 될것이다.   
  
영화 예매 시스템의 목적은 영화를 좀 더 쉽고 빠르게 예매하려는 사용자의 문제를 해결하는 것이다.  
이처럼 문제를 해결하기 위해 사용자가 프로그램을 사용하는 분야를 **도메인** 이라고 한다.   
<img width="681" alt="스크린샷 2023-02-04 오후 7 06 14" src="https://user-images.githubusercontent.com/115435784/216761213-2dde3411-684c-4b94-a5b9-dab1258f911f.png">
  
영화는 여러번 상영될 수 있고, 상영은 여러번 예매될 수 있다는 것을 알 수 있다...? (이 그림보고 어떻게 아는거지?)
  
일반적으로 클래스의 이름은 대응되는 도메인 개념의 이름과 동일하거나 적어도 유사하게 지어야 한다.  
<img width="670" alt="스크린샷 2023-02-04 오후 7 11 22" src="https://user-images.githubusercontent.com/115435784/216761414-5b6bbf93-27a6-47c3-945e-f67c32dc445f.png">
  
### 클래스 구현하기    
```java
package org.example.chapter02.movie;

import java.time.LocalDateTime;

public class Screening {
    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    public Movey getMovieFee() {
        return movie.getFee();
    }
}
```  
여기서 주목할 점은 필드의 접근제어자는 private 이고, 메서드의 접근제어자는 public 이라는 것이다.  
클래스를 구현할 때 중요한 것은 클래스의 경계를 구분짓는 것이다.   
클래스의 경계는 내부와 외부로 구분되고, 훌륭한 클래스를 설계하기 위해서는 어느 부분을 외부에 공개할 것인지, 어떤 부분을 감출 것인지 결정하는 것이다.  
  
클래스의 내부와 외부의 구분해야 하는 이유는? 
> 경계의 명확성이 객체의 자율성을 보장하기 때문이다.  
> 더 중요한 이유로 프로그래머에게 구현의 자유를 제공하기 때문이다.  
  
### 자율적인 객체  
- 객체는 **상태(state)와 행동(behavior)** 을 함께 가지는 복합적인 존재이다.  
- 객체가 스스로 판단하고 행동하는 **자율적인 존재** 이다.  
위 두 가지는 깊이 연관되어 있다.  
   
객체지향 이전 패러다임에서는 데이터와 기능이라는 독립적인 존재를 서로 엮어 프로그램을 구성했다.  
이와 달리 객체지향은 객체라는 단위 안에 데이터와 기능을 한 덩어리로 묶었다.  
이처럼 데이터와 기능을 한 덩어리 내부로 함께 묶는 것을 **캡슐화** 라고 한다.   
  
**인터페이스와 구현의 분리**  
역할을 인터페이스로 나타내고, 
행동을 인터페이스를 구현한 클래스로 나타낸다.  
이는 훌륭한 객체지향 프로그램을 만들기 위해 따라야 하는 핵심 원칙이다.  
  
### 프로그래머의 자유  
프로그래머의 역할 
- **클래스 작성자(class creator)**
- **클라이언트 프로그래머(client programmer)**
  
클래스 작성자는 새로운 데이터 타입을 프로그램에 추가하고, 클라이언트 프로그래머는 클래스 작성자가 추가한 데이터 타입을 사용한다.  
  
클래스 작성자는 클라이언트 프로그래머에게 필요한 부분만 공개하고, 나머지는 꽁꽁 숨기는 **캡슐화**를 해야 한다.  
이 덕분에 클라이언트 프로그래머는 내부 영향을 걱정하지 않고 내부 구현을 마음대로 변경할 수 있다.  
이를 **구현 은닉(implementation hiding)**이라고 한다.  
  
### 협력하는 객체들의 공동체  
```java
public class Screening {
    public Reservation reserve(Customer customer, int audiencCount) {
        return new Reservation(customer, this, calculateFee(audiencCount), audiencCount);
    }
}
```  
Screening의 reserve 메서드를 보면 calculateFee라는 private 메서드를 호출해서 요금을 계산한 후 그 결과를 Reservation의 생성자에 전달한다.  
  
영화를 예매하기 위해 Screening, Movie, Reservation 인스턴스들은 서로의 메서드를 호출하며 상호작용한다.  
이처럼 객체들 사이의 상호작용을 **협력(Collaboration)**이라고 한다.  
  
### 협력에 관한 짧은 이야기  
앞에서 설명한 것 처럼 객체의 내부 상태는 외부에서 접근하지 못하도록 감춰야 한다.  
대신 외부에 공개하는 퍼블릭 인터페이스를 통해 내부 상태에 접근할 수 있도록 허용한다.  
  
객체가 다른 객체와 상호작용 할 수 있는 방법은 **메시지를 전송하고 메시지를 수신**하는 방법 뿐이다.  
**메서드**는 수신된 메시지를 처리하기 위한 자신만의 방법이다.  
  
## 03. 할인요금 구하기 
### 할인 요금 계산을 위한 협력 시작하기  
```java
package org.example.chapter02.movie;

public class Movie {
    private String title;
    private Duration runningTime;
    private Money fee;
    private DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }
    
    public Money getFee() {
        return fee;
    }

    public Money calculateMovieFee(Screening screening) {
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```  
calculateMovieFee 메서드는 discountPolicy에 calculateDiscountAmount 메시지를 전송해 할인 요금을 반환받는다.  
Movie는 기본요금인 fee 에서 반환된 할인 요금을 차감한다.  
   
이 메서드에는 한 가지 이상한 점이 있다.  
어떤 할인 정책을 사용할 것인지 결정하는 코드가 어디에도 없다.  
할인 정책은 고정 혹은 비율로 설정할 것인데!  
  
이 코드는 **상속과 다형성** 두 가지 개념이 섞여 있다.  
이 기반에는 **추상화**라는 원리가 숨어져있다.  
  
### 할인 정책과 할인 조건   
할인 정책은 금액 할인 정책과 비율 할인 정책으로 구분된다.  
두 가지 할인 정책을 각각 AmountDiscountPolicy 와 PercentDiscountPolicy 라는 클래스로 구현할 것이다.  
두 클래스는 대부분의 코드가 유사하고, 할인 요금을 계산하는 방식만 조금 다르다. 따라서 두 클래스 사이의 중복 코드를 제거하기 위해 공통 코드를 보관할 장소가 필요하다.  
  
여기서는 부모 클래스인 DiscountPolicy 안에 중복 코드를 두고 AmountDiscoutPolicy 와 PercentDiscountPolicy 가 이 클래스를 상속받게 할 것이다.  
실제 애플리케이션에서는 DiscoutPolicy의 인스턴스를 생성할 필요가 없기 때문에 **추상 클래스(abstrac class)** 로 구현했다.  
  
스프링 강의에서는 VIP인지 여부에 따라 할인 유무와, 할인 정책을 달리 했었는데, 오브젝트의 경우 여러 조건을 넣어서 할인 정책을 바꿀 수 있도록 했다.  
  
이처럼 부모 클래스에 기본적인 알고리즘의 흐름을 구현하고 중간에 필요한 처리를 자식 클래스에게 위임하는 디자인 패턴을 TEMPLATEMETHOD 패턴 이라고 부른다. 


SequenceCondition ,PeriodCondition 이라는 클래스로 구현할 것이다.  
  
오버로딩과 오버라이딩  
> 오버라이딩과 오버로딩  
> 오버라이딩 : 부모 클래스에 정의된 같은 이름, 같은 파라미터 목록을 가진 메서드를 자식 클래스에서 재정의 하는 경우를 가리킨다.  
> 오버로딩 : 메서드 명이 같지만 파라미터가 다른 경우  
  
  
### 할인 정책 구성하기    
하나의 영화에 대해 단 하나의 할인 정책만 설정할 수 있지만, 할인 조건의 경우에는 여러개를 적용할 수 있다.  
Movie 와 DiscountPolicy의 생성자는 이런 제약을 강제한다.   

```java
public class Movie {
    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        ...
        this.discountPolicy = discountPolicy;
    }
}
```  
  
반면 DiscountPolicy 의 생성자는 여러개의 DiscountCondition 인스턴스를 허용한다.

```java
import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class DiscountPolicy {
    public DiscountPolicy(DiscountPolicy discountPolicy) {
        this.condition = Arrays.asList(conditions);
    }
}
```
    
이처럼 생성자의 파라미터 목록을 이용해 초기화에 필요한 정보를 전달하도록 강제하면 올바른 상태를 가진 객체의 생성을 보장할 수 있다.  
  
```java

Movie avatar = new Movie("아바타", Duration.ofMinutes(120)z Money.wons(10000),
new AmountDiscountPolicy(Money.wons(800)z
new Sequencecondition(1)z
new Sequencecondition(10),
new Periodcondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11z 59)),
new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))));
```

## 04. 상속과 다형성    
### 컴파일 시간 의존성과 실행 시간 의존성    
Movie와 DiscountPolicy는 연결돼 있으며,  
AmountDiscountPolicy 와 PercentDiscountPolicy는 추상 클래스인 DiscountPolicy를 상속받는다.  
  
여기서 눈여겨야 할 부분은 Movie 클래스가 DiscountPolicy 클래스와 연결돼 있다는 것이다.  
그런데 영화 요금을 계산하기 위해서는 추상 클래스인 DiscountPolicy가 아니라 AmountDiscountPolicy와 PercentDiscountPolicy 인스턴스가 필요하다는 것이다.  
따라서 Movie의 인스턴스는 실행 시에 AmountDiscountPolicy 나 PercentDiscountPolicy의 인스턴스에 의존해야 한다.  
하지만 코드 수준에서는 Movie 클래스는 이 두 클래스 중 어떤 것에도 의존하지 않는다. 오직 추상클래스인 DiscountPolicy에만 의존하고 있다.  
  
그렇다면 Movie 클래스는 어떻게 DiscountPolicy의 구현체들과 협력할까?   
답은 Movie 생성자 부분을 보면 알 수 있다.  

```java
public class Movie{
    Movie avatar = new Movie("아바타",
        new PercentDiscountPolicy()); 
}
```  
  
이 경우 Movie의 인스턴스는 PercentDiscountPolicy의 인스턴스에 의존하게 된다.  
  
이번 장의 핵심은 코드의 의존성과 실행 시점의 의존성은 다를 수 있다.  
-> 코드의 의존성은 DiscountPolicy이지만, 실행 시점의 의존성은 PercentDiscountPolicy 이다.  
  
이러한 코드 작성이(코드의 의존성과 실행 시점의 의존성이 다른 경우) 코드를 이해하기가 어려워진다.  
즉 코드를 작성하기 쉬울수록 코드를 이해하기 쉬워지지만,   
반면 이렇게 유연성과 확장성을 고려하여 코드를 작성한다면, 코드를 이해하는데 어려움이 생길 수 있다.  
  
훌륭한 객체지향 설계자로 성장하기 위해서는 항상 유연성과 가독성 사이에서 고민해야 한다.  
무조건 유연한 설계도 읽기 쉬운 코드도 정답이 아니다.  
  
### 차이에 의한 프로그래밍   
클래스를 하나 추가하고 싶은데 그 클래스가 기존의 어떤 클래스와 매우 흡사하다고 가정해보자.  
그 클래스의 코드를 가져와 서 약간만 추가하거나 수정해서 새로운 클래스를 만들 수 있다면 좋을 것이다.  
즉 기존의 코드를 재사용 한다.  ->  이를 가능하게 해주는 것이 상속이다.  
  
상속은 객체지향 코드에서 코드를 재사용하기 위해 가장 많이 사용되는 방법이다.  
  
상속은 기존 클래스를 기반으로 새로운 클래스를 쉽고 빠르게 추가할 수 있는 간편한 방법을 제공한다.   
부모의 코드에서 변경하고 싶은 경우 부모의 코드를 오버라이딩 하면 된다.  
이처럼 부모 클래스와 다른 부분만을 추가해서 새로운 클래스를 쉽고 빠르게 만드는 방법을 **차이에 의한 프로그래밍(programming by differece)**라고 부른다.  
  
### 상속과 인터페이스  
상속이 가치있는 이유는 부모 클래스가 제공하는 모든 인터페이스를 자식 클래스가 물려받을 수 있기 때문이다.  
이것은 상속을 바라보는 일반적인 인식과 거리가 있는데 -> 대부분의 경우 상속의 목적이 메서드나 인스턴스 변수를 재사용하는 것이라고 생각하기 때문  
  
결과적으로 자식 클래스는 부모 클래스가 수신할 수 있는 모든 메시지를 수신할 수 있기 때문에 외부 객체는 자식 클래스를 부모 클래스와 동일한 타입으로 간주할 수 있다.   
  
이처럼 자식 클래스가 부모 클래스를 대신하는 것을 **업캐스팅** 이라고 한다.  
  
### 다형성  
다시한번 강조하지만 메시지와 메서드는 다른 개념이다.  
Movie 는 DiscountPolicy의 인스턴스에게 calculateDiscountAmount 메시지를 전송한다.   
그렇다면 실행되는 메서드는?  
Movie와 상호작용하기 위해 연결된 클래스에 따라 달라진다.  
  
다시 말해서 Movie는 동일한 메시지를 전송하지만 실제로 어떤 메서드가 실행될 것인지는 메시지를 수신하는 객체의 클래스가 무엇이냐에 따라 달라진다.  
이를 **다형성**이라고 부른다.  
  
다형성은 객체지향 프로그램의 컴파일 시간 의존성과 실행시간 의존성이 다를 수 있다는 사실을 기반으로 한다.  
프로그램을 작성할 땐, Movie 클래스는 추상 클래스인 DiscountPolicy 에 의존하지만  
실행 시점에 Movie와 상호작용 하는 클래스는 PercentDiscountPolicy 혹은 AmountDiscountPolicy이다.  
이처럼 다형성은 컴파일 시간 의존성과 실행 시간 의존성을 다르게 만들수 있는 객체지향의 특성을 이용해 서로 다른 메서드를 실행할 수 있게 한다.  
  
다형성이란 동일한 메시지를 수신했을 때 객체의 타입에 따라 다르게 응답할 수 있는 능력을 의미한다.  
따라서 다형적인 협력에 참여하는 객체들은 모두 같은 메시지를 이해할 수 있어야 한다.  
-> 인터페이스가 동일해야 한다.   
  
다형성을 구현하는 방법은 매우 다양하지만, 메시지에 응답하기 위해 실행될 메서드를 컴파일 시점이 아닌 실행 시점에 결정한다는 공통점이 있다.  
다시 말해 메시지와 메서드를 실행 시점에 바인딩 한다.  
이를 **지연 바인딩**, 혹은 **동적 바인딩** 이라고 부른다.  
그에반해 전통적인 함수 호출처럼 컴파일 시점에 실행될 함수나 프로시저를 결정하는 것을 **초기 바인딩** 또는 **정적 바인딩**이라고 한다.  
  
객체지향이 컴파일 시점의 의존성과 실행 시점의 의존성을 분리하고, 하나의 메시지를 선택적으로 서로 다른 메서드에 연결할 수 있는 이유가 바로 지연바인딩이라는 메커니즘을 사용하기 때문이다.  
  
> 구현 상속과 인터페이스 상속  
> 상속을 **구현 상속과 인터페이스 상속**으로 분류할 수 있다.  
> 흔히 구현 상속을 **서브클래싱**이라고 부르고,  
> 인터페이스 상속을 **서브타이핑**이라고 부른다.  
> 상속은 구현 상속이 아니라, 인터페이스 상속을 위해 사용해야 한다. (서브클래싱이 아니라, 서브타이핑을 위해 사용해야 한다.)  
> 인터페이스를 재사용할 목적이 아니라, 구현을 재사용할 목적으로 상속을 사용하며, 변경에 취약한 코드를 낳게 될 확률이 높다.  
  
### 인터페이스와 다형성  
앞에서는 DiscountPolicy를 추상 클래스로 구현함으로써, 자식 클래스들이 인터페이스와 내부 구현을 함께 상속받도록 만들었다.  
그러나 종종 구현은 공유할 필요가 없고 순후하게 인터페이스만 공유하고 싶을때가 있다.  
이를 위해 자바에서는 **인터페이스**라는 프로그래밍 요소를 제공한다.  
  
추상 클래스를 이용해 다형성을 구현했던 할인 정책과 달리 할인 조건은 구현을 공유할 필요가 없기 때문에 자바의 인터페이스를 이용해 타입 계층을 구현할 수 있다.  
  
## 05. 추상화와 유연성  
### 추상화의 힘  
지금까지 살펴본것 처럼 할인 정책은 구체적인 금액 할인 정책과 비율 할인 정책을 포괄하는 추상적인 개념이다.  
할인 조건 역시 더 구체적인 순번 조건과 기간 조건을 포괄하는 추상적인 개념이다.  
다시 말해 DiscountPolicy는 AmountDiscountPolicy와 PercentDiscountPolicy보다 추상적이고,  
DiscountCondition은 SequenceCondition과 PeriodCondition보다 추상적이다.  
  
프로그래밍 언어 측면에서 DiscountPolicy와 DiscountCondition이 더 추상적인 이유는 인터페이스에 초점을 맞추기 때문이다.  
구현은 자식 클래스가 결정할 수 있도록 결정권을 위임한다.  
  
추상화를 이용해 상위 정책을 기술한다는 것은 기본적인 애플리케이션의 협력 흐름을 기술한다는 것을 의미  
새로운 자식 클래스들은 추상화를 이용해서 정의한 상위의 협력 흐름을 그대로 따르게 된다.  
이 개념은 매우 중요한데, 재사용 가능한 설계의 기본을 이루는 **디자인 패턴** 이나 **프레임워크**모두 추상화를 이용해 상위 정책을 정의하는 객체지향 메커니즘을 활용하고 있기 때문이다.  
  
### 유연한 설계  
스타워즈의 경우 할인을 하지 않아서 할인요금을 계산하지 않고, 기본 금액을 그대로 적용한다. 
```java
public class Movie {
    public Money calculateMovieFee(Screening screening) {
        if (discountPolicy == null) {
            return fee;
        }
        return fee.minus(discountPolicy.calculateDiscountAmount(screening));
    }
}
```
이 방식의 문제점은 할인 정책이 없는 경우를 예외 케이스로 취급하기 때문에, 지금까지 일관성 있던 협력 방식이 무너지게 된다는 것이다.  
기존 할인 정책의 경우, 할인할 금액을 계산하는 책임이 DiscountPolicy의 자식 클래스에 있었지만 할인 정책이 없는 경우에는 할인 금액이 0원이라는 사실을 결정하는 책임이 DiscountPolicy가 아닌 Movie 쪽에 있기 때문이다.  
따라서 책임의 위치를 결정하기 위해 조건문을 사용하는 것은 협력의 설계 측면에서 대부분의 경우 좋지 않은 선택이다.   
  
이 경우 일관성을 지킬 수 있는 방법은 0원이라는 할인 요금을 계산할 책임을 그대로 DiscountPolicy 계층에 유지시키는 것이다.  
  
NoneDiscountPolicy 클래스를 추가하자

```java
public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
```  
이제 Movie의 인스턴스에 NoneDiscountPolicy의 인스턴스를 연결해서 할인되지 않는 영화를 생성할 수 있다.  
```java
public class Movie {
    Movie starWars = new Movie("스타워즈",
            new NoneDisoucntPolicy());
}
```   
중요한 것은 기존의 Movie와 DiscountPolicy는 수정하지 않고 NoneDiscountPolicy라는 새로운 클래스를 추가하는 것만으로 애플리케이션의 기능을 확장했다는 것이다. 
이처럼 추상화를 중심으로 코드의 구조를 설계하면 유연하고 확장 가능한 설계를 만들 수 있다.  
  
추상화가 유연한 설계를 가능하게 한 이유 : 설계가 구체적인 상황에 결합되는 것을 방지하기 때문이다.  
Mocie는 특정한 할인 정책에 묶이지 않는다.  
할인 정책을 구현한 클래스가 DiscountPolicy를 상속받고 있다면 어떤 클래스와도 협력이 가능하다.  
  
8장에서 살펴보겠지만, **컨텍스트 독립성**이라고 불리는 이 개념은 프레임워크와같은 유연한 설계가 필수적인 분야에서 그 진가를 발휘한다.  
  
### 추상 클래스와 인터페이스 트레이드오프  ---------- 추후에 다시 정리해보기 
코드를 작성하다보면 트레이드오프를 할 경우가 생긴다.  
코드를 작성할 땐 합당한 이유가 있어야 한다.   
비록 아주 사소한 결정이라도 트레이드 오프를 통해 얻어진 결론과 그렇지 않은 결론 사이의 차이는 크다.   
  
### 코드 재사용    
상속은 코드를 재사용하기 위해 널리 사용되는 방법이다.  
그러나 널리 사용되는 방법이라고 가장 좋은 방법은 아니다.  
객체지향 설계와 관련된 자료를 조금이라도 본 사람의 경우 상속보다는 **합성**이 더 좋은 방법이라고 들었을 것이다.  
  
그렇다면 많은 사람들이 상속 대신 합성을 선호하는 이유는 무엇일까?  
  
### 상속  
상속은 코드를 재사용하기 위해 널리 사용되는 기법이지만, 두 가지 관점에서 설계에 안좋은 영향을 미친다.  
- 상속이 캡슐화를 위반한다.  
- 설계를 유연하게 만들지 못한다.  

  
상속의 가장 큰 문제는 **캡슐화를 위반한다는 것**이다.  
상속을 이용하기 위해서는 부모 클래스의 내부 구조를 잘 알고 있어야 한다.  
결과적으로 부모 클래스의 구현이 자식 클래스에게 노출되기 때문에 캡슐화가 약화된다.  
캡슐화의 약화는 자식 클래스가 부모 클래스에 강하게 결합되도록 만들기 때문에 부모 클래스를 변경할 때, 자식 클래스도 함께 변경될 확률을 높인다.  
  
두 번째 단점은 **설계가 유연하지 않다는 것**이다.  
상속은 부모클래스와 자식 클래스 사이의 고나계를 컴파일 시점에 결정한다.  따라서 실행 시점에 객체의 종류를 변경하는 것이 불가능하다.  
  
예를 들어 실행 시점에 금액 할인 정책인 영화를 비율 할인 정책으로 변경한다고 가정하자.  
상속을 사용한 설계에서는 AmountDiscountMovie의 인스턴스를 PercentDiscountMovie의 인스턴스로 변경해야 한다.  
  
반면 인스턴스 변수로 연결한 기존 방법을 사용하면 실행 시점에 할인 정책을 간단하게 변경할 수 있다.  
다음과 같이 Movie에 DiscountPolicy를 변경할 수 있는 코드를 추가해보자.  
```java
public class Movie {
    private DiscountPolicy discountPolicy;

    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```
  
이제 간단하게 할인정책을 바꿀 수 있다.  
```java
public class Movie {
    Movie avatar = new Movie("아바타",
            new AmountDiscountPolicy());
    
    avatar.changeDiscountPolicy(new PercentDiscountpolicy());
}
```
  
이 예제를 통해 상속보다 인스턴스 변수로 관계를 연결한 원래의 설계가 더 유연하다는 사실을 알 수 있다.  
  
### 합성  
위의 예제는 내부 구현에 대해 잘 알지 못하고, 메서드만 제공한다.   
이처럼 인터페이스에 정의된 메시지를 통해서만 코드를 재사용하는 방법을 **합성**이라고 부른다.  
  
합성은 상속이 가지는 두 가지 문제점을 모두 해결한다.  
인터페이스에 정의된 메서드를 통해서만 재사용이 가능하기 때문에 구현을 효과적으로 캡슐화가 가능하다.  
또한 의존하는 인스턴스를 교체하는 것이 비교적 쉽기 때문에 설계를 유연하게 만들어준다.  
-> 합성은 메시지를 통해 유연하게 결합한다.  
따라서 코드 재사용을 위해서는 상속보다는 합성을 선호하는 것이 더 좋은 방법이다.  
  
그렇다고 해서 상속을 절대 사용하면 안되는 것은 아니다.  
대부분의 설계에서는 상속과 합성을 같이 사용한다.  
  
프로그래밍 관점에서 클래스와 상속이 중요하지만, 너무 프로그래밍 관점으로만 바라보면 객체지향의 본질을 놓치기 쉽다.  
  
객체지향이란 객체를 지향하는 것이다.   
객체지향에서 가장 중요한 것은 애플리케이션의 기능을 구현하기 위해 협력에 참여하는 객체들 사이의 상호작용이다.  
객체들은 협력에 참여하기 위해 역할을 부여받고 역할에 적합한 책임을 수행한다.  
  
