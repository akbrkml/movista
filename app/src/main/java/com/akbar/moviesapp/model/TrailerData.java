package com.akbar.moviesapp.model;

import java.util.List;

/**
 * Created by kamal on 29/11/2016.
 */

public class TrailerData {

    public List<Result> results;

    public class Result {
        public String key;
        public String name;
        public String type;
    }
}
