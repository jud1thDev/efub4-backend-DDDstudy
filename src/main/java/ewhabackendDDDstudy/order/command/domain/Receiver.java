package ewhabackendDDDstudy.order.command.domain;

public class Receiver {
    // 받는 사람
    private String name;
    private String phoneNumber;

    public Receiver(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object other) {
        if ( this == other) return true;
        if ( other == null) return false;
        if (! (other instanceof Receiver)) return false;
        Receiver that = (Receiver)other;
        return this.name.equals(that.name) &&
                this.phoneNumber.equals(that.phoneNumber);
    }
}
