package com.example.interviewrgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.interviewrgpt.Model.ApiRequest;
import com.example.interviewrgpt.Model.ApiResponse;
import com.example.interviewrgpt.Model.MessageModel;
import com.example.interviewrgpt.Service.RetrofitInstance;
import com.example.interviewrgpt.Service.getEndpointService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static List<MessageModel> message;
    static ApiRequest request;

    static RecyclerView recyclerView;

    static Adapter adapter;

    static String userName;

    static Executor executor;

    Button leave_room;
    static TextToSpeech textToSpeech;

    static SpeechRecognizer speechRecognizer;

    static Intent recognizerIntent;


    static final String model = "gpt-3.5-turbo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leave_room = findViewById(R.id.leave);
        recyclerView = findViewById(R.id.chat_ui);
        executor = Executors.newSingleThreadExecutor();
        message = new ArrayList<>();

        Toast.makeText(getApplicationContext(),"Please keep you speech short otherwise it will be trimmed" +
                "....",Toast.LENGTH_LONG).show();

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Log.d("Speech done","");
                } else {
                    // TTS initialization failed. Handle the error.
                    Log.d("Speech not done","");
                }
            }
        });

        textToSpeech.setLanguage(Locale.getDefault());

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        RecognitionListener recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                // Called when the recognizer is ready to start listening.
            }

            @Override
            public void onBeginningOfSpeech() {
                // Called when the user starts speaking.
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // Called when the input volume level changes.
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                // Called when audio data is received.
            }

            @Override
            public void onEndOfSpeech() {
                // Called when the user stops speaking.
            }

            @Override
            public void onError(int error) {
                // Called if there is an error during recognition.
            }

            @Override
            public void onResults(Bundle results) {
                // Called when speech recognition is successful.
                ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (voiceResults != null && !voiceResults.isEmpty()) {
                    String recognizedText = voiceResults.get(0); // Get the first recognized text.
                    Log.d("Recognized text",recognizedText);
                    message.add(new MessageModel("user",recognizedText));
                    callGPT(new ApiRequest(model,message,400));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // Called when partial recognition results are available.
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                // Called when an event related to recognition occurs.
            }
        };

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); // Set the language

        speechRecognizer.setRecognitionListener(recognitionListener);



        Intent received_userDetails = getIntent();
        userName = received_userDetails.getStringExtra("user");
        String jobProfile = received_userDetails.getStringExtra("profile");

        String command = "Act as Interviewer Interviewing for "+jobProfile+ " position ask series of 5 casual and technical " +
                "questions one by one as user respond, if candidate seems" + "nervous say be calm and drink water if needed. " + "If candidate" +
                "asks anything else or says anything " + "offensive say be on " + "point do not reply to any other questions, " +
                "in the end of interview say thank you we will " + "let you know";

        message.add(new MessageModel("system",command));
        message.add(new MessageModel("assistant","Welcome to the interview. I will be asking you a series " +
                "of questions, " + "and you can respond as the candidate. Let's begin.\n"));

        message.add(new MessageModel("user","Yes sir....."));

        request = new ApiRequest(model,message,100);

        adapter = new Adapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        leave_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLauncherIntent = new Intent(MainActivity.this,MainLauncher.class);
                startActivity(mainLauncherIntent);
                textToSpeech.stop();
                speechRecognizer.cancel();
            }
        });
//        //
        callGPT(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeech.shutdown();
        speechRecognizer.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        textToSpeech.stop();
    }

    public void callGPT(ApiRequest request)
    {
        getEndpointService mainService  = RetrofitInstance.getService();
        Call<ApiResponse> call = mainService.generateText(request);
        call.enqueue(new Callback<ApiResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                ApiResponse received_response = response.body();
                if(received_response != null) {
                    message.add(received_response.getChoices().get(0).getReceived_messages());
                    String text = received_response.getChoices().get(0).getReceived_messages().getContent();
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
//                animate.cancel();
//                gpt_wait_logo.setVisibility(View.GONE);
//                    card_view_reply.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    scrollUp();

                }
                else
                    Toast.makeText(MainActivity.this,"Service Error...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
              Log.e("Error",t.getMessage());
            }
        });
    }

    static public void scrollUp()
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(message.size()-1);
            }
        });
    }

}