package com.shop_manager.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shop_manager.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EventActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_event);
        db = FirebaseFirestore.getInstance();

        EditText etTitle = findViewById(R.id.et_title);
        EditText etMessage = findViewById(R.id.et_message);
        Button btn = findViewById(R.id.btn_send);

        btn.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String msg = etMessage.getText().toString().trim();
            if (title.isEmpty() || msg.isEmpty()) { Toast.makeText(this, "제목/메시지를 입력하세요", Toast.LENGTH_SHORT).show(); return; }

            Map<String, Object> event = new HashMap<>();
            event.put("title", title);
            event.put("message", msg);
            event.put("createdAt", System.currentTimeMillis());
            db.collection("events").add(event)
                    .addOnSuccessListener(doc -> Toast.makeText(this, "이벤트 저장됨: 서버에서 푸시 발송 처리", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}
