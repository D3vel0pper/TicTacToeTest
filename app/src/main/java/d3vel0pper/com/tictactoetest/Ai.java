package d3vel0pper.com.tictactoetest;

/**
 * Created by D3vel0pper on 2016/10/15.
 */
public class Ai {

    public Ai(BoardManager boardManager){
        this.parentBoard = boardManager;
        this.boardManager = new BoardManager();
        for(int i = 0;i < 9;i++){
            this.boardManager.getBoardState()[i] = boardManager.getBoardState()[i];
        }
        //debug
//            evaValList = new ArrayList<Integer>();
//            bestPosListMy = new ArrayList<Integer>();
//            bestPosListOth = new ArrayList<Integer>();
    }

    /**
     * own boarManager
     */
    private BoardManager boardManager;
    /**
     * parent boardManager
     */
    private BoardManager parentBoard;
    private int myTurn;
    private int boardState[];

    /**
     * max thinking depth
     */
    private int maxDepth = 9;

    public boolean runAi(){
        if(parentBoard.getHistoryPosition() != -1) {
            boardManager.putStoneThink(parentBoard.getHistoryPosition());
        }
        int nextPosition = decideNext();
        if(nextPosition == -1){
            return false;
        }
        parentBoard.putStone(nextPosition);
        boardManager.putStoneThink(nextPosition);
        return true;
    }

    /**
     * unDo putting
     //         * @param historyPosition : history position that ai put before
     */
    private void unDo(int historyPosition){
        boardManager.getBoardState()[historyPosition] = 0;
        boardManager.switchTurn();
        boardManager.incrementOpenCount();
    }

    /**
     * set Which tern will Ai put stone
     * @param aiTern :tern that showing ai's tern
     */
    public void setAiTurn(int aiTern){
        myTurn = aiTern;
    }
    public int getAiTurn(){
        return myTurn;
    }

