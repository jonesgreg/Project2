package com.example.gregoryjones.project2;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv = (TextView) findViewById(R.id.textView_GPSDATA);
        tv.setText(getIntent().getStringExtra("GPS DATA"));


        final String key = "n1dRfP+BPwABCDEpDQe+MP==";

        Button bt = (Button) findViewById(R.id.button_EncryptedAES);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TextView tv = (TextView) findViewById(R.id.textView_GPSDATA);
                    String plaintext = tv.getText().toString();
                    String ciphertext = encrypt(key, plaintext);
                    TextView tv_display = (TextView) findViewById(R.id.textView_EncryptedDataStatement);
                    tv_display.setText(ciphertext);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        Button bt_decrypt = (Button) findViewById(R.id.button_DecryptAES);
        bt_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextView tv_encrypted = (TextView) findViewById(R.id.textView_EncryptedDataStatement);
                    String encryptedData = tv_encrypted.getText().toString();
                    String decryptedtext = decrypt(key, encryptedData);
                    TextView tv_display = (TextView) findViewById(R.id.textView_EncryptedDataStatement2);
                    tv_display.setText(decryptedtext);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    public String encrypt(String seed, String  cleartext) throws  Exception {
        byte[] rawkey = getRawKey(seed);
        byte[] result = encrypt(rawkey, cleartext.getBytes());
        String encrypt_message = toHex(result);
        return encrypt_message;
    }

    public String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawkey = getRawKey(seed);
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawkey,enc);
        String decrypted_message = new String(result);
        return  decrypted_message;
    }

    private byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new  SecretKeySpec(raw, "AES");
        Cipher cipher =Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;

    }

    private byte[] decrypt(byte [] raw, byte [] encrypted) throws Exception {
        SecretKeySpec skeySpec = new  SecretKeySpec(raw, "AES");
        Cipher cipher =Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    public byte[] toByte(String hexString){
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++){
            result[i]=Integer.valueOf(hexString.substring(2*i,2*i+2),16).byteValue();
        }
        return result;
    }

    public String toHex(byte[] buffer){
        if (buffer == null)
            return "";
        StringBuffer result = new StringBuffer(2*buffer.length);
        for (int i = 0; i < buffer.length; i++){
            appendHex(result,buffer[i]);
        }
        return result.toString();
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
}

