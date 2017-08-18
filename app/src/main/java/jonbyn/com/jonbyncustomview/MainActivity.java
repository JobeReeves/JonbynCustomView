package jonbyn.com.jonbyncustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import widget.ValidCodeView;

public class MainActivity extends AppCompatActivity {

    ValidCodeView validCodeView;
    Button btnSendValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validCodeView = (ValidCodeView) findViewById(R.id.et_valid);
        btnSendValid = (Button) findViewById(R.id.btn_send_valid);
        btnSendValid.setEnabled(false);
        btnSendValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, validCodeView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        validCodeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(6 == s.length()) {
                    btnSendValid.setEnabled(true);
                } else {
                    btnSendValid.setEnabled(false);
                }
            }
        });
    }
}
