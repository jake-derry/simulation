package game.Configurer;

public abstract class simulationParameterCheck {

    private int totalRows;
    private int totalColumns;

    private String BAD_NUM_ROWS = "Unsupported number of rows specified. Using default value of %s.";
    private int defaultRows = 10;
    private String BAD_NUM_COLS = "Unsupported number of columns specified. Using default value of %s.";
    private int defaultCols = 10;

    public int[] simulationParameterCheck(int numRows, int numCols){
        int[] fixedParameters = new int[2];
        fixedParameters[0] = checkRows(numRows);
        fixedParameters[1] = checkColumns(numCols);
        return fixedParameters;
    }

    protected int checkRows(int fileRows){
        if(fileRows < 0){
            totalRows = defaultRows;
            throw new XMLSimulationException(BAD_NUM_ROWS, defaultRows);
        }
        return totalRows;
    }
    protected int checkColumns(int fileCols){
        if(fileCols < 0){
            totalColumns = defaultCols;
            throw new XMLSimulationException(BAD_NUM_ROWS, defaultRows);
        }
        return totalRows;
    }
}
