package com.infusion.digimenu.location;

import java.util.Observable;

public abstract class CountryLocator extends Observable {
    public abstract void locateCountry();
}
