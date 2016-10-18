package d3vel0pper.com.tictactoetest;

/**
 * Created by D3vel0pper on 2016/10/15.
 */

import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * BoardManager class
 * this is managing board that containing
 *  - State (the place's state 0-> nothing 1-> O put 2-> X put) :value
 *  - Tern (which player playing now) :value
 *  - Judge (judge is game over) :Method
 *  - CheckingState :Method
 *  - ChangeTern (Switch tern) :Method
 *  - Run (run game) :Method
 */
public class BoardManager {

    /**
     * Values
     */

    /**
     * tern: 1 -> O's tern -1 -> X's tern
     */
    private int tern;
    private int boardState[];

    private InputStreamReader isr;
    private BufferedReader br;

    private int historyPosition;

    /**
     * show amount of open(nothing put) place
     */
    private int openCount = 9;

    /**
     * position names
     *  ------------
     * | 0 | 1 | 2 |
     * | 3 | 4 | 5 |
     * | 6 | 7 | 8 |
     * -------------
     */
    private static final int TOP_LEFT = 0;
    private static final int TOP_CENTER = 1;
    private static final int TOP_RIGHT = 2;
    private static final int CENTER_LEFT = 3;
    private static final int CENTER = 4;
    private static final int CENTER_RIGHT = 5;
    private static final int BOTTOM_LEFT = 6;
    private static final int BOTTOM_CENTER = 7;
    private static final int BOTTOM_RIGHT = 8;

    /**
     * state name
     * O -> 1
     * X -> -1
     * nothing -> 0
     */
    private static final int NOTHING_PUT = 0;
    private static final int O_PUT = 1;
    private static final int X_PUT = -1;

    /**
     * relate on handling in Android UI
     */
    public Button[] buttons;
    public LinearLayout parentLinear;

    /**
     * Ai that computing
     */
//    private Ai ai;

    /**
     * Constructor
     */
    private BoardManager(){
        tern = 1;
        boardState = new int[9];
        for(int i = 0;i < 9;i++){
            boardState[i] = 0;
        }
        openCount = 9;
        isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
//        ai = new Ai(this);
    }

    public BoardManager(LinearLayout parentLinear,Button[]buttons){
        tern = 1;
        boardState = new int[9];
        for(int i = 0;i < 9;i++){
            boardState[i] = 0;
        }
        openCount = 9;
        this.parentLinear = parentLinear;
        this.buttons = new Button[9];
        this.buttons = buttons;
    }

    /**
     * Getters
     */
    public int[] getBoardState(){
        return boardState;
    }

    public int getOpenCount(){
        return openCount;
    }

    public int getHistoryPosition(){
        return historyPosition;
    }

    public int getTern(){
        return tern;
    }

    /**
     * Methods
     */

    public void run(){
//        desideTurn();
        while(true) {
            //先攻ならここで走る
//            if(ai.getAiTern() == 1){
//                runAi();
//                if (isGameOver()) {
//                    //Print Result
//                    printResult();
//                    return;
//                } else if(checkIsBoardFull()){
//                    //Draw Game
//                    printDraw();
//                    return;
//                }
//            }
//            printBoard();
            inputPosition();
            openCount--;
            if (isGameOver()) {
                //Print Result
                printResult();
                return;
            } else if(checkIsBoardFull()){
                //Draw Game
                printDraw();
                return;
            }
            switchTern();
            //後攻ならここで走る
//            if(ai.getAiTern() == -1){
//                runAi();
//                if (isGameOver()) {
//                    //Print Result
//                    printResult();
//                    return;
//                } else if(checkIsBoardFull()){
//                    //Draw Game
//                    printDraw();
//                    return;
//                }
//            }
        }
    }

//    private void runAi(){
//        putStone(ai.desideNext());
//        putStone(ai.desideTempNext());
//        printBoard();
//        switchTern();
//        openCount--;
//    }

//    private void desideTurn(){
//        System.out.println("先攻か後攻か選択してください。");
//        System.out.println("先攻：１　後攻：２");
//        try {
//            String buf = br.readLine();
//            int input = Integer.parseInt(buf);
//            if(input == 1){
//                ai.setAiTern(-1);
//            } else if(input == 2){
//                ai.setAiTern(1);
//            } else {
//                System.out.println("正しい数字を入力してください。");
//                desideTurn();
//            }
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//    }

    private void inputPosition(){
        System.out.println("置く場所を入力してください。");
        System.out.println("----番号の対応表----");
        showBoardNumber();
        System.out.println("--------------------");
        System.out.print(">> ");
        try {
            String buf = br.readLine();
            int position = Integer.parseInt(buf);
            if(checkState(boardState[position])){
                putStone(position);
            } else {
                System.out.println("その場所に置くことはできません。");
                System.out.print("他の場所を入力してください。\n");
                inputPosition();
            }
        } catch(IOException e){
            e.printStackTrace();
            System.out.println("IOException caused");
        }
    }

    private void printBoard(){
        System.out.println(" ------------");
        for(int i = 0;i < 9;i++){
            System.out.print("| ");
            System.out.print(convertState(boardState[i]));
            System.out.print(" | ");
            i++;
            System.out.print(convertState(boardState[i]));
            System.out.print(" | ");
            i++;
            System.out.print(convertState(boardState[i]));
            System.out.print(" |\n");
        }
        System.out.println(" ------------");
    }

    private void showBoardNumber(){
        System.out.println(" ------------");
        for(int i = 0;i < 9;i++){
            System.out.print("| ");
            System.out.print(i);
            System.out.print(" | ");
            i++;
            System.out.print(i);
            System.out.print(" | ");
            i++;
            System.out.print(i);
            System.out.print(" |\n");
        }
        System.out.println(" ------------");
    }

    public void putStone(int position){
        boardState[position] = tern;
        if(tern == 1){
            buttons[position].setText("O");
        } else if(tern == -1){
            buttons[position].setText("X");
        }
        historyPosition = position;
    }

    /**
     * @param target : the position on board
     * @return : string  O or X matched in state
     */
    private String convertState(int target){
        if(target == O_PUT){
            return "O";
        } else if(target == X_PUT){
            return "X";
        }
        return " ";
    }

    /**
     * return position is possible to put or not
     * @param target : the position to check
     * @return : true -> position has nothing
     *              false -> position has something
     */
    public boolean checkState(int target){
        if(target == NOTHING_PUT) {
            return true;
        }
        return false;
    }

    public void switchTern(){
        if(tern == -1){
            tern = 1;
        } else if(tern == 1){
            tern = -1;
        } else {
            System.out.print("\n!!!--- Illegal tern. Please quit program... ---!!!\n");
        }
    }

    private void printResult(){
        System.out.println("～最終結果～");
        printBoard();
        System.out.print( "「" + convertState(boardState[historyPosition]) + "」の勝ち！！");
        try {
            br.close();
            isr.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.print("failed to close buffered reader or input stream reader");
        }
    }

    private void printDraw(){
        System.out.println("～最終結果～");
        printBoard();
        System.out.print( "ー引き分けー");
        try {
            br.close();
            isr.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.print("failed to close buffered reader or input stream reader");
        }
    }

    public boolean isGameOver(){
        if(checkCrossLine(historyPosition)){
            return true;
        } else if(cheackHorizontal(historyPosition)){
            return true;
        } else if(checkVertical(historyPosition)){
            return true;
        }
        return false;
    }

    private boolean cheackHorizontal(int position){
        if(position < 3){
            if(boardState[TOP_LEFT] == boardState[TOP_CENTER] && boardState[TOP_LEFT] == boardState[TOP_RIGHT]){
                return true;
            }
        } else if(position < 6){
            if(boardState[CENTER_LEFT] == boardState[CENTER] && boardState[CENTER_LEFT] == boardState[CENTER_RIGHT]){
                return true;
            }
        } else if(position < 9){
            if(boardState[BOTTOM_LEFT] == boardState[BOTTOM_CENTER] && boardState[BOTTOM_LEFT] == boardState[BOTTOM_RIGHT]){
                return true;
            }
        }
        return false;
    }

    private boolean checkVertical(int position){
        //position % 3 == 0 ??
        if(position == 0 || position == 3 || position == 6){
            if(boardState[TOP_LEFT] == boardState[CENTER_LEFT] && boardState[TOP_LEFT] == boardState[BOTTOM_LEFT]){
                return true;
            }
        }
        //position % 3 == 1 ??
        else if(position == 1 || position == 4 || position == 7){
            if(boardState[TOP_CENTER] == boardState[CENTER] && boardState[TOP_CENTER] == boardState[BOTTOM_CENTER]){
                return true;
            }
        }
        //positiion % 3 == 2 ??
        else if(position == 2 || position == 5 || position == 8){
            if(boardState[TOP_RIGHT] == boardState[CENTER_RIGHT] && boardState[TOP_RIGHT] == boardState[BOTTOM_RIGHT]){
                return true;
            }
        }
        return false;
    }

    private boolean checkCrossLine(int position){
        if(position == 4){
            if (boardState[TOP_LEFT] == boardState[CENTER] && boardState[TOP_LEFT] == boardState[BOTTOM_RIGHT]) {
                return true;
            } else if (boardState[TOP_RIGHT] == boardState[CENTER] && boardState[TOP_RIGHT] == boardState[BOTTOM_LEFT]) {
                return true;
            }
        }
        //position % 4 == 0 ??
        else if(position == 0 || position == 8) {
            if (boardState[TOP_LEFT] == boardState[CENTER] && boardState[TOP_LEFT] == boardState[BOTTOM_RIGHT]) {
                return true;
            }
        }
        //position % 2 == 0 ??
        else if(position == 2 || position == 6) {
            if (boardState[TOP_RIGHT] == boardState[CENTER] && boardState[TOP_RIGHT] == boardState[BOTTOM_LEFT]) {
                return true;
            }
        }
        return false;
    }

    /**
     * ---- for Debugging ----
     */
    public boolean checkIsBoardFull(){
        if(openCount == 0){
            return true;
        }
        return false;
    }

}
