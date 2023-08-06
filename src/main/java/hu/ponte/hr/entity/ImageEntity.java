package hu.ponte.hr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mimeType;
    private Long size;

    @Column(length = 500)
    private String digitalSign;

    @Lob
    @Column(name = "data", length = 2000000)
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

}
