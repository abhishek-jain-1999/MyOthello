package com.example.abhishek.myothello;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public LinearLayout main;
    public OthelloButton[][] borad = new OthelloButton[8][8];
    public LinearLayout[] lv = new LinearLayout[8];
    boolean gameStatus = true;
    public int currentplayer = -1;
    public int countWhite;
    public int countBlack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = findViewById(R.id.main);
        setBoard();
    }

    public void setBoard() {
        gameStatus = true;
        countBlack = 2;
        countWhite = 2;

        LinearLayout top = new LinearLayout(this);
        LinearLayout mid = new LinearLayout(this);
        LinearLayout end = new LinearLayout(this);

        main.removeAllViews();

        top.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
        top.setLayoutParams(layoutParams1);
        end.setOrientation(LinearLayout.HORIZONTAL);
        end.setLayoutParams(layoutParams1);
        mid.setOrientation(LinearLayout.VERTICAL);
        layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 8);
        mid.setLayoutParams(layoutParams1);
        end.setBackgroundColor(Color.BLACK);


        ImageButton imageButton = new ImageButton(this);
        layoutParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        imageButton.setLayoutParams(layoutParams1);


        TextView textView = new TextView(this);
        textView.setId(R.id.t1);

        layoutParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 7);
        textView.setLayoutParams(layoutParams1);

        main.addView(top);
        main.addView(mid);
        mid.setBackgroundResource(R.drawable.amidback);


        top.addView(imageButton);
        top.addView(textView);

        textView.setText("X 2");
        textView.setTextSize(35);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLACK);

        imageButton.setBackgroundResource(R.drawable.dark_whitemoreborder);


        for (int i = 0; i < 8; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setBackgroundColor(Color.RED);
            mid.addView(linearLayout);
            lv[i] = linearLayout;
        }
        boolean lightstatelayer = false;
        for (int i = 0; i < 8; i++) {
            LinearLayout linearLayout = lv[i];
            if (lightstatelayer) {
                lightstatelayer = false;
            } else {
                lightstatelayer = true;
            }

            for (int j = 0; j < 8; j++) {

                OthelloButton othelloButton = new OthelloButton(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                othelloButton.setLayoutParams(layoutParams);
                if (lightstatelayer) {
                    othelloButton.setBackgroundResource(R.drawable.lightback_border);
                    othelloButton.backcolorcode = 1;
                } else {
                    othelloButton.setBackgroundResource(R.drawable.darkback_border);
                    othelloButton.backcolorcode = -1;
                }

                othelloButton.setOnClickListener(this);
                othelloButton.row = i;
                othelloButton.col = j;
                linearLayout.addView(othelloButton);
                borad[i][j] = othelloButton;

                Toast.makeText(this, "hello", Toast.LENGTH_LONG);

                if (lightstatelayer) {
                    lightstatelayer = false;
                } else {
                    lightstatelayer = true;
                }

            }

        }
        borad[3][3].setBackgroundResource(R.drawable.light_white);
        borad[3][3].setColorcode(1, 1);
        borad[3][4].setBackgroundResource(R.drawable.dark_black);
        borad[3][4].setColorcode(-1, -1);
        borad[4][3].setBackgroundResource(R.drawable.dark_black);
        borad[4][3].setColorcode(-1, -1);
        borad[4][4].setBackgroundResource(R.drawable.light_white);
        borad[4][4].setColorcode(1, 1);

        textView = new TextView(this);
        layoutParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 7);
        layoutParams1.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams1);


        imageButton = new ImageButton(this);
        layoutParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        imageButton.setLayoutParams(layoutParams1);


        main.addView(end);
        end.addView(textView);
        end.addView(imageButton);

        textView.setId(R.id.t2);
        textView.setText("                          2 X");
        textView.setTextSize(35);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.BLACK);
        imageButton.setBackgroundResource(R.drawable.dark_blackmoreborder);

    }

    ArrayList<RowColStore> store = new ArrayList<>();

    public boolean validmovecheck(OthelloButton button) {
        int row = button.row;
        int col = button.col;
        if (button.colorcode != 0) {
            return false;
        }

        store.clear();

        int i = row;
        int j = col;
        int colorCode = currentplayer;
        int oppColorCode = -1 * colorCode;
        boolean oppColorFound = false;

        ArrayList<RowColStore> smallstore = new ArrayList<>();


        i--;
        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }

            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            i--;
        }
        smallstore.clear();
        i = row + 1;
        j = col;
        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }
            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            i++;

        }


        smallstore.clear();
        i = row;
        j = col - 1;

        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }

            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            j--;


        }


        smallstore.clear();
        i = row;
        j = col + 1;

        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }

            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            j++;


        }


        smallstore.clear();
        i = row - 1;
        j = col - 1;

        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }

            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            j--;
            i--;

        }


        smallstore.clear();
        i = row + 1;
        j = col + 1;

        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }

            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            j++;
            i++;

        }


        smallstore.clear();
        i = row + 1;
        j = col - 1;

        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }

            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            j--;
            i++;

        }


        smallstore.clear();
        i = row - 1;
        j = col + 1;

        while (i < 8 && i >= 0 && j < 8 && j >= 0) {
            if (borad[i][j].colorcode == 0) {
                break;
            }

            if (borad[i][j].colorcode == oppColorCode) {
                oppColorFound = true;
                RowColStore Store = new RowColStore(i, j, borad[i][j].backcolorcode);
                smallstore.add(Store);
            }
            if (borad[i][j].colorcode == colorCode) {
                for (int k = 0; k < smallstore.size(); k++) {
                    store.add(smallstore.get(k));

                }
                smallstore.clear();

            }
            j++;
            i--;

        }

        if (store.isEmpty()) {
            return false;
        }

        if (oppColorFound) {
            return true;
        } else {
            return false;
        }
    }

    public void makechangecolor() {
        for (int i = 0; i < store.size(); i++) {
            RowColStore small = store.get(i);
            OthelloButton button = borad[small.row][small.col];
            if (button.backcolorcode == 1) {
                if (currentplayer == 1) {
                    button.setBackgroundResource(R.drawable.light_white);
                } else {
                    button.setBackgroundResource(R.drawable.light_black);
                }
            } else {
                if (currentplayer == 1) {
                    button.setBackgroundResource(R.drawable.dark_white);
                } else {
                    button.setBackgroundResource(R.drawable.dark_black);
                }
            }
            button.colorcode = currentplayer;

        }
        store.clear();

    }

    public boolean count(int step) {
        boolean state = false;
        int Cblack = 0, CWhite = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (step == 1) {
                    if (borad[i][j].colorcode == 0) {
                        state = true;
                    }
                }
                if (step == 2) {
                    if (borad[i][j].colorcode == 1) {
                        CWhite++;
                    }
                    if (borad[i][j].colorcode == -1) {
                        Cblack++;
                    }
                }

            }
        }
        if (step == 2) {

            countWhite = CWhite;
            countBlack = Cblack;
            TextView textView1 = findViewById(R.id.t1);
            TextView textView2 = findViewById(R.id.t2);


            String s1 = "";
            if (currentplayer == -1) {
                if (Cblack / 10 == 0) {
                    s1 = "                           " + Cblack + "X";

                } else {
                    s1 = "                        " + Cblack + "X";
                }
                textView1.setText("X" + countWhite + "    Your Turn");
                textView2.setText(s1);
            } else {
                if (Cblack / 10 == 0) {
                    s1 = " Your Turn        " + Cblack + "X";

                } else {
                    s1 = " Your Turn     " + Cblack + "X";
                }
                textView1.setText("X" + countWhite);
                textView2.setText(s1);

            }
        }


        return state;
    }

    @Override
    public void onClick(View v) {
        if (gameStatus == true) {
            OthelloButton button = (OthelloButton) v;


            if (validmovecheck(button)) {
                if (button.backcolorcode == 1) {
                    if (currentplayer == 1) {
                        button.setBackgroundResource(R.drawable.light_white);
                    } else {
                        button.setBackgroundResource(R.drawable.light_black);
                    }
                } else {
                    if (currentplayer == 1) {
                        button.setBackgroundResource(R.drawable.dark_white);
                    } else {
                        button.setBackgroundResource(R.drawable.dark_black);
                    }
                }
                button.colorcode = currentplayer;


                makechangecolor();
                count(2);
                if (count(1) == false) {
                    if (countWhite > countBlack) {
                        TextView textView = findViewById(R.id.t1);
                        textView.setText("X" + countWhite + "    White Win!!");
                    } else {
                        TextView textView = findViewById(R.id.t2);
                        textView.setText("Black Win!!        X" + countBlack);
                    }
                }

                currentplayer = -1 * currentplayer;

            } else {
                Toast.makeText(this, " Invalid Move", Toast.LENGTH_LONG).show();
            }


        }

    }
}
