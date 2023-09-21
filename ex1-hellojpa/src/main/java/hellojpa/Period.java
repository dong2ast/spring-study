package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable //값 타입을 정의 (JPA에게 알려줌)
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

}
