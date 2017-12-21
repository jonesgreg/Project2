package com.example.gregoryjones.project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigInteger;

public class MainActivity5 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = (TextView) findViewById(R.id.textView_GPSDATA);
        tv.setText(getIntent().getStringExtra("GPS DATA"));
        Intent intent = getIntent();
        final String str = intent.getStringExtra("text");
        final String keyModifier = KeyModifier(str.toCharArray());






        Button bt_encryptXOR = (Button) findViewById(R.id.button_EncryptedXOR);
        bt_encryptXOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TextView tv = (TextView) findViewById(R.id.textView_GPSDATA);
                    String plaintext = tv.getText().toString();
                    String ciphertext = encrypt(keyModifier , plaintext);
                    TextView tv_display = (TextView) findViewById(R.id.textView_EncryptedDataStatement);
                    tv_display.setText(ciphertext);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });



        Button bt_decryptXOR = (Button) findViewById(R.id.button_DecryptXOR);
        bt_decryptXOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextView tv_encrypted = (TextView) findViewById(R.id.textView_EncryptedDataStatement);
                    String encryptedData = tv_encrypted.getText().toString();
                    String decryptedtext = decrypt(keyModifier , encryptedData);
                    TextView tv_display = (TextView) findViewById(R.id.textView_EncryptedDataStatement2);
                    tv_display.setText(decryptedtext);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });




    } // end of the create Method


    public String KeyModifier(char[] keyArr) {
        //DEFAULT KEY
        String key = "n1dRfP+BPwABCDEpDQe+MP==";
        char[] Key_Array = key.toCharArray();
        if (Key_Array.length > keyArr.length) {
            for (int i = 0; i < keyArr.length; i++) {
                if (i % 2 != 0) {
                    Key_Array[i] = keyArr[i];
                }
            }
            String final_key = String.valueOf(Key_Array);
            return final_key;
        } else {
            for (int i = 0; i < Key_Array.length; i++) {
                if (i % 2 == 0) {
                    keyArr[i] = Key_Array[i];

                }
            }
            String final_key = String.valueOf(Key_Array);
            return final_key;
        }

    }






    public String encrypt(String seed, String cleartext)throws  Exception {
        BigInteger binary_original = new BigInteger(cleartext.getBytes());
        BigInteger binary_key = new BigInteger(seed.getBytes());
        BigInteger binary_encrypted = binary_original.xor(binary_key);
        return binary_encrypted.toString(2);
        //return new String(binary_encrypted.toByteArray());

    }

    public String decrypt(String seed, String cleartext) throws Exception {
        BigInteger binary_original = new BigInteger(cleartext,2);
        BigInteger binary_key = new BigInteger(seed.getBytes());
        BigInteger binary_encrypted = binary_key.xor(binary_original);
        String decrypted = new String(binary_encrypted.toByteArray());
        return decrypted;


    }
    private final String HEX = "0123456789ABCDEF";
    private void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }

    private byte[] getRawKey(String keyvalue) {
        byte[] key = Base64.decode(keyvalue.getBytes(), 0);
        return key;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
} // end of the class

