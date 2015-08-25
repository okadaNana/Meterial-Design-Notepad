package owen.com.bluenotepad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import owen.com.bluenotepad.R;
import owen.com.bluenotepad.activity.EditActivity;
import owen.com.bluenotepad.activity.ViewActivity;
import owen.com.bluenotepad.model.NoteModel;

/**
 * 笔记列表的Adapter
 *
 * Created by Owen on 2015/8/24.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    private Context mContext;
    private List<NoteModel> mNoteList = new ArrayList<>();
    private LayoutInflater mInflater;

    public NoteAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setNoteList(List<NoteModel> noteList) {
        mNoteList.clear();
        mNoteList.addAll(noteList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_note, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteModel note = mNoteList.get(position);

        holder.mTvContent.setText(note.content);
        holder.mTvTime.setText(dateFormat.format(note.time));
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tv_content)
        TextView mTvContent;

        @Bind(R.id.tv_time)
        TextView mTvTime;

        @Bind(R.id.layout_content)
        View mLayoutContent;

        @Bind(R.id.tv_edit)
        View mViewEditAction;

        @Bind(R.id.tv_delete)
        View mViewDeleteAction;

        @Bind(R.id.swipe)
        SwipeLayout mSwipeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mLayoutContent.setOnClickListener(this);
            mViewEditAction.setOnClickListener(this);
            mViewDeleteAction.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            int position = this.getAdapterPosition();
            NoteModel note = mNoteList.get(position);

            switch (id) {
                case R.id.layout_content:
                    ViewActivity.actionStart(mContext, note.getId());
                    mSwipeLayout.close();
                    break;

                case R.id.tv_edit:
                    EditActivity.actionStart(mContext, note.getId());
                    mSwipeLayout.close();
                    break;

                case R.id.tv_delete:
                    note.delete();
                    mNoteList.remove(note);
                    notifyItemRemoved(position);
                    break;

                default:
                    break;
            }
        }
    }

}
