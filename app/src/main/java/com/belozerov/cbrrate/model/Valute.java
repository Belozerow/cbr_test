package com.belozerov.cbrrate.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */

@Root(name = "Valute", strict = false)
public class Valute {
    @Element(name = "NumCode")
    private long numCode;
    @Element(name = "CharCode")
    private String charCode;
    @Element(name = "Nominal")
    private long nominal;
    @Element(name = "Name")
    private String name;
    @Element(name = "Value")
    private String value;

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNominal() {
        return nominal;
    }

    public void setNominal(long nominal) {
        this.nominal = nominal;
    }

    public long getNumCode() {
        return numCode;
    }

    public void setNumCode(long numCode) {
        this.numCode = numCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
