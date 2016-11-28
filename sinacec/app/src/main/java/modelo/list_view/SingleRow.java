package modelo.list_view;

/**
 * Created by ayan on 11/14/16.
 */
public class SingleRow implements java.io.Serializable{
    private byte[] image;
    private String label;

    public SingleRow(byte[] image, String label) {
        this.image = image;
        this.label = label;
    }

    public SingleRow() {

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
