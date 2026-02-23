package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class RoomFragment extends Fragment {

    private final String[] rooms = {
            "Phòng khách", "Phòng ngủ chính", "Phòng ngủ 2",
            "Phòng ăn", "Nhà bếp", "Phòng làm việc"
    };
    private final String[] roomEmojis = {"🛋️","🛏️","🛏️","🍽️","🍳","💼"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        LinearLayout grid = view.findViewById(R.id.roomGrid);

        for (int i = 0; i < rooms.length; i += 2) {
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowLp.setMargins(0, 0, 0, 12);
            row.setLayoutParams(rowLp);

            row.addView(createRoomCard(rooms[i], roomEmojis[i]));
            if (i + 1 < rooms.length) row.addView(createRoomCard(rooms[i+1], roomEmojis[i+1]));
            grid.addView(row);
        }
        return view;
    }

    private View createRoomCard(String roomName, String emoji) {
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.feature_card_bg);
        card.setPadding(32, 32, 32, 32);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        lp.setMargins(8, 0, 8, 0);
        card.setLayoutParams(lp);

        // Elevation bằng code
        card.setElevation(8f);
        card.setBackgroundResource(R.drawable.feature_card_bg);

        TextView tvEmoji = new TextView(getContext());
        tvEmoji.setText(emoji);
        tvEmoji.setTextSize(36);
        card.addView(tvEmoji);

        TextView tvName = new TextView(getContext());
        tvName.setText(roomName);
        tvName.setTextSize(14);
        tvName.setTextColor(0xFF1A3A5C);
        tvName.setTypeface(null, android.graphics.Typeface.BOLD);
        LinearLayout.LayoutParams nameLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameLp.setMargins(0, 12, 0, 0);
        tvName.setLayoutParams(nameLp);
        card.addView(tvName);

        card.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RoomDetailActivity.class);
            intent.putExtra("room_name", roomName);
            startActivity(intent);
        });
        return card;
    }
}