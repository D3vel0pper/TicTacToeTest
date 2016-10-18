package d3vel0pper.com.tictactoetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BoardManager boardManager;
    private Ai ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.parent_linear);

        Button[] buttons;
        buttons = new Button[9];
        buttons[0] = (Button)findViewById(R.id.button0);
        buttons[1] = (Button)findViewById(R.id.button1);
        buttons[2] = (Button)findViewById(R.id.button2);
        buttons[3] = (Button)findViewById(R.id.button3);
        buttons[4] = (Button)findViewById(R.id.button4);
        buttons[5] = (Button)findViewById(R.id.button5);
        buttons[6] = (Button)findViewById(R.id.button6);
        buttons[7] = (Button)findViewById(R.id.button7);
        buttons[8] = (Button)findViewById(R.id.button8);

        this.boardManager = new BoardManager(parentLayout,buttons);
        ai = new Ai(this.boardManager);

        for (int i = 0; i < 9; i++){
            buttons[i].setOnClickListener(this);
            buttons[i].setTag(Integer.toString(i));
        }
    }

    public void onClick(View v){
        Button button = (Button) v;
        if(button.getText().equals(" ")){
            //changeButtonState(boardManager.getTern(),button);
            int temp = Integer.parseInt(button.getTag().toString());
            boardManager.putStone(temp);
            if(boardManager.isGameOver()){
                switch (boardManager.getTern()){
                    case 1:
                        Toast.makeText(this,"Oの勝ちです",Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(this,"Oの勝ちです",Toast.LENGTH_SHORT).show();
                        break;
                }
            } else if(boardManager.checkIsBoardFull()){
                Toast.makeText(this,"引き分けです",Toast.LENGTH_SHORT).show();
            }
            boardManager.switchTern();
            ai.run();
            boardManager.switchTern();
        } else {
            Toast.makeText(this,"その場所には置けません",Toast.LENGTH_SHORT).show();
        }
    }

//    private void changeButtonState(int tern,Button button){
//        switch (tern){
//            case 1:
//                button.setText("O");
//                break;
//            case -1:
//                button.setText("X");
//                break;
//        }
//    }

}
