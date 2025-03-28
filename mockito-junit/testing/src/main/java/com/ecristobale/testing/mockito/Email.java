package com.ecristobale.testing.mockito;

import lombok.Getter;
import lombok.Setter;

public class Email {

    private String msgContent;
    @Setter
    @Getter
    private String address;

    public void setContent(String msgContent) {
        this.msgContent = msgContent;
    }

}
