package Model;

/**
 * Created by Chamod on 7/3/2016.
 */
public class Image
{
    private String name;
    private String type;
    private String description;
    private String destination;
    private boolean backed=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isBacked() {
        return backed;
    }

    public void setBacked(boolean backed) {
        this.backed = backed;
    }

    public void loadImage()
    {
        //load image from url

    }

}
