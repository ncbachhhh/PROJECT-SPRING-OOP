package DO_AN.OOP.repository.ATTENDANCE;

import DO_AN.OOP.model.ATTENDANCE.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    List<Attendance> findByAccIdAndLoginTimeBetween(String accId, LocalDateTime startOfMonth, LocalDateTime endOfMonth);
}
