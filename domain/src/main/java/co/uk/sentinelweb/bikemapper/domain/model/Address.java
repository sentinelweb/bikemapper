package co.uk.sentinelweb.bikemapper.domain.model;

/**
 * TODO move to autovalue
 * Created by robert on 18/06/16.
 */

public class Address {
    public static Address create(final String text) {
        final Address address = new Address();
        address.text = text;
        return address;
    }

    private Integer number;
    private String street;
    private String suburb;
    private String city;
    private String county;
    private String country;
    private String postcode;
    private String text;

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(final String county) {
        this.county = county;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(final String suburb) {
        this.suburb = suburb;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
