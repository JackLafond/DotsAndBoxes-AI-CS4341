public class Box {
    private Line north;
    private Line east;
    private Line south;
    private Line west;
    public int completedBy;

    public Box(Line north, Line east, Line south, Line west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.completedBy = 0;
    }

    /**
     * Check to see if the box is completed or not
     * @return True if the box is completed
     */
    public boolean isComplete(){
        return north.isComplete() && east.isComplete() && south.isComplete() && west.isComplete();
    }


    /**
     * Check if the box is one line away from completion
     * @return True if the box can be completed
     */
    public boolean isOneLineAway(){
        return (north.isComplete() ? 1 : 0) + (east.isComplete() ? 1 : 0) + (south.isComplete() ? 1 : 0) + (west.isComplete() ? 1 : 0) == 3;
    }

    /**
     * Update who completed the box
     */
    public void setCompletedBy(int playerID) {
        if(playerID == 1 || playerID == -1) {
            this.completedBy = playerID;
        }
    }

}
