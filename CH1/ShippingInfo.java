package CH1;

public class ShippingInfo {
    private String receiverName;
    private String receiverPhoneNumber;
    private String shippingAddress1;
    private String getShippingAddress2;
    private String shippingZipcode;

    public ShippingInfo(String receiverName, String receiverPhoneNumber, String shippingAddress1, String getShippingAddress2, String shippingZipcode) {
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.shippingAddress1 = shippingAddress1;
        this.getShippingAddress2 = getShippingAddress2;
        this.shippingZipcode = shippingZipcode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
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
