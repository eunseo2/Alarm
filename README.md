# Alarm System
* set up rabbitMQ config 
* message publish to queue
* consume message from queue
* asynchronous message consumption
* save message 

# 알림 서비스

![image](https://user-images.githubusercontent.com/70589857/218290490-120395b0-91ca-46dd-9d46-0eae7bc915d5.png)

카카오 개발자 컨퍼런스(2022) 중 알림 서비스 세션이 흥미로웠습니다.

알림 생성 서버/ 조회 서버를 분리하며, 알림 **생성 서버**에 각각의 장애 발생을 고려하여 설계를 했던 점이 인상깊었습니다.

따라서, 직접 구현해보면 재밌을 거 같아서 간단하게 생성 서버를 구현해보고자 하였습니다. 

[설계 로직]

1) 알림 요청이 들어오면 큐에 넣고 바로 응답하며, 알림 생성은 비동기로 처리한다. 

2) Consumer가 알림 생성을 비동기로 처리 한 후 DB에 저장한다

3) 모두 완료 될 경우 큐로 ack(신호)를 보낸다. 

*최종 소스코드

[https://github.com/eunseo2/AlarmSystem](https://github.com/eunseo2/AlarmSystem)

알림 유실을 대비 하기 위해 사용되는 큐는 rabbitMQ를 이용하였습니다.

![image](https://user-images.githubusercontent.com/70589857/218290505-335689e7-673b-4985-8a6e-750a29464e5e.png)

**set up rabbitMQ config & message publish to queue**

[https://github.com/eunseo2/Alarm/commit/7b5f65630036cef743de4d7417bc8e457633c0d4](https://github.com/eunseo2/Alarm/commit/7b5f65630036cef743de4d7417bc8e457633c0d4)

[https://github.com/eunseo2/Alarm/commit/43614cea40723808280ae639d19ca34f09f46baa](https://github.com/eunseo2/Alarm/commit/43614cea40723808280ae639d19ca34f09f46baa)

**consume message from queue**

[https://github.com/eunseo2/Alarm/commit/2176ddbaaa153ca81af13dc9de6fdea25df561ba](https://github.com/eunseo2/Alarm/commit/2176ddbaaa153ca81af13dc9de6fdea25df561ba)

![image](https://user-images.githubusercontent.com/70589857/218290532-8e207542-3246-407b-b7ca-09b84f64ceca.png)

- `channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);`
- rabbitMQ로 ack를 보낼 경우에만 정상적으로 consume 된 것이다.

EX) 신호를 보내지 않을 시 Unacked에 표시된다. 

![image](https://user-images.githubusercontent.com/70589857/218290545-a1ccc67e-3e94-4319-a1ee-a60a77e09096.png)

만약, 정상적으로 message를 consume 하면, Unacked | Total 모두 0이 된다.

![image](https://user-images.githubusercontent.com/70589857/218290552-53029da9-6aa2-4cd3-9a45-00c372ab36cc.png)

** **비동기 테스트** 

```java
for (int i = 0; i < 10; i++) {
			consumeMessageService.consumeMessage(i);
	}
```

```java
  @Async
	@Override
	public void consumeMessage(int i) {
		try {
			Thread.sleep(3000);
			System.out.println("[AsyncMethod]" + "-" + i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
```

![image](https://user-images.githubusercontent.com/70589857/218290568-80f720a0-7d11-4d9c-b701-4ab48bf1e539.png)
 

[참고]

[if(kakao)dev2022](https://if.kakao.com/2022/session/36)

[RabbitMQ tutorial - Work Queues - RabbitMQ](https://www.rabbitmq.com/tutorials/tutorial-two-java.html)

[[AMQP][RabbitMQ]RabbitMQ아주 기초적이게 사용하기 - Java(feat.Hello World!) - (2)](https://kamang-it.tistory.com/entry/AMQPRabbitMQRabbitMQ%EC%95%84%EC%A3%BC-%EA%B8%B0%EC%B4%88%EC%A0%81%EC%9D%B4%EA%B2%8C-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-JavafeatHello-World-2)

[Spring Boot RabbitMQ Producer and Consumer Example](https://www.javaguides.net/2022/07/spring-boot-rabbitmq-producer-and-consumer-example.html)

[RabbitMQ 도입과 캐싱 처리하기](https://velog.io/@znftm97/RabbitMQ-%EB%8F%84%EC%9E%85%EA%B3%BC-%EC%BA%90%EC%8B%B1-%EC%B2%98%EB%A6%AC%ED%95%98%EA%B8%B0)
