package com.test.ieltsmarkstoband.ui.Feedback;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.ieltsmarkstoband.R;

import static android.app.Activity.RESULT_OK;


public class FeedbackFragment extends Fragment {

    LinearLayout linearLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendFeedback();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.feedback_fragment, null);
        linearLayout = view.findViewById(R.id.feedbackButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8000) {
            if (resultCode == RESULT_OK) {
                showDialog();
            }
        }
    }

    private void sendFeedback() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ieltsmarkstobandscore@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "APP FEEDBACK");
        intent.putExtra(Intent.EXTRA_TEXT, "Write your feedback here :");
        startActivityForResult(intent, 800);
    }

    private void showDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.thanks_fragment, null);
        alert.setView(view);
        final AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        new CountDownTimer(10000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                alertDialog.dismiss();
            }
        }.start();
    }


}
