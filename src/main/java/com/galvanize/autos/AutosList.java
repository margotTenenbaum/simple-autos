package com.galvanize.autos;

import java.util.ArrayList;
import java.util.List;

public class AutosList {
    private List<Auto> autosList;
    public AutosList(List autos) {
        this.autosList = autos;
    }

    public List<Auto> getAutosList() {
        return autosList;
    }
}
