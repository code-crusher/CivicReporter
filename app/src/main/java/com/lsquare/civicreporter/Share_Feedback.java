package com.lsquare.civicreporter;



        import android.app.Activity;
        import android.app.Notification;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

/**
 * Created by Asheesh on 07-03-2015.
 */

public class Share_Feedback extends Activity implements View.OnClickListener{
    EditText et;
    Button bt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        et=(EditText)findViewById(R.id.editText);
        bt=(Button)findViewById(R.id.button);

        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String st;
        String[] TO = {"eklavya.lsquare@gmail.com"};
        String[] CC = {""};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        st=String.valueOf(et.getText());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your Feedback"+":"+st);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Share_Feedback.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
