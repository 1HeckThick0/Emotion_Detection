package com.example.emotiondetection.src.ui;

import static com.example.emotiondetection.utils.Constants.USER_LOGO;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emotiondetection.R;
import com.example.emotiondetection.adapters.ChatAdapter;
import com.example.emotiondetection.api.ServerRequestHandler;
import com.example.emotiondetection.api.ServerResponseListener;
import com.example.emotiondetection.models.BotResponse;
import com.example.emotiondetection.models.ChatModel;
import com.example.emotiondetection.models.UserMessage;
import com.example.emotiondetection.models.UserSession;
import com.example.emotiondetection.utils.SharedPref;
import com.example.emotiondetection.utils.SpeechRecognitionHelper;
import com.example.emotiondetection.utils.UiHandlers;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFragment extends Fragment implements SpeechRecognitionHelper.SpeechRecognitionListener {

    ServerRequestHandler requestHandler;
    UserSession userSession;
    EditText message;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FloatingActionButton submit, mic_button;
    ChatAdapter chatAdapter;
    LinearLayoutManager layoutManager;
    List<ChatModel> chatList = new ArrayList<>();
    SpeechRecognitionHelper speechRecognitionHelper;
    String starter = "Hey there! \uD83D\uDC4B Ready to discover some awesome playlists? Just tell me how you're feeling, and I'll find the perfect tunes to match your mood. Let's vibe out together! \uD83D\uDE0E\uD83C\uDFB6\"";

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        requestHandler = new ServerRequestHandler(getActivity());
        userSession = SharedPref.getUserSession(getActivity());
        speechRecognitionHelper = new SpeechRecognitionHelper(getContext(), this);

        message = v.findViewById(R.id.message);
        submit = v.findViewById(R.id.send_button);
        mic_button = v.findViewById(R.id.mic_button);
        recyclerView = v.findViewById(R.id.recyclerView);
        progressBar = v.findViewById(R.id.progressBar);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChatModel chats = new ChatModel();
        chats.setLogo("/media/gpt_logo.png");
        chats.setName("EmoTune Bot");
        chats.setMessage(starter);
        appendMessage(chats);

        submit.setOnClickListener(v -> {
            if (message.length() == 0) {
                UiHandlers.shortToast(getActivity(), "Enter message");
            } else {
                UserMessage userMessage = new UserMessage(userSession.getId(), message.getText().toString());

                ChatModel chat = new ChatModel();
                chat.setLogo(USER_LOGO);
                chat.setName(userSession.getName());
                chat.setMessage(message.getText().toString());
                message.setText("");

                appendMessage(chat);
                sendMessageBot(userMessage);

            }
        });

        mic_button.setOnClickListener(v -> {
            message.setHint("Listening...");
            mic_button.setEnabled(false);
            speechRecognitionHelper.startListening();
        });
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                watchEditText(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                watchEditText(s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                watchEditText(s);
            }
        });
    }

    private void watchEditText(CharSequence s) {
        if (s.toString().trim().isEmpty()) {
            mic_button.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        } else {
            mic_button.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        }
    }

    private void sendMessageBot(UserMessage userMessage) {
        submit.setEnabled(false);
        String pattern = "^Name: ([A-Za-z0-9\\s]+), ID: (\\d+)$";

        requestHandler.sendMessage(userMessage, new ServerResponseListener<List<BotResponse>>() {
            @Override
            public void onSuccess(List<BotResponse> response) {
                submit.setEnabled(true);
                StringBuilder combinedText = new StringBuilder();
                if (response.size() > 0) {
                    ChatModel chatModel = new ChatModel();
                    chatModel.setShowClick(false);
                    chatModel.setLogo("/media/gpt_logo.png");
                    chatModel.setName("EmoTune Bot");
                    for (BotResponse bot : response) {
                        Matcher matcher = Pattern.compile(pattern).matcher(bot.getText());
                        if (matcher.find()) {
                            String name = matcher.group(1); // Get the name from the first capturing group
                            String id = matcher.group(2);   // Get the ID from the second capturing group

                            chatModel.setPlaylistName(name);
                            chatModel.setPlaylistId(id);
                            chatModel.setShowClick(true);
                            // Move to the next loop iteration
                            continue;
                        }

                        combinedText.append(bot.getText()).append("\n"); // Append the text and a newline
                    }
                    chatModel.setMessage(combinedText.toString());

                    appendMessage(chatModel);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                submit.setEnabled(true);
                UiHandlers.shortToast(getActivity(), errorMessage);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void appendMessage(ChatModel chat) {
        chatList.add(chat);
        chatAdapter = new ChatAdapter(chatList, getActivity());
        recyclerView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
    }


    @Override
    public void onSpeechRecognitionResult(String recognizedText) {
        message.setText(recognizedText);
        message.setHint(" Type Message...");
        mic_button.setEnabled(true);
    }

    @Override
    public void onNoSpeechDetected() {
        message.setHint(" Type Message...");
        mic_button.setEnabled(true);
    }

    @Override
    public void onListeningPaused() {
        message.setHint(" Type Message...");
        mic_button.setEnabled(true);
    }

    @Override
    public void onListeningStopped() {
        message.setHint(" Type Message...");
        mic_button.setEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Additional setup or start listening if

    }

    @Override
    public void onPause() {
        super.onPause();
        speechRecognitionHelper.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        speechRecognitionHelper.stopListening();
    }
}