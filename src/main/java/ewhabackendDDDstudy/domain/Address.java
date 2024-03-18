package ewhabackendDDDstudy.domain;

public class Address {
    // 주소
    private String shippingAddress1;
    private String getShippingAddress2;
    private String shippingZipcode;

    public Address(String shippingAddress1, String getShippingAddress2, String shippingZipcode) {
        this.shippingAddress1 = shippingAddress1;
        this.getShippingAddress2 = getShippingAddress2;
        this.shippingZipcode = shippingZipcode;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public String getGetShippingAddress2() {
        return getShippingAddress2;
    }

    public String getShippingZipcode() {
        return shippingZipcode;
    }

}
