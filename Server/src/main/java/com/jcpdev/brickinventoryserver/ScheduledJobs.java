package com.jcpdev.brickinventoryserver;

import com.jcpdev.brickinventoryserver.Models.Items;
import com.jcpdev.brickinventoryserver.Repo.ItemsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
public class ScheduledJobs {

    @Autowired
    private ItemsRepo itemsRepo;


    @Scheduled(cron = "0 0 08 ? * FRI")//every min
    public void minCheckJob() throws MessagingException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now= LocalDateTime.now();
        EmailService emailService = new EmailService();
        StringBuilder message = new StringBuilder();
        List<Items> itemsList = itemsRepo.findAll();
        for (Items item : itemsList) {
            if(item.getQuantity()< item.getMinimumQuantity()) {
                int orderAmount = item.getMinimumQuantity()- item.getQuantity();
                message.append("<img width= 100px height=100px src="+item.getPhotoUrl()+"><b> "+item.getName()+"</b> QTY is at <b>"+item.getQuantity()+"/"+item.getMinimumQuantity()+"</b>. <mark>Order "+ orderAmount+" more.</mark><br><br>");
            }
        }
        try {
            emailService.sendLowStockEmail(message.toString(), dtf.format(now));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
