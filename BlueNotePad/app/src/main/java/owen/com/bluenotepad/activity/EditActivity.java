package owen.com.bluenotepad.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import owen.com.bluenotepad.R;
import owen.com.bluenotepad.model.NoteModel;

/**
 * 编辑笔记界面
 *
 * Created by Owen on 2015/8/24.
 */
public class EditActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_activity_edit)
    Toolbar mToolbar;

    @Bind(R.id.et_content)
    EditText mEdtContent;

    @Bind(R.id.edit_root_layout)
    LinearLayout mRootLayout;

    NoteModel mNoteModel = new NoteModel();
    long noteId = -1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (null != intent) {
            noteId = intent.getLongExtra("id", -1L);

            if (-1L != noteId) {
                mNoteModel = NoteModel.queryById(noteId);
                String content = mNoteModel.content;
                mEdtContent.setText(content);
                mEdtContent.requestFocus();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String content = mEdtContent.getText().toString();

        switch (item.getItemId()) {
            case android.R.id.home:
                if (!TextUtils.isEmpty(content)) {
                    mNoteModel.time = System.currentTimeMillis();
                    mNoteModel.content = content;
                    mNoteModel.save();
                }

                finish();
                return true;

            case R.id.save:
                if (TextUtils.isEmpty(content)) {
                    Snackbar.make(mRootLayout, getString(R.string.no_content_no_save), Snackbar.LENGTH_LONG).show();
                    return true;
                }

                mNoteModel.time = System.currentTimeMillis();
                mNoteModel.content = content;
                mNoteModel.save();

                finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EditActivity.class);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, long noteId) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("id", noteId);
        context.startActivity(intent);
    }

}
