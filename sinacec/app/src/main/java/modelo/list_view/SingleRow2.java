package modelo.list_view;

/**
 * Created by ayan on 11/15/16.
 */
public class SingleRow2 {
    private String label;
    private String label1;
    private String label2;

    public SingleRow2(){
        ;
    }

    public SingleRow2(String label, String label1, String label2) {
        this.label = label;
        this.label1 = label1;
        this.label2 = label2;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }
}
