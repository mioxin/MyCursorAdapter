package com.example.mmm.mycursoradapter;

import java.util.Date;

/**
 * Created by palchuk on 04.12.2014.
 */
public class Article {
    String title;
    String descr;
    Date adate;
    int img;
    boolean box;
    Article(String _title, String _descr, Date _adate, int _img, boolean _box){
        title = _title;
        descr = _descr;
        adate = _adate;
        img = _img;
        box = _box;
    }
}
