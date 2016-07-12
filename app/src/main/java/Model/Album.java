package Model;

import java.util.ArrayList;

/**
 * Created by Chamod on 7/3/2016.
 */
public class Album
{
    private int id;
    private String name;
    private String description;
    private String dateModified;
    private int noOfImagesBackedUp;
    private int noOfImages;
    private ArrayList<Image> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public int getNoOfImagesBackedUp() {
        return noOfImagesBackedUp;
    }

    public void setNoOfImagesBackedUp(int noOfImagesBackedUp) {
        this.noOfImagesBackedUp = noOfImagesBackedUp;
    }

    public int getNoOfImages() {
        return noOfImages;
    }

    public void setNoOfImages(int noOfImages) {
        this.noOfImages = noOfImages;
    }

    public void addImage(Image image)
    {
        images.add(image);
    }

    public void removeImage(Image image)
    {
        images.remove(image);
    }
}
