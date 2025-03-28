package com.ecristobale.testing.mockito;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Messenger {
    private TemplateEngine templateEngine;
    private MailServer mailServer;

    public void sendMessage(Client client, Template template) {
        String msgContent = templateEngine.prepareMessage(client, template);
        Email email = new Email();
        email.setContent(msgContent);
        email.setAddress(client.getEmail());
        mailServer.send(email);
    }
}
