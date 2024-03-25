package ewhabackendDDDstudy.order.command.domain;

import ewhabackendDDDstudy.common.model.Money;

import java.util.List;

// OrderLine 목록을 별도 클래스로 분리했다고 가정해보자(p.108)
public class OrderLines {
    private List<OrderLine> lines;

    public Money getTotalAmounts(){return null;}

    public void changeOrderLines(List<OrderLine> newLines){
        this.lines = newLines;
    }

}
