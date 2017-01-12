//public class Row implements Comparable<Row> {
public class Row {

    protected String[] information = new String[18];
    protected String[] visibleRowData = new String[10];

    protected String status = null;

    public Row(String[] information) {
        this.information = information;

        visibleRowData[0] = information[4];
        visibleRowData[1] = information[5];
        visibleRowData[2] = information[1];
        visibleRowData[3] = information[2];
        visibleRowData[4] = information[3];
        visibleRowData[5] = information[0];
        visibleRowData[6] = information[7];
        visibleRowData[7] = information[8];
        visibleRowData[8] = information[11];
        visibleRowData[9] = information[12];

        status = information[5];

    }

    public void printRows() {
        for (String data : visibleRowData) {
            System.out.println(data);
        }
    }

}
