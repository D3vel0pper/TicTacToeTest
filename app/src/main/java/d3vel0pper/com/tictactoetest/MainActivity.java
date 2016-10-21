package d3vel0pper.com.tictactoetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BoardManager boardManager;
    protected Ai ai;
    private boolean restartFlag;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restartFlag = false;
        restartButton = (Button) findViewById(R.id.restart_button);

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
        this.ai = new Ai(this.boardManager);
        ai.setAiTurn(-1);

        for (int i = 0; i < 9; i++){
            buttons[i].setOnClickListener(this);
            buttons[i].setTag(Integer.toString(i));
        }
        ChoiceFragment dialog = new ChoiceFragment();
        dialog.show(getSupportFragmentManager(),"explanation");
    }

    public void onClick(View v){
        Button button = (Button) v;
        if(button.getId() == R.id.restart_button){
            restartFlag = true;
            finish();
        }
        else if(button.getText().equals(" ")){
            int temp = Integer.parseInt(button.getTag().toString());
            boardManager.putStone(temp);
            if(checkIsGameOver()){
                return;
            }
            if(!ai.runAi()){
                Toast.makeText(this,"AIは予期せぬ動作で終了しました",Toast.LENGTH_SHORT).show();
            }
            if(checkIsGameOver()){
                return;
            }
        } else {
            Toast.makeText(this,"その場所には置けません",Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean checkIsGameOver(){
        if(boardManager.isGameOver()){
            switch (boardManager.getBoardState()[boardManager.getHistoryPosition()]){
                case 1:
                    Toast.makeText(this,"Oの勝ちです",Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(this,"Xの勝ちです",Toast.LENGTH_SHORT).show();
                    break;
            }
            showRestartButton();
            return true;
        } else if(boardManager.checkIsBoardFull()){
            Toast.makeText(this,"引き分けです",Toast.LENGTH_SHORT).show();
            showRestartButton();
            return true;
        }
        return false;
    }

    private void showRestartButton(){
        restartButton.setVisibility(View.VISIBLE);
        restartButton.setOnClickListener(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(restartFlag){
            restartFlag = false;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    protected void setAiTurn(int turn){
        ai.setAiTurn(turn);
    }

}
