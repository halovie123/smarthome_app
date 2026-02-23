package com.example.smarthome;

import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;
import java.util.Random;

public class HomeFragment extends Fragment {

    private TextView tvTemp, tvHumidity, tvActiveCount, tvGreeting, tvUser;
    private LinearLayout layoutActiveDevices;
    private Handler handler = new Handler();
    private Random random = new Random();
    private float currentTemp = 27f;
    private float currentHumid = 62f;

    // Danh sách thiết bị mô phỏng
    private final String[][] allDevices = {
            {"Phòng khách", "Đèn LED"},
            {"Phòng khách", "Điều hoà"},
            {"Phòng ngủ chính", "Đèn ngủ"},
            {"Nhà bếp", "Quạt thông gió"},
            {"Phòng làm việc", "Đèn bàn"}
    };
    private boolean[] deviceStates = {true, false, true, false, true};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvTemp          = view.findViewById(R.id.tvTemperature);
        tvHumidity      = view.findViewById(R.id.tvHumidity);
        tvActiveCount   = view.findViewById(R.id.tvHomeActiveCount);
        tvGreeting      = view.findViewById(R.id.tvHomeGreeting);
        tvUser          = view.findViewById(R.id.tvHomeUser);
        layoutActiveDevices = view.findViewById(R.id.layoutActiveDevices);

        String user = AccountManager.getCurrentUser(requireContext());
        tvUser.setText(user);
        updateSensorUI();
        updateDeviceList();
        startSensorUpdates();
        return view;
    }

    private void startSensorUpdates() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentTemp  += (random.nextFloat() - 0.5f) * 0.4f;
                currentHumid += (random.nextFloat() - 0.5f) * 0.6f;
                currentTemp   = Math.max(20f, Math.min(35f, currentTemp));
                currentHumid  = Math.max(40f, Math.min(85f, currentHumid));
                updateSensorUI();
                handler.postDelayed(this, 4000);
            }
        }, 4000);
    }

    private void updateSensorUI() {
        tvTemp.setText(String.format("%.1f°C", currentTemp));
        tvHumidity.setText(String.format("%.0f%%", currentHumid));
    }

    private void updateDeviceList() {
        layoutActiveDevices.removeAllViews();
        int count = 0;
        for (int i = 0; i < deviceStates.length; i++) {
            if (deviceStates[i]) {
                count++;
                LinearLayout row = new LinearLayout(getContext());
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 12, 0, 12);

                TextView dot = new TextView(getContext());
                dot.setText("●  ");
                dot.setTextColor(0xFF5B9BD5);
                dot.setTextSize(14);

                TextView name = new TextView(getContext());
                name.setText(allDevices[i][0] + " – " + allDevices[i][1]);
                name.setTextSize(14);
                name.setTextColor(0xFF1A3A5C);

                row.addView(dot);
                row.addView(name);
                layoutActiveDevices.addView(row);

                // Divider
                View div = new View(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
                div.setLayoutParams(lp);
                div.setBackgroundColor(0xFFEAF2FB);
                layoutActiveDevices.addView(div);
            }
        }
        tvActiveCount.setText(count + " thiết bị đang bật");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}