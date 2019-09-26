package game.Configurer;

public abstract class simulationParameterCheck {

    private int totalRows;
    private int totalColumns;

    private String BAD_NUM_ROWS = "Unsupported number of rows specified. Using default value of %s.";
    private int defaultRows = 10;
    private String BAD_NUM_COLS = "Unsupported number of columns specified. Using default value of %s.";
    private int defaultCols = 10;

    simulationParameterCheck(int numRows, int numCols){
        totalRows = numRows;
        totalColumns = numCols;
    }

    /**Checks the validity of dimensions supplied by the XML file. If the dimensions are not supported,
     * then the method will return corrected values.
     *
     * @return either the corrected number of rows/columns or the original specification
     */
    public int[] checkDimensions(){
        int[] fixedParameters = new int[2];
        fixedParameters[0] = checkRows(totalRows);
        fixedParameters[1] = checkColumns(totalColumns);
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