    private boolean isAllEmpty(){
        for(int i = 0;i < 9;i++){
            if(boardManager.getBoardState()[i] != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * check board is leached or not
     * @return : -1 -> not yet , other -> position that will finish game if the place be put
     */
    public int isLeach(int targetTurn){
        int iterator = 0;
        int tergetTurn = targetTurn;
        for(;iterator < 9;iterator++) {
            if(boardManager.checkState(boardManager.getBoardState()[iterator])){
                if(isRightLeach(iterator,tergetTurn)){
                    return iterator;
                }
                if(isUnderLeach(iterator,tergetTurn)){
                    return iterator;
                }
                if(isLeftLeach(iterator,tergetTurn)){
                    return iterator;
                }
                if(isUpLeach(iterator,tergetTurn)){
                    return iterator;
                }
                if(isCrossLeach(iterator,tergetTurn)){
                    return iterator;
                }
                if(isBetweenLeach(iterator,tergetTurn)){
                    return iterator;
                }
            }
        }
        return -1;
    }

    private boolean isRightLeach(int startPosition, int checkTurn){
        if(startPosition == 0 || startPosition == 3 || startPosition == 6){
            if(boardManager.getBoardState()[startPosition + 1] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 1] == boardManager.getBoardState()[startPosition + 2])){
                return true;
            }
        }
        return false;
    }

    private boolean isUnderLeach(int startPosition, int checkTurn){
        if(startPosition == 0 || startPosition == 1 || startPosition == 2){
            if(boardManager.getBoardState()[startPosition + 3] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 3] == boardManager.getBoardState()[startPosition + 6])){
                return true;
            }
        }
        return false;
    }

    private boolean isLeftLeach(int startPosition, int checkTurn){
        if(startPosition == 2 || startPosition == 5 || startPosition == 8){
            if(boardManager.getBoardState()[startPosition - 1] == checkTurn &&
                    (boardManager.getBoardState()[startPosition - 1] == boardManager.getBoardState()[startPosition - 2])){
                return true;
            }
        }
        return false;
    }

    private boolean isUpLeach(int startPosition, int checkTurn){
        if(startPosition == 6 || startPosition == 7 || startPosition == 8){
            if(boardManager.getBoardState()[startPosition - 3] == checkTurn &&
                    (boardManager.getBoardState()[startPosition - 3] == boardManager.getBoardState()[startPosition - 6])){
                return true;
            }
        }
        return false;
    }

    private boolean isCrossLeach(int startPosition, int checkTurn){
        if(startPosition == 4){
            if(boardManager.getBoardState()[startPosition - 4] == checkTurn &&
                    (boardManager.getBoardState()[startPosition - 4] == boardManager.getBoardState()[startPosition + 4])){
                return true;
            } else if(boardManager.getBoardState()[startPosition - 2] == checkTurn &&
                    (boardManager.getBoardState()[startPosition - 2] == boardManager.getBoardState()[startPosition + 2])){
                return true;
            }
        } else if(startPosition == 0){
            if(boardManager.getBoardState()[startPosition + 4] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 4] == boardManager.getBoardState()[startPosition + 8])){
                return true;
            }
        } else if(startPosition == 2){
            if(boardManager.getBoardState()[startPosition + 2] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 2] == boardManager.getBoardState()[startPosition + 4])){
                return true;
            }
        } else if(startPosition == 6){
            if(boardManager.getBoardState()[startPosition - 2] == checkTurn &&
                    (boardManager.getBoardState()[startPosition - 2] == boardManager.getBoardState()[startPosition - 4])){
                return true;
            }
        } else if(startPosition == 8){
            if(boardManager.getBoardState()[startPosition - 4] == checkTurn &&
                    (boardManager.getBoardState()[startPosition - 4] == boardManager.getBoardState()[startPosition - 8])){
                return true;
            }
        }
        return false;
    }

    private boolean isBetweenLeach(int startPosition, int checkTurn){
        if(startPosition == 4){
            if(boardManager.getBoardState()[startPosition + 1] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 1] == boardManager.getBoardState()[startPosition - 1])){
                return true;
            } else if(boardManager.getBoardState()[startPosition + 3] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 3] == boardManager.getBoardState()[startPosition - 3])){
                return true;
            }
        } else if(startPosition == 1 || startPosition == 7){
            if(boardManager.getBoardState()[startPosition + 1] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 1] == boardManager.getBoardState()[startPosition - 1])){
                return true;
            }
        } else if(startPosition == 3 || startPosition == 5){
            if(boardManager.getBoardState()[startPosition + 3] == checkTurn &&
                    (boardManager.getBoardState()[startPosition + 3] == boardManager.getBoardState()[startPosition - 3])){
                return true;
            }
        }
        return false;
    }

    /**
     * decide next
     * @return :nextPosition -> position ai will put , -1 -> unexpected work happen;
     */
    public int decideNext(){
        int nextPosition = -1;
        nextPosition = isLeach(myTurn);
        if(nextPosition != -1){
            return nextPosition;
        }
        if(isAllEmpty() && boardManager.getBoardState()[4] == 0){
            nextPosition = 4;
        }
        if(nextPosition != -1){
            return nextPosition;
        }
        nextPosition = isLeach(myTurn * -1);
        if(nextPosition != -1){
            return nextPosition;
        }
//        if(boardManager.getBoardState()[4] == 0){
//            nextPosition = 4;
//        }
//        if(nextPosition != -1){
//            return nextPosition;
//        }
        nextPosition = minMax(myTurn * -1, maxDepth);
        if(nextPosition != -1){
            return nextPosition;
        }
        return -1;
    }

    /**
     * mini max algorithm
     * @param turn :
     * @param depth : remained open place (constant value)
     * @return : Child node returns evaluated value, Root node returns NextPosition
     */
    private int minMax(int turn,int depth){

        int evaluatedValue;
        int childValue = 0;
        int bestNextPosition = 0;
        int historyPosition = -1;

        //check is game over or board full
        if(depth != maxDepth) {
            if (boardManager.isGameOver()) {
                //if before this, is myTurn
                if (turn * -1 == myTurn) {
                    if(boardManager.getHistoryPosition() == 0
                            || boardManager.getHistoryPosition() == 2
                            || boardManager.getHistoryPosition() == 6
                            || boardManager.getHistoryPosition() == 8){
                        return 10 - depth - 2;
                    }
                    return 10 - depth;
                } else {
                    if(boardManager.getHistoryPosition() == 0
                            || boardManager.getHistoryPosition() == 2
                            || boardManager.getHistoryPosition() == 6
                            || boardManager.getHistoryPosition() == 8){
                        return depth + 2 - 10;
                    }
                    return depth - 10;
                }
            }
            if (boardManager.checkIsBoardFull()) {
                return 0;
            }
        }

        depth -= 1;

        if(turn != myTurn){
            evaluatedValue = Integer.MAX_VALUE;
        } else {
            evaluatedValue = Integer.MIN_VALUE;
        }

        for(int i = 0;i < 9;i++){
            if(boardManager.checkState(boardManager.getBoardState()[i])){

                boardManager.putStoneThink(i);
                historyPosition = i;
                childValue = minMax(turn * -1,depth);

                //if next is opposite turn
                if((turn * -1) == (myTurn * -1)){
                    if(childValue > evaluatedValue){
                        evaluatedValue = childValue;
                        bestNextPosition = historyPosition;
                    }
                }
                //if next is my turn
                else if((turn * -1) == myTurn){
                    if(childValue <= evaluatedValue){
                        evaluatedValue = childValue;
                        bestNextPosition = historyPosition;
                    }
                }

                unDo(historyPosition);

            }
        }

        if((depth + 1) == maxDepth){
            return bestNextPosition;
        }
        return childValue;
    }
}