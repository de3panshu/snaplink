package com.deepanshu.snaplink.entity;

import com.deepanshu.snaplink.utility.constant.UrlStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginalUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "oid")
    private int id;

    @NotBlank
    private String url;

    @CreationTimestamp()
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expirationDate;

    @UpdateTimestamp
    private LocalDateTime lastModified;

    private int status;

    private String metaTitle;
    private String metaDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid", nullable = false)
    @JsonBackReference
    private ShortUrl shortUrl;

    @PrePersist
    public void prePersist() {
        //setting the default expiration date of the link as 2years.
        if (this.getExpirationDate() == null) {
            this.setExpirationDate(LocalDateTime.now().plusYears(2));
        }

        //setting the status to ENABLED
        this.status |= UrlStatus.ENABLED.getValue();

    }
}
