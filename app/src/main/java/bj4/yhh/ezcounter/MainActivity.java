package bj4.yhh.ezcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mCounter;
    private boolean mIsLock = false;

    private TextView mCount;
    private Button mAddOne, mReset, mLockReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mCount = (TextView) findViewById(R.id.counter);
        mAddOne = (Button) findViewById(R.id.add_one);
        mAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCount.setText(String.valueOf(++mCounter));
            }
        });
        mReset = (Button) findViewById(R.id.reset);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCounter = 0;
                mCount.setText(String.valueOf(0));
            }
        });
        mLockReset = (Button) findViewById(R.id.lock_reset);
        mLockReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsLock = !mIsLock;
                if (mIsLock) {
                    mLockReset.setText(R.string.unlock_reset);
                    mReset.setEnabled(false);
                } else {
                    mLockReset.setText(R.string.lock_reset);
                    mReset.setEnabled(true);
                }
            }
        });
    }
}
