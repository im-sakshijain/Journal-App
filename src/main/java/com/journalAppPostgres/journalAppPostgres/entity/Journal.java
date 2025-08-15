package com.journalAppPostgres.journalAppPostgres.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name="journal_entries")
@Data
@NoArgsConstructor
public class Journal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)//auto-increment

  private Long id;

  @NonNull
  @Column(nullable=false)
  private String title;

  @NonNull
  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name="created_at")
  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference
  private User user;


}
