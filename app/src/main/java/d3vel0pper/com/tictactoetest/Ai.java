package d3vel0pper.com.tictactoetest;

/**
 * Created by D3vel0pper on 2016/10/15.
 */
public class Ai {

    public Ai(BoardManager boardManager) {
        this.boardManager = boardManager;
        DEPTH = 0;
        boardState = new int[9];
        for (int i = 0; i < 9; i++) {
            boardState[i] = boardManager.getBoardState()[i];
        }
    }

    private static final int WIN = 10;
    private static final int LOSE = -10;
    private int DEPTH;

    private BoardManager boardManager;
    private int myTern;
    private int boardState[];

    /**
     * unDo putting
     * //         * @param historyPosition : history position that ai put before
     */
    private void unDo(int historyPosition) {
        boardManager.getBoardState()[historyPosition] = 0;
//            boardManager.switchTern();
        DEPTH = 0;
    }

    public void setAiTern(int aiTern) {
        myTern = aiTern;
    }

    public int getAiTern() {
        return myTern;
    }

    public int desideNext() {
        //this will return 0 if nothing place to put
//        return minMax(myTern,3);
        return 0;
    }

    public void run(){
        int next = desideTempNext();
        boardManager.putStone(next);
    }

    public int desideTempNext() {
        for (int i = 0; i < 9; i++) {
            if (boardManager.checkState(boardManager.getBoardState()[i])) {
                return i;
            }
        }
        return 0;
    }

}