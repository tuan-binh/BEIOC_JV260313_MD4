package com.example.notiservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailListener {
    private final MailService mailService;

    @KafkaListener(topics = "order-created",groupId = "notify-group",topicPartitions = @TopicPartition(
            topic = "medicine-stock-event",
            partitions = {"0","1"}
    ))
    public void listenerMail(String email) {
        mailService.sendEmail(email,"Xác nhận đơn hàng","Vui lòng bạn xác nhận đơn hàng này thông qua luồng mail này");
    }

}
