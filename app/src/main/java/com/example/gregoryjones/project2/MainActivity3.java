package com.example.gregoryjones.project2;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigInteger;


public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = (TextView) findViewById(R.id.textView_GPSDATA);
        tv.setText(getIntent().getStringExtra("GPS DATA"));


        final String key = "n1dRfP+BPwABCDEpDQe+MP==";

        Button bt = (Button) findViewById(R.id.button_EncryptedXOR);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TextView tv = (TextView) findViewById(R.id.textView_GPSDATA);
                    String plaintext = tv.getText().toString();
                    String ciphertext = encrypt(key, plaintext);
                    TextView tv_display = (TextView) findViewById(R.id.textView_EncryptedDataStatement3);
                    tv_display.setText(ciphertext);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button bt_decrypt = (Button) findViewById(R.id.button_DecryptXOR);
        bt_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextView tv_encrypted = (TextView) findViewById(R.id.textView_EncryptedDataStatement3);
                    String encryptedData = tv_encrypted.getText().toString();
                    String decryptedtext = decrypt(key, encryptedData);
                    TextView tv_display = (TextView) findViewById(R.id.textView_EncryptedDataStatement4);
                    tv_display.setText(decryptedtext);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
    }



    public String encrypt(String seed, String cleartext)throws  Exception {
        BigInteger binary_original = new BigInteger(cleartext.getBytes());
        BigInteger binary_key = new BigInteger(seed.getBytes());
        BigInteger binary_encrypted = binary_original.xor(binary_key);
        return binary_encrypted.toString(2);

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


