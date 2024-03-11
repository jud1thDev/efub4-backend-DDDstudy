package ewhabackendDDDstudy.CH1;

public class ShippingInfo {
    // 기존의 합쳐져있던 요소들을 두 밸류타입(받는사람, 주소)으로 분리
    // -> 활용함으로써 shippingInfo가 두 밸류로 이루어짐을 알기 쉬움.
    private Receiver receiver;
    private Address address;

    public ShippingInfo(Receiver receiver, Address address) {
        this.receiver = receiver;
        this.address = address;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Address getAddress() {
        return address;
    }
}
