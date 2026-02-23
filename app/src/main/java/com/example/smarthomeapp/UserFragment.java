package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        Spinner spinnerLang = view.findViewById(R.id.spinnerLanguage);
        Button btnLogout    = view.findViewById(R.id.btnLogout);

        String user = AccountManager.getCurrentUser(requireContext());
        tvUsername.setText(user);

        // Language spinner
        String[] languages = {"Tiếng Việt", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(adapter);

        btnLogout.setOnClickListener(v -> {
            AccountManager.logout(requireContext());
            Intent intent = new Intent(requireContext(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        return view;
    }
}