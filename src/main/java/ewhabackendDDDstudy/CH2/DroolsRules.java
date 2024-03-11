package ewhabackendDDDstudy.CH2;

import java.util.List;

// Drools는 이 절의 주제가 아니므로 아래 코드 설명 x
// evaluate 메서드에 값을 주면 별도 파일로 작성한 규칙을 이용해서 연산을 수행하는 코드 정도로만 이해하고 넘어가자.
public class DroolsRules {
    private KieContainer kContainer;

    public DroolsRules() {
        KieServices ks = KieServices.Factory.get();
        kContainer = ks.getKieClassPathcontainer();
    }

    public void evaluate(String sessionName, List<?> facts) {
        KieContainer kSession = kContainer.newKieSession(sessionName);
        try {
            facts.forEach(x -> kSession.insert(x));
            kSession.fireAllRules();
        } finally {
            kSession.dispose();
        }
    }

}
