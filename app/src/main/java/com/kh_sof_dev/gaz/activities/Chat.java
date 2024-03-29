
package com.kh_sof_dev.gaz.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kh_sof_dev.gaz.Adapters.Chat_adapter;
import com.kh_sof_dev.gaz.Classes.Firebase.NewMessage;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chat extends AppCompatActivity {
    ImageView Send_icon, back_btn, camera_icon;
    EditText msg_et;
    RecyclerView chat_RV;
    public static final String order_id = "order_id";
    public Context context;
    Chat_adapter chatAdapter;
    List<NewMessage> msg_arraylist;
    DatabaseReference reference;

    boolean isRead = false;
    private static final int GET_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_chat);
        //************************Definitions***********************************/
        context = this;
        Send_icon = findViewById(R.id.Send_icon);
        back_btn = findViewById(R.id.back_btn);
        camera_icon = findViewById(R.id.camera_icon);
        msg_et = findViewById(R.id.msg_et);
        chat_RV = findViewById(R.id.RV);
        msg_arraylist = new ArrayList<>();
        //**********************************************************************/
        //***********************SharedPreference******************************/
        final user_info user_info = new user_info(getApplicationContext());
        //******************************Adapter*********************************/
        chatAdapter = new Chat_adapter(context, msg_arraylist);
//        chat_RV.setHasFixedSize(true);
        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
        chat_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        chat_RV.setAdapter(chatAdapter);

        //************************DatabaseReference******************************/

        String orderId = "0";
        if (getIntent().hasExtra(order_id))
            orderId = getIntent().getStringExtra(order_id);
        reference = FirebaseDatabase.getInstance().getReference().child("messages").child(orderId != null ? orderId : "0");//"NewBranchForTesting"
        //***********************************************************************/

        //*******************************Actions*********************************/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        camera_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatchFrom_GALLERY();
            }
        });
        Send_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!msg_et.getText().toString().trim().equals("")) {

                    String key = reference.push().getKey();
                    NewMessage message = new NewMessage();
                    message.setMsg(msg_et.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d h:m aa", Locale.ENGLISH);
                    String currentDateandTime = sdf.format(new Date());
                    try {
                        Date mDate = sdf.parse(currentDateandTime);
                        message.setLong_time(mDate.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    long timeInMilliseconds = mDate.getTime();
                    message.setDate_time(currentDateandTime);
                    message.setSender_id(user_info.getId());
                    message.setSender_img(user_info.getImag());
                    message.setSender_name(user_info.getName());
                    message.setMsgID(key);

                    assert key != null;
                    reference.child(key).setValue(message);
                    msg_et.setText("");
                }
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //************************************************************************/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //***new */
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        //******** UploadImgType  ====  2 , 3 , 4   *************/
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void CatchFrom_GALLERY() {
        android.content.Intent intent = new Intent(android.content.Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(intent, GET_FROM_GALLERY);
    }

    private void updateConversation(DataSnapshot dataSnapshot) {
        NewMessage message = dataSnapshot.getValue(NewMessage.class);
        msg_arraylist.add(message);
        chatAdapter.notifyDataSetChanged();
        chat_RV.scrollToPosition(msg_arraylist.size() - 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRead = true;
    }
}