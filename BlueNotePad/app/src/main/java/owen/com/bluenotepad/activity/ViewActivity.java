package owen.com.bluenotepad.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import owen.com.bluenotepad.R;
import owen.com.bluenotepad.model.NoteModel;

/**
 * 查看笔记
 *
 * Created by Owen on 2015/8/24.
 */
public class ViewActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_activity_view)
    Toolbar mToolbar;

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (null != intent) {
            long noteId = intent.getLongExtra("id", -1L);

            if (-1 == noteId) {
                finish();
                return;
            }

            NoteModel noteModel = NoteModel.queryById(noteId);
            String content = noteModel.content;
            tvContent.setText(content);
        }
    }

    public static void actionStart(Context context, long noteId) {
        Intent intent = new Intent(context, ViewActivity.class);
        intent.putExtra("id", noteId);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
