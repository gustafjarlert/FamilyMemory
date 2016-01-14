package se.jarlert.familymemory.util;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageButton;

/**
 * Created by Gustaf on 2016-01-05.
 */
public class MemoryCell {


    private ImageButton button;
    private int id;
    private Drawable picture;
    private int picId;
    private boolean visible;
    private boolean unlocked;


    public MemoryCell(ImageButton iB,int idNr, Drawable pic, int picIdent) {
        button = iB;
        id = idNr;
        picture = pic;
        picId = picIdent;
        visible = false;
        unlocked = true;
    }

    public MemoryCell(ImageButton iB,int idNr, int picIdent) {
        button = iB;
        id = idNr;
        picId = picIdent;
        picture = null;
        visible = false;
        unlocked = true;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void lock() {
        unlocked = false;
    }

    public ImageButton getButton() {
        return button;
    }

    public Drawable getPicture() {
        return picture;
    }

    public int getId() {
        return id;
    }

    public int getPicId() {
        return picId;
    }

    public boolean changeVisibility() {
        if(visible) {
            visible = false;
        } else {
            visible = true;
        }
        return visible;
    }

}
