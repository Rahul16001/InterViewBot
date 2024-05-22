package com.example.interviewrgpt.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiRequest {
    @SerializedName("model") String model;
    @SerializedName("messages") List<MessageModel> messages;

    @SerializedName("max_tokens") int maxTokens;

    public ApiRequest(String model, List<MessageModel> messages,int maxTokens)
    {
        this.messages = messages;
        this.model = model;
        this.maxTokens = maxTokens;
    }

    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }
}
