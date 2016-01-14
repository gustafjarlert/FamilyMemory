package se.jarlert.familymemory;

import se.jarlert.familymemory.util.MemoryCell;
import se.jarlert.familymemory.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Memory extends Activity implements View.OnClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private Drawable cardback;
    private Drawable ballonger;
    private MemoryCell[] buttons;
    private MemoryCell button;
    private boolean otherCardUp;
    private int otherPicId;
    private int otherButtonUp;
    private int score;
    private ImageButton bottomImage;
    private TextView statusText;
    private Stack<MemoryCell> turnbackcards;
    private Resources res;
    private int clickCounter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memory);

//        final View controlsView = findViewById(R.id.fullscreen_content_controls);
//        final View contentView = findViewById(R.id.fullscreen_content);
        bottomImage = (ImageButton)findViewById(R.id.bottomimage) ;
        bottomImage.setBackground(cardback);
        LinkedList<Drawable> sortedPictures = new LinkedList<Drawable>();
        LinkedList<Drawable> randomPictures = new LinkedList<Drawable>();
        otherCardUp = false;
        otherPicId = -1;
        otherButtonUp = -1;
        clickCounter = 0;
        turnbackcards = new Stack<MemoryCell>();
        res = getResources();
        cardback = res.getDrawable(R.drawable.cardback);
        ballonger = res.getDrawable(R.drawable.ballonger);
        statusText = (TextView) findViewById(R.id.statustext);

        int buttonNumber=20;
        score = buttonNumber/2;
        ImageButton[] imageButtons = createImageButtonArray(buttonNumber);
        LinkedList<Integer> randomPicIds = createRandomOrder(buttonNumber / 2,18);
        ListIterator<Integer> randomIter = randomPicIds.listIterator();
        buttons = new MemoryCell[buttonNumber];
        for(int i=0;i<buttonNumber;i++) {
            int picId=randomIter.next();
            buttons[i] = new MemoryCell(imageButtons[i],i,picId);
            imageButtons[i].setOnClickListener(this);
        }
        bottomImage.setOnClickListener(this);

    }

    private ImageButton[] createImageButtonArray(int buttonNumber) {
        ImageButton[] buttons = new ImageButton[buttonNumber];
        ImageButton[] buttonNames = new ImageButton[20];
        buttonNames[0] = (ImageButton) findViewById(R.id.button1);
        buttonNames[1] = (ImageButton) findViewById(R.id.button2);
        buttonNames[2] = (ImageButton) findViewById(R.id.button3);
        buttonNames[3] = (ImageButton) findViewById(R.id.button4);
        buttonNames[4] = (ImageButton) findViewById(R.id.button5);
        buttonNames[5] = (ImageButton) findViewById(R.id.button6);
        buttonNames[6] = (ImageButton) findViewById(R.id.button7);
        buttonNames[7] = (ImageButton) findViewById(R.id.button8);
        buttonNames[8] = (ImageButton) findViewById(R.id.button9);
        buttonNames[9] = (ImageButton) findViewById(R.id.button10);
        buttonNames[10] = (ImageButton) findViewById(R.id.button11);
        buttonNames[11] = (ImageButton) findViewById(R.id.button12);
        buttonNames[12] = (ImageButton) findViewById(R.id.button13);
        buttonNames[13] = (ImageButton) findViewById(R.id.button14);
        buttonNames[14] = (ImageButton) findViewById(R.id.button15);
        buttonNames[15] = (ImageButton) findViewById(R.id.button16);
        buttonNames[16] = (ImageButton) findViewById(R.id.button17);
        buttonNames[17] = (ImageButton) findViewById(R.id.button18);
        buttonNames[18] = (ImageButton) findViewById(R.id.button19);
        buttonNames[19] = (ImageButton) findViewById(R.id.button20);

        for(int i = 0; i<buttonNumber; i++) {
            buttons[i] = buttonNames[i];
        }
        return buttons;
    }

    protected LinkedList<Integer> createRandomOrder(int number, int totalPic) {
        LinkedList<Integer> startNumbers = new LinkedList<Integer>();
        LinkedList<Integer> sortedNumbers = new LinkedList<Integer>();
        LinkedList<Integer> randomNumbers = new LinkedList<Integer>();
        for (int i = 0; i<totalPic; i++) {
            startNumbers.add(i);
        }
        for (int i = 0; i<number; i++) {
            int size = startNumbers.size();
            double randSeed = size*Math.random();
            Iterator<Integer> startNumbersIterator = startNumbers.listIterator();
            for (int j=1;j<randSeed;j++) {
                startNumbersIterator.next();
            }
            int chosen = startNumbersIterator.next();
            startNumbersIterator.remove();
            sortedNumbers.add(chosen);
            sortedNumbers.add(chosen);
        }
        while(!sortedNumbers.isEmpty()) {
            int i = sortedNumbers.size();
            double randSeed = i*Math.random();
            Iterator<Integer> sortedNumbersIterator= sortedNumbers.listIterator();
            for(int j=1;j<randSeed;j++) {
                sortedNumbersIterator.next();
            }
            randomNumbers.add(sortedNumbersIterator.next());
            sortedNumbersIterator.remove();
        }
        return randomNumbers;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onClick(View v) {
        int buttonClick = -1;
        clickCounter++;
        switch (v.getId()) {
            case R.id.button1:
                buttonClick = 0;
                break;
            case R.id.button2:
                buttonClick = 1;
                break;
            case R.id.button3:
                buttonClick = 2;
                break;
            case R.id.button4:
                buttonClick = 3;
                break;
            case R.id.button5:
                buttonClick = 4;
                break;
            case R.id.button6:
                buttonClick = 5;
                break;
            case R.id.button7:
                buttonClick = 6;
                break;
            case R.id.button8:
                buttonClick = 7;
                break;
            case R.id.button9:
                buttonClick = 8;
                break;
            case R.id.button10:
                buttonClick = 9;
                break;
            case R.id.button11:
                buttonClick = 10;
                break;
            case R.id.button12:
                buttonClick = 11;
                break;
            case R.id.button13:
                buttonClick = 12;
                break;
            case R.id.button14:
                buttonClick = 13;
                break;
            case R.id.button15:
                buttonClick = 14;
                break;
            case R.id.button16:
                buttonClick = 15;
                break;
            case R.id.button17:
                buttonClick = 16;
                break;
            case R.id.button18:
                buttonClick = 17;
                break;
            case R.id.button19:
                buttonClick = 18;
                break;
            case R.id.button20:
                buttonClick = 19;
                break;
            case R.id.bottomimage:
                if(score<1) {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                break;

        }
        if(buttonClick!=-1) {
            button = buttons[buttonClick];
            boolean unlocked = button.isUnlocked();
            if (unlocked) {
                button.changeVisibility();
                Drawable hiddenPicture = getPic(button.getPicId());
                button.getButton().setBackground(hiddenPicture);
                bottomImage.setBackground(hiddenPicture);
                if (otherCardUp) {
                    if (otherPicId != button.getPicId()) {
                        button.changeVisibility();
                        buttons[otherButtonUp].changeVisibility();
                        turnbackcards.add(button);
                        turnbackcards.add(buttons[otherButtonUp]);
                        v.postDelayed(updateTask, 1000);
                    } else if (button.getId()!=otherButtonUp) {
                        button.lock();
                        buttons[otherButtonUp].lock();
                        score--;
                    } else {
                        button.changeVisibility();
                        turnbackcards.add(button);
                        v.postDelayed(updateTask, 1000);
                    }
                    otherCardUp = false;
//                otherPicId = -1;
//                otherButtonUp = -1;
                } else {
                    otherCardUp = true;
                    otherPicId = button.getPicId();
                    otherButtonUp = button.getId();

                }
            }
            if (score < 1) {
                bottomImage.setBackground(ballonger);
                statusText.setText("Score: "+clickCounter);
            }
        }
    }

    private Drawable getPic(int picId) {
        String fileName = "pic"+picId;
        int fileId = res.getIdentifier(fileName,"drawable","se.jarlert.familymemory");
        try {
            return res.getDrawable(fileId);
        } catch (Resources.NotFoundException E) {
            System.err.println(fileName+" not working");
            System.out.println(fileName+" not working");
        }
        return cardback;
    }

    private Runnable updateTask = new Runnable () {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void run() {
            while (!turnbackcards.isEmpty()) {
                turnbackcards.pop().getButton().setBackground(cardback);
            }
            bottomImage.setBackground(cardback);

        }
    };


}
