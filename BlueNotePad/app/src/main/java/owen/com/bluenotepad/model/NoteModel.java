package owen.com.bluenotepad.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * 笔记
 *
 * Created by Owen on 2015/8/24.
 */
@Table(name="note")
public class NoteModel extends BaseModel {

    @Column(name="content")
    public String content;

    @Column(name="time")
    public long time;

    public NoteModel() {
    }

    public NoteModel(String content, long time) {
        this.content = content;
        this.time = time;
    }

    public static List<NoteModel> queryNoteList() {
        List<NoteModel> modelList = new Select().from(NoteModel.class).orderBy("time desc").execute();
        return modelList;
    }

    public static NoteModel queryById(long id) {
        NoteModel model = new Select().from(NoteModel.class).where("Id = ?", id).executeSingle();
        return model;
    }

    public static NoteModel deleteById(long id) {
        NoteModel model = new Delete().from(NoteModel.class).where("Id = ?", id).executeSingle();
        return model;
    }

}
