package com.example.smarthome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.*;

public class RoomDetailActivity extends AppCompatActivity {

    private String roomName;
    private LinearLayout layoutDevices;
    private TextView tvRoomName, tvRoomTemp, tvRoomHumid;
    private Handler handler = new Handler();
    private Random random = new Random();
    private float temp = 26f, humid = 60f;

    private static final String[] FAKE_DEVICES = {
            "Đèn LED", "Điều hoà", "Quạt điện",
            "Smart Plug", "Cảm biến chuyển động", "Camera IP", "Màn hình thông minh"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_room_detail);

        roomName        = getIntent().getStringExtra("room_name");
        tvRoomName      = findViewById(R.id.tvRoomDetailName);
        tvRoomTemp      = findViewById(R.id.tvRoomTemp);
        tvRoomHumid     = findViewById(R.id.tvRoomHumid);
        layoutDevices   = findViewById(R.id.layoutRoomDevices);

        tvRoomName.setText(roomName);
        updateSensor();
        startSensorUpdates();
        loadDevices();

        findViewById(R.id.btnAddDevice).setOnClickListener(v -> showScanDialog());
        findViewById(R.id.btnRoomBack).setOnClickListener(v -> finish());
    }

    private void startSensorUpdates() {
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                temp  += (random.nextFloat() - 0.5f) * 0.3f;
                humid += (random.nextFloat() - 0.5f) * 0.5f;
                temp  = Math.max(20f, Math.min(35f, temp));
                humid = Math.max(40f, Math.min(85f, humid));
                updateSensor();
                handler.postDelayed(this, 4000);
            }
        }, 4000);
    }

    private void updateSensor() {
        tvRoomTemp.setText(String.format("🌡️ %.1f°C", temp));
        tvRoomHumid.setText(String.format("💧 %.0f%%", humid));
    }

    private String getPrefKey() { return "room_devices_" + roomName.replace(" ", "_"); }

    private Set<String> getDevices() {
        SharedPreferences prefs = getSharedPreferences("RoomDevices", Context.MODE_PRIVATE);
        return new HashSet<>(prefs.getStringSet(getPrefKey(), new HashSet<>()));
    }

    private void saveDevices(Set<String> devices) {
        getSharedPreferences("RoomDevices", Context.MODE_PRIVATE)
                .edit().putStringSet(getPrefKey(), devices).apply();
    }

    private void loadDevices() {
        layoutDevices.removeAllViews();
        Set<String> devices = getDevices();
        if (devices.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("Chưa có thiết bị. Nhấn 'Thêm thiết bị' để quét.");
            empty.setTextColor(0xFFAABBD0);
            empty.setTextSize(14);
            empty.setPadding(8, 24, 8, 24);
            layoutDevices.addView(empty);
            return;
        }
        for (String entry : devices) {
            String[] parts = entry.split("\\|\\|\\|");
            String name = parts[0];
            boolean isOn = parts.length > 1 && parts[1].equals("1");
            addDeviceRow(name, isOn);
        }
    }

    private void addDeviceRow(String deviceName, boolean isOn) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(android.view.Gravity.CENTER_VERTICAL);
        row.setPadding(0, 16, 0, 16);

        TextView tvName = new TextView(this);
        tvName.setText(deviceName);
        tvName.setTextSize(15);
        tvName.setTextColor(0xFF1A3A5C);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        tvName.setLayoutParams(lp);

        Switch sw = new Switch(this);
        sw.setChecked(isOn);
        sw.setThumbTintList(android.content.res.ColorStateList.valueOf(0xFF5B9BD5));
        sw.setTrackTintList(android.content.res.ColorStateList.valueOf(0xFFB8D4EE));

        sw.setOnCheckedChangeListener((btn, checked) -> {
            updateDeviceState(deviceName, checked);
        });

        // Long press to delete
        row.setOnLongClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xoá thiết bị")
                    .setMessage("Xoá " + deviceName + " khỏi phòng?")
                    .setPositiveButton("Xoá", (d, w) -> removeDevice(deviceName))
                    .setNegativeButton("Huỷ", null)
                    .show();
            return true;
        });

        row.addView(tvName);
        row.addView(sw);

        // Divider
        LinearLayout wrapper = new LinearLayout(this);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.addView(row);
        View div = new View(this);
        div.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        div.setBackgroundColor(0xFFEAF2FB);
        wrapper.addView(div);
        layoutDevices.addView(wrapper);
    }

    private void updateDeviceState(String name, boolean on) {
        Set<String> devices = getDevices();
        Set<String> updated = new HashSet<>();
        for (String d : devices) {
            if (d.split("\\|\\|\\|")[0].equals(name)) updated.add(name + "|||" + (on ? "1" : "0"));
            else updated.add(d);
        }
        saveDevices(updated);
    }

    private void removeDevice(String name) {
        Set<String> devices = getDevices();
        devices.removeIf(d -> d.split("\\|\\|\\|")[0].equals(name));
        saveDevices(devices);
        loadDevices();
    }

    private void showScanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_scan, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        ProgressBar progress = dialogView.findViewById(R.id.scanProgress);
        TextView tvScanStatus = dialogView.findViewById(R.id.tvScanStatus);
        LinearLayout layoutFound = dialogView.findViewById(R.id.layoutFoundDevices);

        // Mô phỏng scan 2 giây
        handler.postDelayed(() -> {
            progress.setVisibility(View.GONE);
            tvScanStatus.setText("Tìm thấy thiết bị:");
            layoutFound.setVisibility(View.VISIBLE);

            // Hiện 3-4 thiết bị ngẫu nhiên chưa được thêm
            Set<String> existing = getDevices();
            Set<String> existingNames = new HashSet<>();
            for (String e : existing) existingNames.add(e.split("\\|\\|\\|")[0]);

            List<String> available = new ArrayList<>();
            for (String d : FAKE_DEVICES) if (!existingNames.contains(d)) available.add(d);
            Collections.shuffle(available);
            int count = Math.min(4, available.size());

            for (int i = 0; i < count; i++) {
                String dName = available.get(i);
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setGravity(android.view.Gravity.CENTER_VERTICAL);
                row.setPadding(8, 16, 8, 16);

                TextView tv = new TextView(this);
                tv.setText("📡  " + dName);
                tv.setTextSize(14);
                tv.setTextColor(0xFF1A3A5C);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                tv.setLayoutParams(lp);

                Button btnAdd = new Button(this);
                btnAdd.setText("Thêm");
                btnAdd.setTextColor(0xFFFFFFFF);
                btnAdd.setBackgroundResource(R.drawable.btn_primary_bg);
                btnAdd.setTextSize(12);
                btnAdd.setPadding(24, 8, 24, 8);
                btnAdd.setStateListAnimator(null);

                btnAdd.setOnClickListener(v2 -> {
                    Set<String> devices = getDevices();
                    devices.add(dName + "|||0");
                    saveDevices(devices);
                    btnAdd.setText("✓");
                    btnAdd.setEnabled(false);
                    btnAdd.setBackgroundColor(0xFF4CAF50);
                    loadDevices();
                });

                row.addView(tv);
                row.addView(btnAdd);
                layoutFound.addView(row);
            }
            if (count == 0) {
                TextView noMore = new TextView(this);
                noMore.setText("Tất cả thiết bị đã được thêm.");
                noMore.setTextColor(0xFFAABBD0);
                layoutFound.addView(noMore);
            }
        }, 2000);

        dialogView.findViewById(R.id.btnCloseScan).setOnClickListener(v -> dialog.dismiss());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}