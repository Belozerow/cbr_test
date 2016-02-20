package com.belozerov.cbrrate.api.model;

import com.belozerov.cbrrate.model.Valute;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */
@Root(strict = false)
public class RateAnswer {
    @ElementList(name = "ValCurs", required = false, inline = true)
    private ArrayList<Valute> valutes;

    public ArrayList<Valute> getValutes() {
        return valutes;
    }

    public void setValutes(ArrayList<Valute> valutes) {
        this.valutes = valutes;
    }
}
