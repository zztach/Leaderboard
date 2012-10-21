package org.zisist.leaderboard.publisher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.zisist.leaderboard.publisher.Publisher;
import org.zisist.model.Configuration;
import org.zisist.model.TopPlayer;

import java.util.List;

/**
 * Sends by email the results of the algorithm's execution<br>
 *
 * Created by zis.tax@gmail.com on 12/10/2012 at 1:14 AM
 */
@Service("emailPublisher")
public class EmailPublisher implements Publisher {
    @Autowired
    private MailSender mailSender;

    @Override
    public void publish(List<TopPlayer> topPlayerList, Configuration conf) {
        SimpleMailMessage simpleMailMessage;
        simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("Leaderboard_Merger_App");
        String[] recipients = {conf.getEmail()};
        simpleMailMessage.setTo(recipients);
        StringBuilder message = new StringBuilder("World Ranking").append("\n");
        for(TopPlayer topPlayer : topPlayerList) {
            message.append(topPlayer.getName()).append(" -- ")
                    .append(topPlayer.getPoints()).append(" -- ")
                    .append(topPlayer.getCountry()).append("\n");
        }
        simpleMailMessage.setText(message.toString());
        simpleMailMessage.setSubject("Publishing Top Of The World");
        mailSender.send(simpleMailMessage);

    }
}
