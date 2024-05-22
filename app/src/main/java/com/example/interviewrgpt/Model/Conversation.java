package com.example.interviewrgpt.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Conversation {

    @SerializedName("message")
    MessageModel received_messages;

    public Conversation(MessageModel received_messages)
    {
        this.received_messages = received_messages;
    }

    public MessageModel getReceived_messages() {
        return received_messages;
    }

    public void setReceived_messages(MessageModel received_messages) {
        this.received_messages = received_messages;
    }
}
