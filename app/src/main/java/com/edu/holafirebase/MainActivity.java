package com.edu.holafirebase;

import static butterknife.ButterKnife.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.*;

public class MainActivity extends AppCompatActivity {

    private static final String PATH_START = "start";
    private static final String PATH_MESSAGE= "message";
    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.btnSend)
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final TextView tvMessage = findViewById(R.id.tvMessage);

        //crear variable para base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //refencia a la ruta que se quiere escribir o leer
        final DatabaseReference reference =
                database.getReference(PATH_START).child(PATH_MESSAGE);

                        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot
                                             dataSnapshot) {
                tvMessage.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError
                                            databaseError) {
                Toast.makeText(MainActivity.this, "Error al consultar  en firebase.",
                        Toast.LENGTH_LONG).show();
            }


        });
    }
    @OnClick(R.id.btnSend)
    public void onViewClicked()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference =
                database.getReference(PATH_START).child(PATH_MESSAGE);// "/-start/-message"
        reference.setValue(etMessage.getText().toString().trim());
    }

}

