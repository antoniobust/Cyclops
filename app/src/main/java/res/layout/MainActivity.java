package res.layout;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mdmobile.pocketconsole.BuildConfig;
import com.mdmobile.pocketconsole.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    //Bottom navigation bar, navigation listener
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_devices:
                    mTextMessage.setText(R.string.title_devices);
                    return true;
                case R.id.navigation_profiles:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
                case R.id.navigation_server:
                    mTextMessage.setText(R.string.title_server);
                    return true;
                case R.id.navigation_users:
                    mTextMessage.setText(R.string.title_users);
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            navigation.setItemIconTintList(getColorStateList(R.color.color_state_list));
        }else{
            getResources().getColorStateList((R.color.color_state_list),this.getTheme());
        }
    }

}
