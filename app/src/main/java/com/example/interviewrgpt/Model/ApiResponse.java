package com.example.interviewrgpt.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("choices")
    List<Conversation> choices;

    public ApiResponse(List<Conversation> choices) {
        this.choices = choices;
    }

    public List<Conversation> getChoices() {
        return choices;
    }

    public void setChoices(List<Conversation> choices) {
        this.choices = choices;
    }
}
