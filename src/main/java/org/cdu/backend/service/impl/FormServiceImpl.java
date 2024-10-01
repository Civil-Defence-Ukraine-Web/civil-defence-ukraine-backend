package org.cdu.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.cdu.backend.dto.form.FormRequestDto;
import org.cdu.backend.service.FormService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
    private static final String MESSAGE_TEXT_BODY = "Feedback body: ";
    private static final String MESSAGE_TEXT_SENDER = "Feedback sender: ";
    private static final String MESSAGE_TEXT_IS_VOLUNTEER = "Is sender want to be volunteer: ";

    @Value("${form.mail.recipient}")
    private String mailRecipient;
    private final JavaMailSender mailSender;

    @Override
    public void sendForm(FormRequestDto requestDto) {
        SimpleMailMessage message = createMessage(requestDto);
        mailSender.send(message);
    }

    private SimpleMailMessage createMessage(FormRequestDto requestDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        String messageText = MESSAGE_TEXT_BODY
                        + System.lineSeparator()
                        + requestDto.message()
                        + System.lineSeparator()
                        + System.lineSeparator()
                        + MESSAGE_TEXT_SENDER
                        + requestDto.email()
                        + System.lineSeparator()
                        + MESSAGE_TEXT_IS_VOLUNTEER
                        + (requestDto.isVolunteer() ? "Yes" : "No");

        message.setTo(mailRecipient);
        message.setSubject(requestDto.subject());
        message.setText(messageText);

        return message;
    }
}
