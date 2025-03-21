package com.example.lawconnect;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChatBot extends AppCompatActivity {
    private WebView webView;
    private ProgressBar loadingProgress;
    private TextView errorMessage;
    private static final String TAG = "ChatbotActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_bot);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize UI elements
        webView = findViewById(R.id.chatbot_webview);
        loadingProgress = findViewById(R.id.loading_progress);
        errorMessage = findViewById(R.id.error_message);

        // Configure WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(TAG, "WebView Console: " + consoleMessage.message() + " -- From line " +
                        consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingProgress.setVisibility(View.GONE);
                errorMessage.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);

                String js = "javascript:(function() {" +
                        "window.botpressWebChat.sendEvent({" +
                        "  type: 'setConfig'," +
                        "  config: {" +
                        "    composerPlaceholder: 'Ask a legal question...', " +
                        "    themeColor: '#6200EA', " +
                        "    textColorOnTheme: '#FFFFFF', " +
                        "    backgroundColor: '#FFFFFF', " +
                        "    showPoweredBy: false " +
                        "  }" +
                        "});" +
                        "})()";
                webView.evaluateJavascript(js, null);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e(TAG, "WebView Error: " + description + " (Code: " + errorCode + ", URL: " + failingUrl + ")");
                loadingProgress.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText("Failed to load chatbot: " + description);
            }
        });

        // Load the published Botpress Webchat URL
        String botpressUrl = "https://cdn.botpress.cloud/webchat/v2.2/shareable.html?configUrl=https://files.bpcontent.cloud/2025/03/13/08/20250313083219-BY6BMC2R.json";
        Log.d(TAG, "Loading URL: " + botpressUrl);
        webView.loadUrl(botpressUrl);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

//    private void sendMessage() {
//        String userMessage = messageInput.getText().toString().trim();
//        if (userMessage.isEmpty()) return;
//
//        // Add user message to chat
//        messages.add("User: " + userMessage);
//        chatAdapter.notifyDataSetChanged();
//        messageInput.setText("");
//
//        // Send message to Botpress API
//        OkHttpClient client = new OkHttpClient();
//        String url = "https://api.botpress.cloud/v1/bots/ec699308-d269-408f-ae18-41dfc2758961/converse/user123";
//        String json = "{\"type\":\"text\",\"text\":\"" + userMessage + "\"}";
//
//        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .addHeader("Authorization", "Bearer bp_pat_Q9CWZTWLaAVGIOCvysA5Zl41j89ReTtM93GX") // Replace with your API token
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, java.io.IOException e) {
//                Log.e(TAG, "API request failed: " + e.getMessage());
//                runOnUiThread(() -> {
//                    messages.add("Bot: Error: " + e.getMessage());
//                    chatAdapter.notifyDataSetChanged();
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws java.io.IOException {
//                if (response.isSuccessful()) {
//                    String botResponse = response.body().string();
//                    Log.d(TAG, "API response: " + botResponse);
//                    runOnUiThread(() -> {
//                        String parsedResponse = parseBotResponse(botResponse);
//                        messages.add("Bot: " + parsedResponse);
//                        chatAdapter.notifyDataSetChanged();
//                    });
//                } else {
//                    Log.e(TAG, "API request unsuccessful: " + response.code() + " " + response.message());
//                    runOnUiThread(() -> {
//                        messages.add("Bot: Error: " + response.code() + " " + response.message());
//                        chatAdapter.notifyDataSetChanged();
//                    });
//                }
//            }
//        });
//    }
//
//    private String parseBotResponse(String response) {
//        // Botpress typically returns a JSON array of responses
//        // Example: [{"type":"text","text":"Hello!"}]
//        try {
//            org.json.JSONArray jsonArray = new org.json.JSONArray(response);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
//                if (jsonObject.getString("type").equals("text")) {
//                    return jsonObject.getString("text");
//                }
//            }
//            return "No text response found";
//        } catch (Exception e) {
//            Log.e(TAG, "Error parsing response: " + e.getMessage());
//            return "Error parsing response: " + response;
//        }
//    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}