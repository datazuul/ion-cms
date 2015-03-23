package org.nextime.ion.commons;

import java.util.Collections;
import java.util.Vector;
import org.nextime.ion.framework.business.Publication;
import org.nextime.ion.framework.business.Section;

// Referenced classes of package org.nextime.ion.commons:
//            MyPublicationComparator

public class PublicationSorter
{

    public PublicationSorter()
    {
    }

    public static Vector sortPublications(Section s)
    {
        Vector publications = s.listPublications();
        Vector result = (Vector)publications.clone();
        Collections.sort(result, new MyPublicationComparator(s));
        return result;
    }

    public static void upPublication(Publication p, Section s)
    {
        try
        {
            int currentIndex = Integer.parseInt(p.getMetaData("index") + "");
            int newIndex = currentIndex + 1;
            Vector publications = s.listPublications();
            if(newIndex > publications.size())
            {
                newIndex = publications.size();
            } else
            {
                for(int i = 0; i < publications.size(); i++)
                {
                    if(newIndex == Integer.parseInt(((Publication)publications.get(i)).getMetaData("index") + ""))
                    {
                        Publication pac = (Publication)publications.get(i);
                        pac.setMetaData("index", (Integer.parseInt(pac.getMetaData("index") + "") - 1) + "");
                    }
                }

            }
            p.setMetaData("index", newIndex + "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void downPublication(Publication p, Section s)
    {
        try
        {
            int currentIndex = Integer.parseInt(p.getMetaData("index") + "");
            int newIndex = currentIndex - 1;
            Vector publications = s.listPublications();
            if(newIndex < 1)
            {
                newIndex = 1;
            } else
            {
                for(int i = 0; i < publications.size(); i++)
                {
                    if(newIndex == Integer.parseInt(((Publication)publications.get(i)).getMetaData("index") + ""))
                    {
                        Publication pac = (Publication)publications.get(i);
                        pac.setMetaData("index", (Integer.parseInt(pac.getMetaData("index") + "") + 1) + "");
                    }
                }

            }
            p.setMetaData("index", newIndex + "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void initPublication(Publication p, Section s)
    {
        try
        {
            Vector publications = s.listPublications();
            if(publications.contains(p))
            {
                p.setMetaData("index", publications.size() + "");
            } else
            {
                p.setMetaData("index", (publications.size() + 1) + "");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void removePublication(Publication p, Section s)
    {
        try
        {
            Vector publications = s.listPublications();
            int index = Integer.parseInt(p.getMetaData("index") + "");
            for(int i = 0; i < publications.size(); i++)
            {
                Publication tp = (Publication)publications.get(i);
                int tindex = Integer.parseInt(tp.getMetaData("index") + "");
                if(tindex > index)
                {
                    tp.setMetaData("index", (tindex - 1) + "");
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
