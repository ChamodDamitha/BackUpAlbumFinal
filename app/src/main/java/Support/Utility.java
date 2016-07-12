package Support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chamod on 7/11/2016.
 */
public class Utility
{

    public static String getCurrentDate()
    {
        DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
        Date date=new Date();
        return df.format(date);
    }
}
