package pe.nanamochi.osu.banchojar.entities.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id = UUID.randomUUID();
    @ManyToOne
    private User user;
    private String osuVersion;
    private int utcOffset;
    private float latitude;
    private float longitude;
    private boolean displayCityLocation;
    private boolean pmPrivate;
    private String osuPathMd5;
    private String adaptersStr;
    private String adaptersMd5;
    private String uninstallMd5;
    private String diskSignatureMd5;

}
