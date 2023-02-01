package common;

public class MetOfficeRecordParser {

    private String year;
    private String airTemperatureString;
    private int airTemperature;
    private boolean airTemperatureValid;

    public void parse(String record) {
        if (record.length() < 18) { return ; }

        year = record.substring(3, 7);
    }
}
