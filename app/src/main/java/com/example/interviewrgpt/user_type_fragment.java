package com.example.interviewrgpt;

import static com.example.interviewrgpt.MainActivity.adapter;
import static com.example.interviewrgpt.MainActivity.message;
import static com.example.interviewrgpt.MainActivity.model;
import static com.example.interviewrgpt.MainActivity.recognizerIntent;
import static com.example.interviewrgpt.MainActivity.scrollUp;
import static com.example.interviewrgpt.MainActivity.speechRecognizer;
import static com.example.interviewrgpt.UserDetails.mode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.interviewrgpt.Model.ApiRequest;
import com.example.interviewrgpt.Model.MessageModel;

import java.util.Objects;

public class user_type_fragment extends Fragment {
    ImageView reply_button;
    static Animation animate;

    EditText user_text;

    View card_view_reply,recording_view;
    Button recording_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_type_fragment, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {
        super.onStart();
        reply_button = requireView().findViewById(R.id.reply_button);
        user_text = requireView().findViewById(R.id.reply_holder);
        animate= AnimationUtils.loadAnimation(requireContext().getApplicationContext(),R.anim.logo_rotate);
        reply_button = requireView().findViewById(R.id.reply_button);
        card_view_reply = requireView().findViewById(R.id.card_view_reply);
        recording_view = requireView().findViewById(R.id.recording_view);
        recording_button = requireView().findViewById(R.id.recording_button);

        if(mode == 1)
        {
            card_view_reply.setVisibility(View.VISIBLE);
            recording_view.setVisibility(View.GONE);
        }

        else if(mode == 0)
        {
            recording_view.setVisibility(View.VISIBLE);
            card_view_reply.setVisibility(View.GONE);
        }

        reply_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                String text = user_text.getText().toString();
                if(text.equals("")) Toast.makeText(requireContext().getApplicationContext(),"Enter Something First...",
                        Toast.LENGTH_SHORT).show();
                else {
//                    card_view_reply.setVisibility(View.GONE);
//                    gpt_wait_logo.setVisibility(View.VISIBLE);
//                    gpt_wait_logo.startAnimation(animate);
//                    InputMethodManager imm = (InputMethodManager) requireContext().
//                            getSystemService(Context.INPUT_METHOD_SERVICE); // to hide keyboard after reply
//                    imm.hideSoftInputFromWindow(user_text.getWindowToken(), 0);
                    message.add(new MessageModel("user",text));
                    user_text.clearFocus();
                    user_text.setText("");
                    playSound();
                    adapter.notifyDataSetChanged();
                    scrollUp();
                    new MainActivity().callGPT(new ApiRequest(model,message,100));

                }
            }
        });

        recording_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    speechRecognizer.startListening(recognizerIntent);
                    Log.d("listening","");
                }

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                  speechRecognizer.stopListening();
                  Log.d("stopped listening","");
                }
                return false;
            }
        });

    }

    void playSound()
    {
        MediaPlayer click  = MediaPlayer.create(requireActivity().getApplicationContext(),R.raw.click_sound);
        click.start();
    }




}