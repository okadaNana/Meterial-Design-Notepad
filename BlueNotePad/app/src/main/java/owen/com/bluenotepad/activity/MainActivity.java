package owen.com.bluenotepad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import owen.com.bluenotepad.R;
import owen.com.bluenotepad.adapter.NoteAdapter;
import owen.com.bluenotepad.model.NoteModel;
import owen.com.bluenotepad.views.EmptyRecyclerView;

/**
 * 主界面
 *
 * Created by Owen on 2015/8/24.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_activity_main)
    Toolbar mToolbar;

    @Bind(R.id.recycler_note)
    EmptyRecyclerView mRecyclerNote;

    @Bind(R.id.tv_empty)
    TextView mTvEmpty;

    NoteAdapter mNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        handleSend(getIntent());
    }

    private void handleSend(Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SEND)) {
            String content = intent.getStringExtra(Intent.EXTRA_TEXT);
            long time = System.currentTimeMillis();
            NoteModel note = new NoteModel();
            note.content = content;
            note.time = time;
            note.save();
        }
    }

    private void initView() {
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        mNoteAdapter = new NoteAdapter(this);
        mRecyclerNote.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerNote.setAdapter(mNoteAdapter);
        mRecyclerNote.setEmptyView(mTvEmpty);
    }

    @OnClick(R.id.fab_add)
    public void addNote() {
        EditActivity.actionStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<NoteModel> noteList = NoteModel.queryNoteList();
        mNoteAdapter.setNoteList(noteList);
        mNoteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_about == item.getItemId()) {
            AboutActivity.actionStart(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
