package com.example.interviewrgpt;


import android.content.Context;
import android.widget.Toast;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest;
import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretManagerServiceSettings;

import java.io.IOException;


public class getApiKey {
    static public String apiKey;
    getApiKey(Context context){

        String projectId = "focus-copilot-397914";
        String secretId = "chat_gpt_api";

        try {
        GoogleCredentials credentials = GoogleCredentials.fromStream(context.getResources().
                openRawResource(R.raw.cloud_manager_key));
        SecretManagerServiceSettings settings = SecretManagerServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

        SecretManagerServiceClient secret_client = SecretManagerServiceClient.create(settings);

        String secretName = "projects/" + projectId + "/secrets/" + secretId + "/versions/latest";

        AccessSecretVersionResponse response = secret_client.accessSecretVersion(secretName);
        apiKey = response.getPayload().getData().toStringUtf8();
        secret_client.close();
        }
        catch(Exception e)
        {
            Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
        }

    }

}
