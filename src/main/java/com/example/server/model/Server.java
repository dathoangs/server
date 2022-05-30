package com.example.server.model;

import com.example.server.document.Unique;
import com.example.server.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true) @NotEmpty(message = "Địa chỉ IP không được rỗng")
    @Unique(message = "Địa chỉ IP không được trùng")
    private String ipAddress;
    private String name, memory, type, imageUrl;
    private Status status;

}
